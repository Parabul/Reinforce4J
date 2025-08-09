package org.reinforce4j.montecarlo.tasks;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import org.apache.commons.math3.util.Pair;
import org.reinforce4j.constants.NumberOfExpansionsPerNode;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.core.GameState;
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

  private final ExpandTaskFactory expandTaskFactory;
  private final ExpansionStrategy expansionStrategy;
  private final Evaluator evaluator;
  private final ExecutorService executor;

  private final GameState currentState;
  private final Queue<Example> expandedNodes;
  private final int numberOfMoves;
  private final int numberOfExpansionsPerNode;
  private final CountDownLatch expansionsRemaining;

  @AssistedInject
  ExpandTask(
      NumberOfMoves numberOfMoves,
      NumberOfExpansionsPerNode numberOfExpansionsPerNode,
      ExpandTaskFactory expandTaskFactory,
      ExpansionStrategy expansionStrategy,
      @MonteCarloTreeSearchModule.DefaultEvaluator Evaluator evaluator,
      @MonteCarloTreeSearchModule.ExpansionWorkers ExecutorService executor,
      @MonteCarloTreeSearchModule.ExpandedNodes Queue<Example> expandedNodes,
      @Assisted GameState gameState,
      @Assisted CountDownLatch expansionsRemaining) {

    this.numberOfMoves = numberOfMoves.value();
    this.numberOfExpansionsPerNode = numberOfExpansionsPerNode.value();
    this.expandTaskFactory = expandTaskFactory;
    this.expansionsRemaining = expansionsRemaining;
    this.executor = executor;
    this.expansionStrategy = expansionStrategy;
    this.evaluator = evaluator;
    this.expandedNodes = expandedNodes;
    this.currentState = gameState;
  }

  @Override
  public void run() {
    try {
      TreeNode currentNode = new TreeNode(currentState, numberOfMoves);

      for (int i = 0; i < numberOfExpansionsPerNode; i++) {
        expand(currentNode);
      }

      expandedNodes.offer(currentNode.toExample());
      expansionsRemaining.countDown();
      long expansionsLeft = expansionsRemaining.getCount();
      if (expansionsLeft % 10 == 0) {
        logger.info("Remaining expansions {}", expansionsLeft);
        logger.info(executor.toString());
      }
      if (expansionsLeft < 1) {
        logger.warn("--------------------------------------------------------");
        logger.warn("No longer accepting expansions!");
        logger.warn("--------------------------------------------------------");

        return;
      }

      for (TreeNode child : currentNode.getChildStates()) {
        if (child == null) {
          continue;
        }
        if (child.state().isGameOver()) {
          continue;
        }
        expandedNodes.offer(child.toExample());
        if (Math.random() < 0.5) {
          executor.submit(expandTaskFactory.create(child.state(), expansionsRemaining));
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void expand(TreeNode currentNode) {
    Deque<Pair<TreeNode, AverageValue>> backPropagationStack = new ArrayDeque<>();

    while (!currentNode.state().isGameOver()) {
      // If child nodes are visited for the first time, collect average value.
      Optional<AverageValue> childValue = currentNode.initChildren(evaluator);
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
}
