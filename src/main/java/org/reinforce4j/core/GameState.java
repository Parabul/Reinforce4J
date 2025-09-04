package org.reinforce4j.core;

/** Core game state abstraction. */
public interface GameState {

  Player getCurrentPlayer();

  boolean isMoveAllowed(int move);

  boolean isGameOver();

  // Returns NULL if game is not over, returns Player.NONE for games ended in draw (tie).
  Player getWinner();

  GameState move(int move);

  // TODO(anarbek): Move to Encoder interface
  float[] encode();

  Player getPotentialWinner();
}
