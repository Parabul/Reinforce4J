package org.reinforce4j.exploration;

import org.reinforce4j.core.GameState;

public class ManhattanDistance implements Distance {
  @Override
  public float between(GameState left, GameState right) {
    float[] leftEncoded = left.encode();
    float[] rightEncoded = right.encode();

    float sumOfAbsoluteDifferences = 0.0f;
    for (int i = 0; i < leftEncoded.length; i++) {
      sumOfAbsoluteDifferences += Math.abs(leftEncoded[i] - rightEncoded[i]);
    }

    return sumOfAbsoluteDifferences;
  }
}
