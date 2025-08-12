package org.reinforce4j.evaluation;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("value", value).add("policy", policy).toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    StateEvaluation that = (StateEvaluation) o;
    return Float.compare(value, that.value) == 0 && Arrays.equals(policy, that.policy);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(policy, value);
  }
}
