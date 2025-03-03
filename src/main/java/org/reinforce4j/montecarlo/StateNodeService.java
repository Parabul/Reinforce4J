package org.reinforce4j.montecarlo;

import java.util.*;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.utils.TensorFlowUtils;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;
import org.tensorflow.example.Example;

@SuppressWarnings("unchecked")
public class StateNodeService<T extends GameState> {

  private final Deque<StateNode<T>> pool;
  private final int capacity;

  private final GameService<T> gameService;
  private final Evaluator<T> evaluator;
  private final int minVisits;

  public StateNodeService(
      GameService<T> gameService, Evaluator<T> evaluator, int capacity, int minVisits) {
    this.capacity = capacity;
    this.minVisits = minVisits;
    this.pool = new ArrayDeque<>(capacity);
    this.gameService = gameService;
    this.evaluator = evaluator;
  }

  public void init() {
    for (int i = 0; i < capacity; i++) {
      pool.add(new StateNode<>(gameService.newInitialState(), gameService.numMoves()));
    }
  }

  public StateNode<T> newRoot() {
    StateNode<T> node = acquire(gameService.initialState());
    evaluator.evaluate(node);
    return node;
  }

  public Optional<AverageValue> initChildren(StateNode<T> stateNode) {
    if (stateNode.isInitialized()) {
      return Optional.empty();
    }
    stateNode.setInitialized(true);
    // TODO(Anarbek): Ensure that these are properly deleted at prune
    for (int move = 0; move < gameService.numMoves(); move++) {
      if (!stateNode.state().isMoveAllowed(move)) {
        continue;
      }
      StateNode<T> childNode = acquire(stateNode.state());
      childNode.state().move(move);
      stateNode.getChildStates()[move] = childNode;
    }

    evaluator.evaluate(stateNode.getChildStates());

    AverageValue averageValue = new AverageValue();
    for (int move = 0; move < gameService.numMoves(); move++) {
      if (!stateNode.state().isMoveAllowed(move)) {
        continue;
      }
      StateNode<T> childNode = stateNode.getChildStates()[move];
      childNode
          .getAverageValue()
          .set(childNode.state().getCurrentPlayer(), childNode.evaluation().getValue());
      averageValue.add(childNode.state().getCurrentPlayer(), childNode.evaluation().getValue());
    }

    return Optional.of(averageValue);
  }

  // Returns number of nodes written. Skips nodes that have less than `minVisits`.
  public long writeTo(StateNode<T> root, TFRecordWriter writer) {
    long written = 0;
    Stack<StateNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      StateNode current = stack.pop();
      if (!current.isLeaf()) {
        writer.write(toExample(current));
        written++;
        for (StateNode child : current.getChildStates()) {
          if (child != null && child.getVisits() > minVisits) {
            stack.push(child);
          }
        }
      }
    }

    return written;
  }

  public Example toExample(StateNode<T> stateNode) {
    float[] encodedInput = new float[gameService.numFeatures()];
    stateNode.state().encode(encodedInput);

    float[] encodedOutput = new float[gameService.numMoves() + 1];
    stateNode.encode(encodedOutput);
    return TensorFlowUtils.toExample(encodedInput, encodedOutput);
  }

  /** Traverses the tree and prunes nodes with less than `minVisits` visits. */
  public void traverseAndPrune(StateNode<T> root, int minVisits) {
    Stack<StateNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      StateNode current = stack.pop();
      if (current.isLeaf()) {
        continue;
      }
      if (current.getVisits() > minVisits) {
        for (StateNode child : current.getChildStates()) {
          if (child != null) {
            stack.push(child);
          }
        }
      } else {
        pruneChildNodes(current);
      }
    }
  }

  private void pruneChildNodes(StateNode<T> node) {
    node.setInitialized(false);

    Stack<StateNode> stack = new Stack<>();
    for (int move = 0; move < gameService.numMoves(); move++) {
      if (node.getChildStates()[move] != null) {
        stack.push(node.getChildStates()[move]);
        node.getChildStates()[move] = null;
      }
    }

    while (!stack.isEmpty()) {
      StateNode current = stack.pop();
      if (!current.isLeaf()) {
        for (StateNode child : current.getChildStates()) {
          if (child != null) {
            stack.push(child);
          }
        }
      }
      release(current);
    }
  }

  public StateNode<T> acquire(final T state) {
    return pool.pop().initializeWith(state);
  }

  public void release(final StateNode node) {
    pool.push(node);
  }

  public double getUsage() {
    return 1.0 - ((double) pool.size() / capacity);
  }

  public int getSize() {
    return capacity - pool.size();
  }

  public int getCapacity() {
    return capacity;
  }

  public GameService<T> getGameService() {
    return gameService;
  }
}
