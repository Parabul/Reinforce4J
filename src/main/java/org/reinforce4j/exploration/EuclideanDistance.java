package org.reinforce4j.exploration;

import org.reinforce4j.core.GameState;

public class EuclideanDistance implements Distance {
  @Override
  public float between(GameState left, GameState right) {
    float[] leftEncoded = left.encode();
    float[] rightEncoded = right.encode();

    double sumOfSquares = 0.0;
    for (int i = 0; i < leftEncoded.length; i++) {
      float difference = leftEncoded[i] - rightEncoded[i];
      sumOfSquares += difference * difference;
    }

    return (float) Math.sqrt(sumOfSquares);
  }
}
