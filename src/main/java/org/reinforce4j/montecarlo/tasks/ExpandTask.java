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
import org.reinforce4j.constants.NumberOfExpansionsPerNode;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.constants.NumberOfNodesToExpand;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;
import org.reinforce4j.evaluation.batch.BatchClientEvaluator;
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


  private final ExpandTaskFactory expandTaskFactory;
  private final ExpansionStrategy expansionStrategy;
  private final BatchClientEvaluator evaluator;
  private final ListeningExecutorService executor;

  private final GameState currentState;
  private final int numExpansions;
  private final AtomicInteger completeExpansions;
  private final List<Example> expandedNodes;
  private final int numberOfMoves;
  private final int numberOfExpansionsPerNode;
  private final int numberOfNodesToExpand;

  @AssistedInject
  ExpandTask(
      NumberOfMoves numberOfMoves,
      NumberOfExpansionsPerNode numberOfExpansionsPerNode,
      NumberOfNodesToExpand numberOfNodesToExpand,
      ExpandTaskFactory expandTaskFactory,
      ExpansionStrategy expansionStrategy,
      BatchClientEvaluator evaluator,
      AtomicInteger completeExpansions,
      ListeningExecutorService executor,
      @MonteCarloTreeSearchModule.ExpandedNodes List<Example> expandedNodes,
      @Assisted GameState gameState,
      @Assisted int numExpansions) {
    this.currentState = gameState;
    this.numExpansions = numExpansions;
    this.numberOfExpansionsPerNode = numberOfExpansionsPerNode.value();
    this.numberOfNodesToExpand = numberOfNodesToExpand.value();

    this.numberOfMoves = numberOfMoves.value();
    this.expandTaskFactory = expandTaskFactory;
    this.completeExpansions = completeExpansions;
    this.executor = executor;
    this.expansionStrategy = expansionStrategy;
    this.evaluator = evaluator;
    this.expandedNodes = expandedNodes;
  }

  @Override
  public void run() {
    try {
      TreeNode currentNode = new TreeNode(currentState, numberOfMoves);

      for (int i = 0; i < numExpansions; i++) {
        expand(currentNode);
      }

      expandedNodes.add(currentNode.toExample());
      int expansions = completeExpansions.incrementAndGet();
      if (expansions % 10 == 1) {
        logger.info("Complete expansions {}", expansions);
      }
      if (expansions > numberOfNodesToExpand) {
        if (!executor.isShutdown()) {
          logger.warn("--------------------------------------------------------");
          logger.info("Shutdown. No longer accepting expansions");
          logger.warn("--------------------------------------------------------");
          executor.shutdownNow();
        }
        return;
      }

      for (TreeNode child : currentNode.getChildStates()) {
        if (child.state().isGameOver()) {
          continue;
        }
        executor.submit(expandTaskFactory.create(child.state(), numberOfExpansionsPerNode));
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void expand(TreeNode currentNode) {
    Deque<Pair<TreeNode, AverageValue>> backPropagationStack = new ArrayDeque<>();

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
}
