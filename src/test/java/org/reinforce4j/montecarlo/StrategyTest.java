package org.reinforce4j.montecarlo;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.core.Player;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

class StrategyTest {

  @Test
  void suggestMovesEvenlyWhenNoInformation() {
    StateNodeService<TicTacToe> stateNodeService =
        new StateNodeService(new TicTacToeService(), new ZeroValueUniformEvaluator<>(9), 10000, 30);
    stateNodeService.init();

    StateNode<TicTacToe> root = stateNodeService.newRoot();
    stateNodeService.initChildren(root);

    int n = stateNodeService.getGameService().numMoves();

    Strategy strategy = new Strategy(n);

    int[] histogram = new int[n];

    int times = 1000_000;
    for (int i = 0; i < times; i++) {
      histogram[strategy.suggestMove(root)]++;
    }

    float[] dist = new float[n];

    for (int i = 0; i < n; i++) {
      dist[i] = 1.0f * histogram[i] / times;
    }

    assertThat(dist)
        .usingTolerance(0.01)
        .containsExactly(0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11)
        .inOrder();
  }

  @Test
  void suggestMoveWithHigherValue() {
    StateNodeService<TicTacToe> stateNodeService =
        new StateNodeService(new TicTacToeService(), new ZeroValueUniformEvaluator<>(9), 10000, 30);

    stateNodeService.init();

    StateNode<TicTacToe> stateNode = stateNodeService.newRoot();

    stateNodeService.initChildren(stateNode);

    int numMoves = stateNodeService.getGameService().numMoves();
    Strategy strategy = new Strategy(numMoves);

    // Move 5 has some value
    stateNode.getChildStates()[4].getAverageValue().add(new AverageValue(0.2f, Player.ONE));

    int[] histogram = new int[numMoves];

    int n = 1_000_000;
    for (int i = 0; i < n; i++) {

      histogram[strategy.suggestMove(stateNode)]++;
    }

    float[] dist = new float[numMoves];

    for (int i = 0; i < numMoves; i++) {
      dist[i] = 1.0f * histogram[i] / n;
    }

    assertThat(dist)
        .usingTolerance(0.01)
        .containsExactly(0.095, 0.095, 0.095, 0.095, 0.23, 0.095, 0.095, 0.095, 0.095)
        .inOrder();
  }

  @Test
  void suggestMoveWithHigherPriors() {
    StateNodeService<TicTacToe> stateNodeService =
        new StateNodeService(new TicTacToeService(), new ZeroValueUniformEvaluator<>(9), 10000, 30);

    stateNodeService.init();

    StateNode<TicTacToe> stateNode = stateNodeService.newRoot();

    stateNodeService.initChildren(stateNode);

    int numMoves = stateNodeService.getGameService().numMoves();
    Strategy strategy = new Strategy(numMoves);

    stateNode.evaluation().setValue(0.0f);
    float[] priors = new float[] {0.2f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f};

    System.arraycopy(priors, 0, stateNode.evaluation().getPolicy(), 0, numMoves);

    int[] histogram = new int[numMoves];

    int n = 1_000_000;
    for (int i = 0; i < n; i++) {

      histogram[strategy.suggestMove(stateNode)]++;
    }

    float[] dist = new float[numMoves];

    for (int i = 0; i < numMoves; i++) {
      dist[i] = 1.0f * histogram[i] / n;
    }

    assertThat(dist)
        .usingTolerance(0.01)
        .containsExactly(0.77, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028)
        .inOrder();
  }
}
