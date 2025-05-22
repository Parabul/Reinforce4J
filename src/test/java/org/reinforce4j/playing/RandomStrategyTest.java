package org.reinforce4j.playing;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

class RandomStrategyTest {

  @Test
  void nextMoveFromRootUniform() {
    HistoryBasedStrategy<TicTacToe> strategy = new RandomStrategy<>(TicTacToeService.INSTANCE);

    int n = 1000_000;
    int[] moves = new int[9];
    for (int i = 0; i < n; i++) {
      moves[strategy.nextMove(ImmutableList.of())]++;
    }

    float[] histogram = new float[9];
    for (int i = 0; i < 9; i++) {
      histogram[i] = (float) moves[i] / n;
    }

    Truth.assertThat(histogram)
        .usingTolerance(0.01)
        .containsExactly(0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11);
  }

  @Test
  void allowedMovesOnly() {
    HistoryBasedStrategy<TicTacToe> strategy = new RandomStrategy<>(TicTacToeService.INSTANCE);

    int n = 1000_000;
    int[] moves = new int[9];
    for (int i = 0; i < n; i++) {
      moves[strategy.nextMove(ImmutableList.of(4))]++;
    }

    float[] histogram = new float[9];
    for (int i = 0; i < 9; i++) {
      histogram[i] = (float) moves[i] / n;
    }

    Truth.assertThat(histogram)
        .usingTolerance(0.01)
        .containsExactly(0.12, 0.12, 0.12, 0.0, 0.12, 0.12, 0.12, 0.12, 0.12);
  }
}
