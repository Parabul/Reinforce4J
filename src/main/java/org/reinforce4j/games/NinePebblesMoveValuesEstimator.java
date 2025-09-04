package org.reinforce4j.games;

import org.reinforce4j.core.Player;
import org.reinforce4j.learning.heuristics.MoveValueEstimator;

public class NinePebblesMoveValuesEstimator implements MoveValueEstimator<NinePebbles> {
  @Override
  public float[] estimateMoveValues(NinePebbles state) {
    float[] values = new float[NinePebbles.NUM_MOVES];
    float parentDiff =
        state.getCurrentPlayer().equals(Player.ONE)
            ? state.getScoreOne() - state.getScoreTwo()
            : state.getScoreTwo() - state.getScoreOne();
    for (int i = 0; i < NinePebbles.NUM_MOVES; i++) {
      if (!state.isMoveAllowed(i)) {
        continue;
      }
      NinePebbles childState = state.move(i);
      float childDiff =
          state.getCurrentPlayer().equals(Player.ONE)
              ? childState.getScoreOne() - childState.getScoreTwo()
              : childState.getScoreTwo() - childState.getScoreOne();
      values[i] = (childDiff - parentDiff) / 81f;
    }
    return values;
  }
}
