package org.reinforce4j.games;

import org.reinforce4j.core.GameService;
import org.reinforce4j.core.Player;

public class TicTacToeService implements GameService<TicTacToe> {
  private static final TicTacToe INITIAL_STATE = new TicTacToe(new int[3][3], Player.ONE);

  @Override
  public TicTacToe newInitialState() {
    return new TicTacToe(new int[3][3], Player.ONE);
  }

  @Override
  public TicTacToe initialState() {
    return INITIAL_STATE;
  }

  @Override
  public int numMoves() {
    return 9;
  }

  @Override
  public float[] encode(TicTacToe state) {
    float[] features = new float[numFeatures()];
    for (int move = 1; move <= numMoves(); move++) {
      int i = (move - 1) / 3;
      int j = (move - 1) % 3;

      features[move - 1] = state.getCells()[i][j];
    }
    return features;
  }

  @Override
  public int numFeatures() {
    return 9;
  }
}
