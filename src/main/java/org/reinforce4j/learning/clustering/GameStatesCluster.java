package org.reinforce4j.learning.clustering;

import java.util.List;
import org.reinforce4j.core.GameState;

/** Interface for Game States clustering. */
public interface GameStatesCluster {

  /** Returns assigned clusters for given game states. Mutates internal state. */
  int[] fit(List<GameState> gameStates, int numClusters);

  /** Returns assigned cluster for a given state, does not mutate internal state. */
  int predict(GameState gameState);
}
