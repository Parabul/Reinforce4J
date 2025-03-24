package org.reinforce4j.utils;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

class RandomStateGeneratorTest {

  @Test
  void nextGeneratesNonRootState() {
    RandomStateGenerator<TicTacToe> generator =
        new RandomStateGenerator<>(TicTacToeService.INSTANCE, 4);
    Set<TicTacToe> states = new HashSet<>();
    for (int i = 0; i < 1000; i++) {
      List<Integer> history = generator.next();
      assertThat(history.size()).isGreaterThan(0);
      assertThat(history.size()).isAtMost(4);
    }

    assertThat(states.size()).isGreaterThan(400);
  }
}
