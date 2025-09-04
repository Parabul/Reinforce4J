package org.reinforce4j.exploration;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.TicTacToe;

public class RandomWalkTest {

  @Test
  public void shouldReturnNonEmptyList() {
    RandomStateGenerator randomWalk = new RandomWalk(new TicTacToe(), TicTacToe.NUM_MOVES);

    for (int i = 0; i < 1000; i++) {
      Truth.assertThat(randomWalk.get().size()).isAtLeast(3);

    }
  }

  @Test
  public void shouldReturnNonEmptyListConnect4() {
    RandomStateGenerator randomWalk = new RandomWalk(new Connect4(), Connect4.NUM_MOVES);

    for (int i = 0; i < 1000; i++) {
      Truth.assertThat(randomWalk.get().size()).isAtLeast(6);

    }
  }
}
