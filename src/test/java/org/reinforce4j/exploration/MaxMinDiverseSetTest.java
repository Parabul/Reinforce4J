package org.reinforce4j.exploration;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.utils.TestUtils;

public class MaxMinDiverseSetTest {

  @Test
  public void testOffer() {
    MaxMinDiverseSet diverseSet = new MaxMinDiverseSet(1000, new EuclideanDistance());
    for (int i = 0; i < 1_000_000; i++) {
      TicTacToe state = TestUtils.getRandomTicTacToeState();
      assertThat(state).isNotNull();
      diverseSet.offer(state);
    }

    assertThat(diverseSet.get()).hasSize(1000);
    System.out.println(diverseSet.getLeastDiverse().getFirst());
    System.out.println(diverseSet.getLeastDiverse().getSecond());
  }
}
