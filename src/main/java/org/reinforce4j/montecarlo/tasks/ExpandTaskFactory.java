package org.reinforce4j.montecarlo.tasks;

import org.reinforce4j.core.GameState;

/** Factory class that generates Tasks executed against SearchTree. */
public interface ExpandTaskFactory {
  ExpandTask create(GameState gameState, int numExpansions);
}
