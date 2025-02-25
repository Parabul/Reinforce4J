package org.reinforce4j.games;

import org.reinforce4j.core.GameService;
import org.reinforce4j.core.Player;

public class Connect4Service implements GameService<Connect4> {

  private static final Connect4 INITIAL_STATE = new Connect4(new int[6][7], Player.ONE);

  @Override
  public int numMoves() {
    return 7;
  }

  @Override
  public int numFeatures() {
    return 42;
  }

  @Override
  public Connect4 newInitialState() {
    return new Connect4(new int[6][7], Player.ONE);
  }

  @Override
  public Connect4 initialState() {
    return INITIAL_STATE;
  }

  @Override
  public float[] encode(Connect4 state) {
    float[] features = new float[numFeatures()];
    int index = 0;
    for (int[] row : state.getBoard()) {
      for (int cell : row) {
        features[index++] = cell;
      }
    }
    return features;
  }
}
