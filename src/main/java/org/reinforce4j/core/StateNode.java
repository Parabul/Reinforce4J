package org.reinforce4j.core;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.reinforce4j.evaluation.GameStateAndEvaluation;
import org.reinforce4j.evaluation.StateEvaluation;
import org.reinforce4j.utils.TensorFlowUtils;
import org.tensorflow.example.Example;
import org.tensorflow.example.Feature;
import org.tensorflow.example.Features;
import org.tensorflow.example.FloatList;

@SuppressWarnings("unchecked")
// Wrapper around game state that represents a single node in the Monte Carlo Search Tree.
public class StateNode<T extends GameState> implements GameStateAndEvaluation<T> {

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
  private int id;
  // True when visited and not pruned.
  private boolean initialized = false;

  public StateNode(int id, T initialState, int numMoves) {
    this.id = id;
    this.state = initialState;
    this.evaluation = new StateEvaluation(numMoves);
    this.childStates = new StateNode[numMoves];
    this.averageValue = new AverageValue();
    this.outcomes = new Outcomes();
  }

  @Override
  public StateEvaluation getEvaluation() {
    return evaluation;
  }

  public Outcomes getOutcomes() {
    return outcomes;
  }

  @Override
  public T getState() {
    return state;
  }

  public StateNode<T> initializeWith(T state) {
    this.state.copy(state);
    // Resetting of fields should be done at pruning time.
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

  public boolean isInitialized() {
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

  public int getId() {
    return id;
  }

  public StateNode<T> setId(int id) {
    this.id = id;
    return this;
  }

  public void update(Player winner, AverageValue averageValue) {
    this.outcomes.addWinner(winner);
    this.averageValue.add(averageValue);
  }

  public boolean prune() {
    if (!initialized) {
      return false;
    }
    initialized = false;

    for (int i = 0; i < childStates.length; i++) {
      childStates[i] = null;
    }

    return true;
  }

  public boolean isLeaf() {
    return !initialized || state.isGameOver();
  }

  public float[] getPolicy() {
    return getEvaluation().getPolicy();
  }

  public long getVisits() {
    return outcomes.getTotalOutcomes();
  }

  public Example toExample(GameService<T> gameService) {
    if (isLeaf()) {
      throw new IllegalStateException("Leaf nodes have no moves");
    }
    FloatList.Builder outcomeFeature =
        FloatList.newBuilder().addValue(outcomes.valueFor(state.getCurrentPlayer()));

    float[] values = new float[childStates.length];
    float sum = 0;
    for (int move = 0; move < childStates.length; move++) {
      if (childStates[move] == null) {
        continue;
      }

      values[move] = childStates[move].outcomes.valueFor(state.getCurrentPlayer());
      sum += values[move];
    }

    // TODO(anarbek): Revisit. Check how to handle states all zero values moves.
    for (float value : values) {
      outcomeFeature.addValue(sum > 0 ? value / sum : 0);
    }

    return Example.newBuilder()
        .setFeatures(
            Features.newBuilder()
                .putFeature("input", TensorFlowUtils.floatList(gameService.encode(state)))
                .putFeature("output", Feature.newBuilder().setFloatList(outcomeFeature).build()))
        .build();
  }

  public AverageValue getAverageValue() {
    return averageValue;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add(
            "childStates",
            Arrays.stream(childStates)
                .filter(ch -> ch != null)
                .map(ch -> ch.outcomes)
                .collect(Collectors.toList()))
        .add("state", state)
        .add("policy", getEvaluation())
        .add("averageValue", averageValue)
        .add("outcomes", outcomes)
        .add("id", id)
        .add("initialized", initialized)
        .toString();
  }
}
