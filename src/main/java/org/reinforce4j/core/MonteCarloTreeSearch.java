package org.reinforce4j.core;

import java.util.*;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;

public class MonteCarloTreeSearch<T extends GameState> {

  private final StateNode root;
  private final StateNodeService<T> stateNodeService;
  private final Strategy strategy;
  private final BackPropagationStack backPropagationStack;

  public MonteCarloTreeSearch(StateNodeService<T> stateNodeService) {
    this.stateNodeService = stateNodeService;
    this.root = stateNodeService.newRoot();
    this.strategy = new Strategy(stateNodeService.getGameService().numMoves());
    this.backPropagationStack = new BackPropagationStack(50);
  }

  public StateNode<T> getRoot() {
    return root;
  }

  public void expand() {
    StateNode currentNode = root;

    while (!currentNode.getState().isGameOver()) {
      AverageValue averageValue = new AverageValue();

      // If child nodes are visited for the first time, collect average value.
      if (stateNodeService.initChildren(currentNode)) {
        for (StateNode childNode : currentNode.getChildStates()) {
          if (childNode != null) {
            averageValue.add(childNode.getAverageValue());
          }
        }
      }

      backPropagationStack.push(currentNode, averageValue);

      // Move
      currentNode = currentNode.getChildStates()[strategy.suggestMove(currentNode)];
    }

    // Game Over: a leaf should have a winner.
    Player winner = currentNode.getState().getWinner();

    // Draw has no value, otherwise 1 to the winner.
    AverageValue accumulatedValue =
        winner.equals(Player.NONE) ? new AverageValue(0, Player.ONE) : new AverageValue(1, winner);

    // Update leaf node
    currentNode.update(winner, accumulatedValue);

    // Back propagate:
    do {
      accumulatedValue.add(backPropagationStack.getAverageValue());
      backPropagationStack.getStateNode().update(winner, accumulatedValue);
      backPropagationStack.next();
    } while (backPropagationStack.hasNext());
  }

  // Returns number of nodes written. Skips nodes that have less than `minVisits`.
  public long writeTo(TFRecordWriter writer) {
    return stateNodeService.writeTo(getRoot(), writer);
  }

  // Returns number of nodes written. Skips nodes that have less than `minVisits`.
  public void prune(int minVisits) {
    Stack<StateNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      StateNode current = stack.pop();
      if (!current.isLeaf()) {
        if (current.getVisits() > minVisits) {
          for (StateNode child : current.getChildStates()) {
            if (child != null && child.getVisits() > minVisits) {
              stack.push(child);
            }
          }
        } else {
          current.prune();
        }
      }
    }
  }
}
