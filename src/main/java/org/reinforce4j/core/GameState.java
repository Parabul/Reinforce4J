package org.reinforce4j.core;

/** Core game state abstraction. */
public interface GameState<T extends GameState> {

  Player getCurrentPlayer();

  boolean isMoveAllowed(int move);

  // Mutates the state.
  void move(int move);

  boolean isGameOver();

  // Returns NULL if game is not over, returns Player.NONE for games ended in draw (tie).
  Player getWinner();

  // Copies internal state from the other.
  void copy(T other);
}
