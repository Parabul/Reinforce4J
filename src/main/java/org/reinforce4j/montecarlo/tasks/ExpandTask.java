package org.reinforce4j.montecarlo.tasks;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.math3.util.Pair;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.core.Player;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.montecarlo.AverageValue;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchModule;
import org.reinforce4j.montecarlo.TreeNode;
import org.reinforce4j.montecarlo.strategies.ExpansionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorflow.example.Example;

/** Expands given state. */
public class ExpandTask implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(ExpandTask.class);

  //  private static final int MAX_ITERATIONS = 30_000;
  private static final int MAX_ITERATIONS = 50;

  private final ExpandTaskFactory expandTaskFactory;
  private final ExpansionStrategy expansionStrategy;
  private final Evaluator evaluator;
  private final int pruneMinVisits;
  private final ListeningExecutorService executor;

  private final TreeNode currentNode;
  private final int numExpansions;
  private final AtomicInteger completeExpansions;
  private final List<Example> expandedNodes;
  private final int numberOfMoves;

  @AssistedInject
  ExpandTask(
      NumberOfMoves numberOfMoves,
      ExpandTaskFactory expandTaskFactory,
      ExpansionStrategy expansionStrategy,
      Evaluator evaluator,
      AtomicInteger completeExpansions,
      @MonteCarloTreeSearchModule.PruneMinVisits int pruneMinVisits,
      ListeningExecutorService executor,
      @MonteCarloTreeSearchModule.ExpandedNodes List<Example> expandedNodes,
      @Assisted TreeNode currentNode,
      @Assisted int numExpansions) {
    this.numberOfMoves = numberOfMoves.value();
    this.expandTaskFactory = expandTaskFactory;
    this.completeExpansions = completeExpansions;
    this.executor = executor;
    this.currentNode = currentNode;
    this.expansionStrategy = expansionStrategy;
    this.numExpansions = numExpansions;
    this.evaluator = evaluator;
    this.pruneMinVisits = pruneMinVisits;
    this.expandedNodes = expandedNodes;
  }

  @Override
  public void run() {
    try {
      int expansions = completeExpansions.incrementAndGet();
      if (expansions % 10 == 0) {
        logger.info("Expansions " + expansions);
      }
      // logger.info("Expanding started");
      Deque<Pair<TreeNode, AverageValue>> backPropagationStack = new ArrayDeque<>();
      for (int i = 0; i < numExpansions; i++) {
        expand(currentNode, backPropagationStack);
      }

      // logger.info("Pruning started");
      prune(currentNode);

      // logger.info(completeExpansions.get() + " expansions completed.");
      // logger.info("Successfully expanded state: \n" + currentNode.state());
      // treeWriter.writeOne(currentNode);
      expandedNodes.add(currentNode.toExample());
      for (TreeNode child : currentNode.getChildStates()) {
        if (child.state().isGameOver()) {
          continue;
        }

        if (expansions > MAX_ITERATIONS) {
          // logger.warn("--------------------------------------------------------");
          // logger.info("No longer accepting expansions");
          // logger.warn("--------------------------------------------------------");
          if (!executor.isShutdown()) {
            logger.info("Shutdown. No longer accepting expansions");
            executor.shutdownNow();
          }
          return;
        }
        executor.submit(expandTaskFactory.create(child, 1000 - child.getVisits()));
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void expand(
      TreeNode currentNode, Deque<Pair<TreeNode, AverageValue>> backPropagationStack) {
    while (!currentNode.state().isGameOver()) {
      // If child nodes are visited for the first time, collect average value.
      Optional<AverageValue> childValue = initChildren(currentNode);
      if (childValue.isPresent()) {
        backPropagationStack.addLast(Pair.create(currentNode, childValue.get()));
      } else {
        backPropagationStack.addLast(Pair.create(currentNode, null));
      }

      // Move
      currentNode = currentNode.getChildStates()[expansionStrategy.suggestMove(currentNode)];
    }

    // Game Over: a leaf should have a winner.
    Player winner = currentNode.state().getWinner();

    AverageValue accumulatedValue = new AverageValue();
    // Draw has no value, otherwise 1 to the winner.
    accumulatedValue.addWinner(winner);

    // Update leaf node
    currentNode.update(winner, accumulatedValue);

    // Back propagate:
    while (!backPropagationStack.isEmpty()) {
      if (backPropagationStack.peekLast().getSecond() != null) {
        accumulatedValue.add(backPropagationStack.peekLast().getSecond());
      }
      // Update parents
      backPropagationStack.pollLast().getFirst().update(winner, accumulatedValue);
    }
  }

  private Optional<AverageValue> initChildren(TreeNode treeNode) {
    if (treeNode.isInitialized()) {
      return Optional.empty();
    }
    treeNode.setInitialized(true);
    for (int move = 0; move < numberOfMoves; move++) {
      if (!treeNode.state().isMoveAllowed(move)) {
        continue;
      }
      treeNode.getChildStates()[move] = new TreeNode(treeNode.state().move(move), numberOfMoves);
    }

    evaluator.evaluate(treeNode.getChildStates());

    AverageValue childrenAverageValue = new AverageValue();
    for (int move = 0; move < numberOfMoves; move++) {
      if (!treeNode.state().isMoveAllowed(move)) {
        continue;
      }
      TreeNode childNode = treeNode.getChildStates()[move];
      childNode
          .getAverageValue()
          .fromEvaluation(childNode.state().getCurrentPlayer(), childNode.evaluation().getValue());
      childrenAverageValue.add(childNode.getAverageValue());
    }

    return Optional.of(childrenAverageValue);
  }

  private void prune(TreeNode currentNode) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(currentNode);

    while (!stack.isEmpty()) {
      TreeNode current = stack.pop();
      if (current.isLeaf()) {
        continue;
      }
      if (current.getVisits() > pruneMinVisits) {
        for (TreeNode child : current.getChildStates()) {
          if (child != null) {
            stack.push(child);
          }
        }
      } else {
        pruneChildNodes(current);
      }
    }
  }

  private void pruneChildNodes(TreeNode node) {
    node.setInitialized(false);

    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode[] children = node.getChildStates();
    for (int move = 0; move < children.length; move++) {
      if (children[move] != null) {
        stack.push(children[move]);
        children[move] = null;
      }
    }

    while (!stack.isEmpty()) {
      TreeNode current = stack.pop();
      if (!current.isLeaf()) {
        for (TreeNode child : current.getChildStates()) {
          if (child != null) {
            stack.push(child);
          }
        }
      }
    }
  }
}
