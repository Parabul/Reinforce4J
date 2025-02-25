package org.reinforce4j.evaluation;

// Represents an evaluation of a game state as viewed from the current player.
public class StateEvaluation {
  // Is number in range [-1; 1] where 1 means the state favors current player and -1 means the state
  // favors the opponent.
  private float value;
  // Policy outcomes for each move. Array of float values between 0 and 1 that sum to 1, has size
  // `num_moves`.
  private float[] policy;

  public StateEvaluation(int numMoves) {
    this.value = 0;
    this.policy = new float[numMoves];
  }

  public void copy(StateEvaluation other) {
    this.value = other.value;
    System.arraycopy(other.policy, 0, this.policy, 0, other.policy.length);
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

  public void setPolicy(float[] policy) {
    this.policy = policy;
  }
}
