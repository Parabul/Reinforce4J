package org.reinforce4j.core;

import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;

import java.util.Stack;

@SuppressWarnings("unchecked")
public class StateNodeService<T extends GameState> {

  private final StateNode<T>[] pool;
  private final int capacity;

  private final GameService<T> gameService;
  private final Evaluator<T> evaluator;
  private final int minVisits;
  private int pointer;

  public StateNodeService(
      GameService<T> gameService, Evaluator<T> evaluator, int capacity, int minVisits) {
    this.capacity = capacity;
    this.minVisits = minVisits;
    this.pool = new StateNode[capacity];
    this.pointer = 0;
    this.gameService = gameService;
    this.evaluator = evaluator;
  }

  public void init() {
    for (int i = 0; i < capacity; i++) {
      pool[i] = new StateNode<T>(i, gameService.newInitialState(), gameService.numMoves());
    }
    pointer = -1;
  }

  public StateNode<T> newRoot() {
    return create(gameService.initialState());
  }

  public boolean initChildren(StateNode<T> stateNode) {
    if (stateNode.isInitialized()) {
      return false;
    }
    stateNode.setInitialized(true);
    // TODO(Anarbek): Ensure that these are properly deleted at prune
    for (int move = 0; move < gameService.numMoves(); move++) {
      if (!stateNode.getState().isMoveAllowed(move)) {
        continue;
      }
      StateNode<T> childNode = create(stateNode.getState());
      childNode.getState().move(move);
      stateNode.getChildStates()[move] = childNode;
    }

    evaluator.evaluate(stateNode.getChildStates());

    return true;
  }

  // Returns number of nodes written. Skips nodes that have less than `minVisits`.
  public long writeTo(StateNode<T> root, TFRecordWriter writer) {
    long written = 0;
    Stack<StateNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      StateNode current = stack.pop();
      if (!current.isLeaf()) {
        writer.write(current.toExample(gameService));
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

  public StateNode<T> create(final T state) {
    return pool[++pointer].initializeWith(state).setId(pointer);
  }

  public void delete(final StateNode buffer) {
    pool[buffer.getId()].copy(pool[--pointer]).setId(buffer.getId());
  }

  public double getUsage() {
    return 1.0 * pointer / capacity;
  }

  public int getCapacity() {
    return capacity;
  }

  public GameService<T> getGameService() {
    return gameService;
  }
}
