package org.reinforce4j.games;

import org.reinforce4j.core.GameService;
import org.reinforce4j.core.Player;

public enum TicTacToeService implements GameService<TicTacToe> {
  INSTANCE;

  private static final TicTacToe INITIAL_STATE = new TicTacToe(new byte[3][3], Player.ONE);
  private final int NUM_MOVES = 9;
  private final int NUM_FEATURES = 9;

  @Override
  public TicTacToe newInitialState() {
    return new TicTacToe(new byte[3][3], Player.ONE);
  }

  @Override
  public TicTacToe initialState() {
    return INITIAL_STATE;
  }

  @Override
  public int numMoves() {
    return NUM_MOVES;
  }

  @Override
  public int numFeatures() {
    return NUM_FEATURES;
  }
}
