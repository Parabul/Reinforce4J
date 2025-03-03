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
  public int numFeatures() {
    return 9;
  }
}
