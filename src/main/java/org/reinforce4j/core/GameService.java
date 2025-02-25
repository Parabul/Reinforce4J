package org.reinforce4j.core;

public interface GameService<T extends GameState> {

  /** Returns a new instance of the initial state of the game. */
  T newInitialState();

  /** Returns a reference to the instance of the initial state of the game. */
  T initialState();

  // Number of alternative moves in the game [1, N];
  int numMoves();

  float[] encode(T state);

  // Number of features is the length of the encoded array.
  int numFeatures();
}
