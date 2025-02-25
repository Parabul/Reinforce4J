package org.reinforce4j.learning.evaluation;

import static com.google.common.truth.Truth.assertThat;

import org.reinforce4j.core.GameState;
import org.junit.jupiter.api.Test;
import org.reinforce4j.core.Player;
import org.reinforce4j.core.StateNode;
import org.reinforce4j.core.StateNodeService;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

class ZeroValueUniformEvaluatorTest {

  private static final double TOLERANCE = 0.0001;

  @Test
  void shouldEvaluateRoot() {
    StateNodeService<TicTacToe> stateNodeService =
        new StateNodeService(
            new TicTacToeService(),
            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)),
            10000,
            30);

    stateNodeService.init();

    StateNode<TicTacToe> root = stateNodeService.newRoot();

    ZeroValueUniformEvaluator evaluator =
        new ZeroValueUniformEvaluator(stateNodeService.getGameService().numMoves());
    evaluator.evaluate(root);

    assertThat(root.getAverageValue().getValue(Player.ONE)).isZero();
    float val = 1.0f / 9;
    assertThat(root.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(new float[] {val, val, val, val, val, val, val, val, val});
  }
  //
  //  @Test
  //  void shouldEvaluateNonRoot() {
  //    GameState root = GameRegistry.TIC_TAC_TOE.initialState();
  //    root.move(5);
  //
  //    ZeroValueUniformEvaluator evaluator = new
  // ZeroValueUniformEvaluator(GameRegistry.TIC_TAC_TOE);
  //    StateEvaluation evaluation = evaluator.evaluate(root);
  //
  //    assertThat(evaluation.getValue()).isZero();
  //    float val = 1.0f / 8;
  //    assertThat(evaluation.getMoveValues())
  //        .usingTolerance(TOLERANCE)
  //        .containsExactly(new float[] {val, val, val, val, 0.0f, val, val, val, val});
  //  }
  //
  //  @Test
  //  void shouldEvaluateGameOver() {
  //    GameState root = GameRegistry.TIC_TAC_TOE.initialState();
  //    root.move(5);
  //    root.move(2);
  //    root.move(1);
  //    root.move(4);
  //    root.move(9);
  //
  //    assertThat(root.isGameOver()).isTrue();
  //    ZeroValueUniformEvaluator evaluator = new
  // ZeroValueUniformEvaluator(GameRegistry.TIC_TAC_TOE);
  //    StateEvaluation evaluation = evaluator.evaluate(root);
  //
  //    assertThat(evaluation.getValue()).isZero();
  //    float val = 1.0f / 8;
  //    assertThat(evaluation.getMoveValues())
  //            .usingTolerance(TOLERANCE)
  //            .containsExactly(new float[] {0.0f, 0.0f, 0.25f, 0.0f, 0.0f, 0.25f, 0.25f, 0.25f,
  // 0.0f});
  //  }
}
