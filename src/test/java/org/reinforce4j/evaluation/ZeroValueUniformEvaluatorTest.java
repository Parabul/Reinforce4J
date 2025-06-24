package org.reinforce4j.evaluation;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

class ZeroValueUniformEvaluatorTest {

  private static final double TOLERANCE = 0.0001;

  @Test
  void shouldEvaluateRoot() {
    TicTacToeService service = TicTacToeService.INSTANCE;
    TicTacToe root = service.newInitialState();
    StateEvaluation evaluation = new StateEvaluation(service.numMoves());

    GameStateAndEvaluation envelope = GameStateAndEvaluationEnvelope.create(root, evaluation);

    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(service.numMoves());
    evaluator.evaluate(envelope);

    assertThat(evaluation.getValue()).isZero();
    float val = 1.0f / 9;
    assertThat(evaluation.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(new float[] {val, val, val, val, val, val, val, val, val});
  }

  @Test
  void shouldEvaluateNonRoot() {
    TicTacToeService service = TicTacToeService.INSTANCE;
    TicTacToe root = service.newInitialState();
    root.move(4);
    StateEvaluation evaluation = new StateEvaluation(service.numMoves());

    GameStateAndEvaluation envelope = GameStateAndEvaluationEnvelope.create(root, evaluation);

    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(service.numMoves());
    evaluator.evaluate(envelope);

    assertThat(evaluation.getValue()).isZero();
    float val = 1.0f / 9;
    assertThat(evaluation.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(new float[] {val, val, val, val, val, val, val, val, val});
  }
}
