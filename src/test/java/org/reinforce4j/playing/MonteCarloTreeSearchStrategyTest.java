package org.reinforce4j.playing;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;
import org.reinforce4j.montecarlo.MonteCarloTreeSearch;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;

class MonteCarloTreeSearchStrategyTest {

  @Test
  void nextMoveFromRoot() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(200_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    MonteCarloTreeSearchStrategy<TicTacToe> strategy =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);

    int[] moves = new int[9];
    for (int i = 0; i < 10; i++) {
      int nextMove = strategy.nextMove(ImmutableList.of());
      Truth.assertThat(TicTacToeService.INSTANCE.initialState().isMoveAllowed(nextMove)).isTrue();
      moves[nextMove]++;
    }

    Truth.assertThat(moves).asList().containsExactly(0, 0, 0, 0, 10, 0, 0, 0, 0).inOrder();
  }

  @Test
  void nextMoveSecondMove() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(200_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    int n = 100_000;
    // Warmup.
    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    MonteCarloTreeSearchStrategy<TicTacToe> strategy =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);

    int[] moves = new int[9];
    for (int i = 0; i < 10; i++) {
      int nextMove = strategy.nextMove(ImmutableList.of(5));
      Truth.assertThat(TicTacToeService.INSTANCE.initialState().isMoveAllowed(nextMove)).isTrue();
      moves[nextMove]++;
    }

    Truth.assertThat(moves).asList().containsExactly(0, 0, 0, 0, 10, 0, 0, 0, 0).inOrder();
  }

  @Test
  void nextMoveWithSomeHistory() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(200_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    MonteCarloTreeSearchStrategy<TicTacToe> strategy =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);

    int[] moves = new int[9];
    for (int i = 0; i < 10; i++) {
      int nextMove = strategy.nextMove(ImmutableList.of(4, 2, 0));
      Truth.assertThat(TicTacToeService.INSTANCE.initialState().isMoveAllowed(nextMove)).isTrue();
      moves[nextMove]++;
    }

    Truth.assertThat(moves).asList().containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 10).inOrder();
  }

  @Test
  void nextMoveIllegalHistory() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(200_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    MonteCarloTreeSearchStrategy<TicTacToe> strategy =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> strategy.nextMove(ImmutableList.of(4, 4)));

    Truth.assertThat(exception.getMessage()).contains("Move not allowed");
  }
}
