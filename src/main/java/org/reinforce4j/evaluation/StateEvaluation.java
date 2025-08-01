package org.reinforce4j.evaluation;

import com.google.common.base.MoreObjects;
import java.util.Arrays;

// Represents an evaluation of a game state as viewed from the current player.
public class StateEvaluation {
  // Policy outcomes for each move. Array of float values between 0 and 1 that sum to 1, has size
  // `num_moves`.
  private final float[] policy;
  // Is number in range [-1; 1] where 1 means the state favors current player and -1 means the state
  // favors the opponent.
  private float value;

  public StateEvaluation(int numberOfMoves) {
    value = 0;
    policy = new float[numberOfMoves];
  }

  public int getNumberOfMoves() {
    return policy.length;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public float[] getPolicy() {
    return policy;
  }

  public void reset() {
    value = 0;
    Arrays.fill(policy, 0);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("value", value).add("policy", policy).toString();
  }
}
