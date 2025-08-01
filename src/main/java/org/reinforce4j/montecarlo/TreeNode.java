package org.reinforce4j.montecarlo;

import com.google.common.base.MoreObjects;
import java.util.Optional;
import org.reinforce4j.core.*;
import org.reinforce4j.evaluation.EvaluatedGameState;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.StateEvaluation;
import org.reinforce4j.utils.tfrecord.TensorFlowUtils;
import org.tensorflow.example.Example;

// Wrapper around game state that represents a single node in the Monte Carlo Search Tree.
public class TreeNode implements EvaluatedGameState {

  private final GameState state;
  private final StateEvaluation evaluation;
  // Average value of all nodes reached through this node.
  private final AverageValue averageValue;
  // Outcomes observed.
  private final Outcomes outcomes;
  // Array of child state, has size `num_moves`. Unreachable states (moves not allowed) are
  // populated with nulls.
  private final TreeNode[] childStates;
  // True when visited and not pruned.
  private boolean initialized = false;

  public TreeNode(GameState state, int numMoves) {
    this.state = state;
    this.evaluation = new StateEvaluation(numMoves);
    this.childStates = new TreeNode[numMoves];
    this.averageValue = new AverageValue();
    this.outcomes = new Outcomes();
  }

  public void update(Player winner, AverageValue averageValue) {
    this.outcomes.addWinner(winner);
    this.averageValue.add(averageValue);
  }

  /** Initializes child states and evaluates using provided evaluator. */
  public Optional<AverageValue> initChildren(Evaluator evaluator) {
    int numberOfMoves = evaluation().getNumberOfMoves();
    if (isInitialized()) {
      return Optional.empty();
    }
    initialized = true;
    for (int move = 0; move < numberOfMoves; move++) {
      if (!state().isMoveAllowed(move)) {
        continue;
      }
      getChildStates()[move] = new TreeNode(state().move(move), numberOfMoves);
    }

    evaluator.evaluate(getChildStates());

    AverageValue childrenAverageValue = new AverageValue();
    for (int move = 0; move < numberOfMoves; move++) {
      if (!state().isMoveAllowed(move)) {
        continue;
      }
      TreeNode childNode = getChildStates()[move];
      childNode
          .getAverageValue()
          .fromEvaluation(childNode.state().getCurrentPlayer(), childNode.evaluation().getValue());
      childrenAverageValue.add(childNode.getAverageValue());
    }

    return Optional.of(childrenAverageValue);
  }

  @Override
  public StateEvaluation evaluation() {
    return evaluation;
  }

  @Override
  public GameState state() {
    return state;
  }

  public TreeNode[] getChildStates() {
    return childStates;
  }

  public Outcomes getOutcomes() {
    return outcomes;
  }

  public AverageValue getAverageValue() {
    return averageValue;
  }

  public boolean isInitialized() {
    return initialized;
  }

  public boolean isLeaf() {
    return !initialized || state.isGameOver();
  }

  public int getVisits() {
    return outcomes.getTotalOutcomes();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("state", state)
        .add("policy", evaluation())
        .add("averageValue", averageValue)
        .add("outcomes", outcomes)
        .add("initialized", initialized)
        .toString();
  }

  public float[] encode() {
    float[] outputs = new float[childStates.length + 1];
    if (isLeaf()) {
      throw new IllegalStateException("Leaf node can not be encoded");
    }

    outputs[0] = averageValue.getValue(state.getCurrentPlayer());
    float totalVisits = 0;
    for (int move = 0; move < childStates.length; move++) {
      if (childStates[move] == null) {
        continue;
      }

      outputs[move + 1] = childStates[move].getVisits();
      totalVisits += outputs[move + 1];
    }
    if (totalVisits == 0) {
      throw new IllegalStateException("No visits found for " + this);
    }

    for (int move = 0; move < childStates.length; move++) {
      outputs[move + 1] = outputs[move + 1] / totalVisits;
    }

    return outputs;
  }

  public Example toExample() {
    return TensorFlowUtils.toExample(state.encode(), encode());
  }
}
