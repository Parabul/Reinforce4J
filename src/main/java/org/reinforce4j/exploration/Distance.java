package org.reinforce4j.exploration;

import org.reinforce4j.core.GameState;

public interface Distance {
  float between(GameState left, GameState right);
}
