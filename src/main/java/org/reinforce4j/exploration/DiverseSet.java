package org.reinforce4j.exploration;

import java.util.Collection;
import java.util.Set;
import org.reinforce4j.core.GameState;

/**
 * Set that maintains the diversity (as minimal distance between any two elements) of the set of
 * Game States observed.
 */
public interface DiverseSet {

  /** Returns true if a given state is added to the set, false otherwise. */
  boolean offer(GameState gameState);

  default boolean offerAll(Collection<GameState> gameStates) {
    boolean taken = false;
    for (final GameState gameState : gameStates) {
      if (offer(gameState)) {
        taken = true;
      }
    }
    return taken;
  }

  /** Returns the set accumulated so far. */
  Set<GameState> get();
}
