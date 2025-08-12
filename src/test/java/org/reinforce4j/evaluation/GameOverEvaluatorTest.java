package org.reinforce4j.evaluation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reinforce4j.games.TicTacToe;

public class GameOverEvaluatorTest {

  @Test
  public void evaluate() {
    Evaluator delegate = Mockito.mock(Evaluator.class);
    GameOverEvaluator evaluator = new GameOverEvaluator(delegate);

    TicTacToe root = new TicTacToe();
    System.out.println(root);
    TicTacToe randomState = root.move(0);
    System.out.println(randomState);
    TicTacToe gameOver = root.move(0).move(3).move(1).move(4).move(2);
    System.out.println(gameOver);

    EvaluatedGameStateEnvelope[] gameStates = {
      EvaluatedGameStateEnvelope.create(root, new StateEvaluation(TicTacToe.NUM_MOVES)),
      EvaluatedGameStateEnvelope.create(randomState, new StateEvaluation(TicTacToe.NUM_MOVES)),
      EvaluatedGameStateEnvelope.create(gameOver, new StateEvaluation(TicTacToe.NUM_MOVES))
    };

    assertThat(root.isGameOver()).isFalse();

    evaluator.evaluate(gameStates);
    assertThat(gameStates[0].state()).isEqualTo(root);
    assertThat(gameStates[0].evaluation().getValue()).isEqualTo(0);
    assertThat(gameStates[0].evaluation().getPolicy())
        .usingTolerance(0.01)
        .containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 0);

    assertThat(gameStates[1].state()).isEqualTo(randomState);
    assertThat(gameStates[1].evaluation().getValue()).isEqualTo(0);
    assertThat(gameStates[1].evaluation().getPolicy())
        .usingTolerance(0.01)
        .containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 0);

    assertThat(gameStates[2].state()).isEqualTo(gameOver);
    assertThat(gameStates[2].evaluation().getValue()).isEqualTo(-1);
    assertThat(gameStates[2].evaluation().getPolicy())
        .usingTolerance(0.01)
        .containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 0)
        .inOrder();
  }
}
