package org.reinforce4j.learning.heuristics;

import org.reinforce4j.core.GameState;

public interface MoveValueEstimator<T extends GameState> {
  float[] estimateMoveValues(T state);
}
