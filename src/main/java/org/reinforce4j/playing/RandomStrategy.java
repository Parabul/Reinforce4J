package org.reinforce4j.playing;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.core.GameState;

public class RandomStrategy implements StateBasedStrategy {

  private final UniformRandomProvider random = RandomSource.XO_RO_SHI_RO_128_PP.create();
  private final int[] allowedMoves;

  public RandomStrategy(NumberOfMoves numberOfMoves) {
    this.allowedMoves = new int[numberOfMoves.value()];
  }

  @Override
  public int nextMove(GameState state) {
    int numAllowedMoves = 0;
    for (int i = 0; i < allowedMoves.length; i++) {
      if (state.isMoveAllowed(i)) {
        allowedMoves[numAllowedMoves++] = i;
      }
    }

    int option = random.nextInt(numAllowedMoves);

    return allowedMoves[option];
  }
}
