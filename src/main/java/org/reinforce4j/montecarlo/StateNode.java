package org.reinforce4j.montecarlo;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import org.reinforce4j.core.*;
import org.reinforce4j.evaluation.GameStateAndEvaluation;
import org.reinforce4j.evaluation.StateEvaluation;
import org.reinforce4j.utils.tfrecord.TensorFlowUtils;
import org.tensorflow.example.Example;

@SuppressWarnings("unchecked")
// Wrapper around game state that represents a single node in the Monte Carlo Search Tree.
public class StateNode<T extends GameState> implements GameStateAndEvaluation<T> {

  // Since the output could be all zeros (when all moves result in loss), we add small epsilon for
  // each output to keep numerical operations safe.
  private static final float EPSILON = 1E-6f;

  // Array of child state, has size `num_moves`. Unreachable states (moves not allowed) are
  // populated with zeros.
  private final StateNode<T>[] childStates;
  private final T state;

  private final StateEvaluation evaluation;
  // Observations:
  // Total value of all nodes reached through this node.
  private final AverageValue averageValue;
  // Unique identification for object pooling.
  // Outcomes observed:
  private final Outcomes outcomes;
  // True when visited and not pruned.
  private boolean initialized = false;

  public StateNode(T initialState, int numMoves) {
    this.state = initialState;
    this.evaluation = new StateEvaluation(numMoves);
    this.childStates = new StateNode[numMoves];
    this.averageValue = new AverageValue();
    this.outcomes = new Outcomes();
  }

  @Override
  public StateEvaluation evaluation() {
    return evaluation;
  }

  public Outcomes getOutcomes() {
    return outcomes;
  }

  @Override
  public T state() {
    return state;
  }

  public StateNode<T> initializeWith(T state) {
    this.state.copy(state);
    evaluation.reset();
    averageValue.reset();
    outcomes.reset();
    this.initialized = false;
    Arrays.fill(childStates, null);
    return this;
  }

  public StateNode<T>[] getChildStates() {
    return childStates;
  }

  public StateNode<T> copy(StateNode<T> other) {
    this.state.copy(other.state);
    this.initialized = other.initialized;

    System.arraycopy(other.childStates, 0, this.childStates, 0, this.childStates.length);

    this.evaluation.copy(other.evaluation);
    this.averageValue.copy(other.averageValue);
    this.outcomes.copy(other.outcomes);

    return this;
  }

  public void update(Player winner, AverageValue averageValue) {
    this.outcomes.addWinner(winner);
    this.averageValue.add(averageValue);
  }

  public boolean isInitialized() {
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

  public boolean isLeaf() {
    return !initialized || state.isGameOver();
  }

  public long getVisits() {
    return outcomes.getTotalOutcomes();
  }

  public AverageValue getAverageValue() {
    return averageValue;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("state", state)
        .add("policy", evaluation())
        .add("averageValue", averageValue)
        .add("outcomes", outcomes)
        .add("initialized", initialized)
        //            .add(
        //                    "childStates",
        //                    Arrays.stream(childStates)
        //                            .filter(ch -> ch != null)
        //                            .map(ch -> ch.outcomes)
        //                            .collect(Collectors.toList()))
        .toString();
  }

  public float[] encode() {
    float[] outputs = new float[childStates.length + 1];
    if (isLeaf()) {
      throw new IllegalStateException("Leaf node can not be encoded");
    }

    outputs[0] = outcomes.valueFor(state.getCurrentPlayer());
    float sum = 0;
    for (int move = 0; move < childStates.length; move++) {
      if (childStates[move] == null) {
        continue;
      }

      outputs[move + 1] = childStates[move].outcomes.winRateFor(state.getCurrentPlayer()) + EPSILON;
      sum += outputs[move + 1];
    }

    // TODO: Revisit. Check how to handle states with all zero values moves.
    for (int move = 0; move < childStates.length; move++) {
      outputs[move + 1] = outputs[move + 1] / sum;
    }

    return outputs;
  }

  public Example toExample() {
    return TensorFlowUtils.toExample(state.encode(), encode());
  }
}
