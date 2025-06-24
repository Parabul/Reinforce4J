package org.reinforce4j.montecarlo;

import java.util.*;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;

public class MonteCarloTreeSearch<T extends GameState> {

  private final Deque<StateNode<T>> pool;
  private final NodeSelectionStrategy nodeSelectionStrategy;
  private final BackPropagationStack backPropagationStack;
  private final MonteCarloTreeSearchSettings<T> settings;
  private final GameService<T> gameService;
  private final Evaluator<T> evaluator;
  private StateNode<T> root;

  public MonteCarloTreeSearch(MonteCarloTreeSearchSettings<T> settings) {
    this.gameService = settings.gameService().get();
    this.evaluator = settings.evaluator().get();
    this.settings = settings;
    this.pool = new ArrayDeque<>(settings.nodesPoolCapacity());
    this.nodeSelectionStrategy = new NodeSelectionStrategy(gameService.numMoves());
    this.backPropagationStack = new BackPropagationStack(settings.backPropagationStackCapacity());
  }

  public StateNode<T> getRoot() {
    return root;
  }

  public void expand() {
    expand(root);
  }

  public void expand(StateNode currentNode) {
    while (!currentNode.state().isGameOver()) {
      // If child nodes are visited for the first time, collect average value.
      Optional<AverageValue> childValue = initChildren(currentNode);
      if (childValue.isPresent()) {
        backPropagationStack.push(currentNode, childValue.get());
      } else {
        backPropagationStack.push(currentNode, null);
      }

      // Move
      currentNode = currentNode.getChildStates()[nodeSelectionStrategy.suggestMove(currentNode)];
    }

    // Game Over: a leaf should have a winner.
    Player winner = currentNode.state().getWinner();

    AverageValue accumulatedValue = new AverageValue();
    // Draw has no value, otherwise 1 to the winner.
    accumulatedValue.addWinner(winner);

    // Update leaf node
    currentNode.update(winner, accumulatedValue);

    // Back propagate:
    do {
      if (backPropagationStack.getAverageValue() != null) {
        accumulatedValue.add(backPropagationStack.getAverageValue());
        backPropagationStack.release(backPropagationStack.getAverageValue());
      }
      // Update parents
      backPropagationStack.getStateNode().update(winner, accumulatedValue);
      backPropagationStack.next();
    } while (backPropagationStack.hasNext());

    //    backPropagationStack.release(accumulatedValue);
  }

  public void init() {
    for (int i = 0; i < settings.nodesPoolCapacity(); i++) {
      pool.add(new StateNode<>(gameService.newInitialState(), gameService.numMoves()));
    }
    root = newRoot();
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
    for (int move = 0; move < gameService.numMoves(); move++) {
      if (!stateNode.state().isMoveAllowed(move)) {
        continue;
      }
      StateNode<T> childNode = acquire(stateNode.state());
      childNode.state().move(move);
      stateNode.getChildStates()[move] = childNode;
    }

    evaluator.evaluate(stateNode.getChildStates());

    AverageValue childrenAverageValue = backPropagationStack.acquire();
    for (int move = 0; move < gameService.numMoves(); move++) {
      if (!stateNode.state().isMoveAllowed(move)) {
        continue;
      }
      StateNode<T> childNode = stateNode.getChildStates()[move];
      childNode
          .getAverageValue()
          .fromEvaluation(childNode.state().getCurrentPlayer(), childNode.evaluation().getValue());
      childrenAverageValue.add(childNode.getAverageValue());
    }

    return Optional.of(childrenAverageValue);
  }

  // Traverses the tree from root and dumps all nodes back into pool, optionally writes nodes into
  // `writer`;
  public long writeTo(TFRecordWriter writer) {
    long written = 0;
    Deque<StateNode> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      StateNode current = stack.pop();
      if (!current.isLeaf()) {
        for (StateNode child : current.getChildStates()) {
          if (child != null) {
            stack.push(child);
          }
        }

        if (current.getVisits() >= settings.writeMinVisits()) {
          writer.write(current.toExample());
          written++;
        }
      }

      release(current);
    }

    // Consider other reset options.
    root = newRoot();
    return written;
  }

  /** Traverses the tree and prunes nodes with less than `minVisits` visits. */
  public void prune() {
    Deque<StateNode> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      StateNode current = stack.pop();
      if (current.isLeaf()) {
        continue;
      }
      if (current.getVisits() > settings.pruneMinVisits()) {
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

    Deque<StateNode> stack = new ArrayDeque<>();
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
    try {
      return pool.pop().initializeWith(state);
    } catch (NoSuchElementException e) {
      throw new RuntimeException(
          "Failed to acquire, with capacity: "
              + settings.nodesPoolCapacity()
              + " and size: "
              + getSize()
              + " and usage: "
              + getUsage(),
          e);
    }
  }

  public void release(final StateNode node) {
    pool.push(node);
  }

  public double getUsage() {
    return 1.0 - ((double) pool.size() / settings.nodesPoolCapacity());
  }

  public int getSize() {
    return settings.nodesPoolCapacity() - pool.size();
  }
}
