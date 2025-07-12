package org.reinforce4j.evaluation;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.games.TicTacToe;

class ZeroValueUniformEvaluatorTest {

  private static final double TOLERANCE = 0.0001;

  @Test
  void shouldEvaluateRoot() {
    TicTacToe root = new TicTacToe();
    StateEvaluation evaluation = new StateEvaluation(9);

    EvaluatedGameState envelope = EvaluatedGameStateEnvelope.create(root, evaluation);

    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(9);
    evaluator.evaluate(envelope);

    assertThat(evaluation.getValue()).isZero();
    float val = 1.0f / 9;
    assertThat(evaluation.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(new float[] {val, val, val, val, val, val, val, val, val});
  }

  @Test
  void shouldEvaluateNonRoot() {

    TicTacToe root = new TicTacToe();
    root.move(4);
    StateEvaluation evaluation = new StateEvaluation(9);

    EvaluatedGameState envelope = EvaluatedGameStateEnvelope.create(root, evaluation);

    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(9);
    evaluator.evaluate(envelope);

    assertThat(evaluation.getValue()).isZero();
    float val = 1.0f / 9;
    assertThat(evaluation.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(new float[] {val, val, val, val, val, val, val, val, val});
  }
}
