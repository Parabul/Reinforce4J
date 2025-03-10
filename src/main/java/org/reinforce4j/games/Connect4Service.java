package org.reinforce4j.games;

import org.reinforce4j.core.GameService;
import org.reinforce4j.core.Player;

public enum Connect4Service implements GameService<Connect4> {
  INSTANCE;

  private static final Connect4 INITIAL_STATE = new Connect4(new int[6][7], Player.ONE);

  private final int NUM_MOVES = 7;
  private final int NUM_FEATURES = 42;

  @Override
  public int numMoves() {
    return NUM_MOVES;
  }

  @Override
  public int numFeatures() {
    return NUM_FEATURES;
  }

  @Override
  public Connect4 newInitialState() {
    return new Connect4(new int[6][7], Player.ONE);
  }

  @Override
  public Connect4 initialState() {
    return INITIAL_STATE;
  }
}
