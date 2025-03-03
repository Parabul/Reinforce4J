package org.reinforce4j.learning.evaluation;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.evaluation.*;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;
import org.reinforce4j.montecarlo.StateNode;
import org.reinforce4j.montecarlo.StateNodeService;

public class TensorflowEvaluatorTest {

  private static final float TOLERANCE = 0.0001f;

  @Test
  public void shouldEvaluateCorrectly() {

    StateNodeService<TicTacToe> stateNodeService =
        new StateNodeService(
            new TicTacToeService(),
            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)),
            10000,
            30);

    stateNodeService.init();

    TensorflowEvaluator tensorflowBatchEvaluator =
        new TensorflowEvaluator(
            TensorflowEvaluator.TIC_TAC_TOE_V1, stateNodeService.getGameService());

    TicTacToe game1 = stateNodeService.getGameService().newInitialState();
    TicTacToe game2 = stateNodeService.getGameService().newInitialState();
    game2.move(4);
    TicTacToe game3 = stateNodeService.getGameService().newInitialState();
    game3.move(4);
    game3.move(1);

    StateNode<TicTacToe> node1 = stateNodeService.acquire(game1);
    StateNode<TicTacToe> node2 = stateNodeService.acquire(game2);
    StateNode<TicTacToe> node3 = stateNodeService.acquire(game3);

    tensorflowBatchEvaluator.evaluate(node1, node2, null, node3);

    assertThat(node1.evaluation().getValue()).isWithin(TOLERANCE).of(0.3031689f);
    assertThat(node1.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.11732529f,
            0.10184305f,
            0.11379578f,
            0.102521166f,
            0.12674625f,
            0.101940125f,
            0.1172647f,
            0.10257506f,
            0.11598862f)
        .inOrder();

    assertThat(node2.evaluation().getValue()).isWithin(TOLERANCE).of(-0.48276043f);
    assertThat(node2.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.15116668f,
            0.10973671f,
            0.13573483f,
            0.10371558f,
            3.211865E-9f,
            0.10486766f,
            0.13961151f,
            0.11444233f,
            0.14072475f)
        .inOrder();

    assertThat(node3.evaluation().getValue()).isWithin(TOLERANCE).of(0.539513f);
    assertThat(node3.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.15158418f,
            1.4859517E-12f,
            0.16057102f,
            0.14095221f,
            1.7542738E-10f,
            0.14514937f,
            0.13938312f,
            0.11960253f,
            0.14275756f)
        .inOrder();
  }

  @Test
  public void shouldEvaluateCorrectlyRootState() {
    TicTacToeService service = new TicTacToeService();
    TicTacToe root = service.newInitialState();
    StateEvaluation evaluation = new StateEvaluation(service.numMoves());

    GameStateAndEvaluation envelope = GameStateAndEvaluationImpl.create(root, evaluation);

    TensorflowEvaluator tensorflowBatchEvaluator =
        new TensorflowEvaluator(TensorflowEvaluator.TIC_TAC_TOE_V1, service);

    tensorflowBatchEvaluator.evaluate(envelope);

    assertThat(evaluation.getValue()).isWithin(TOLERANCE).of(0.3031689f);
    assertThat(evaluation.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            new float[] {
              0.11732529f,
              0.10184305f,
              0.11379578f,
              0.102521166f,
              0.12674625f,
              0.101940125f,
              0.1172647f,
              0.10257506f,
              0.11598862f
            })
        .inOrder();
  }

  @Test
  public void evaluateManyTimesCorrectly() {
    TicTacToeService service = new TicTacToeService();
    TicTacToe stateOne = service.newInitialState();
    TicTacToe stateTwo = service.newInitialState();
    stateTwo.move(4);

    StateEvaluation evaluationOne = new StateEvaluation(service.numMoves());
    StateEvaluation evaluationTwo = new StateEvaluation(service.numMoves());

    GameStateAndEvaluation envelopeOne = GameStateAndEvaluationImpl.create(stateOne, evaluationOne);
    GameStateAndEvaluation envelopeTwo = GameStateAndEvaluationImpl.create(stateTwo, evaluationTwo);

    TensorflowEvaluator tensorflowBatchEvaluator =
        new TensorflowEvaluator(TensorflowEvaluator.TIC_TAC_TOE_V1, service);

    for (int i = 0; i < 1000; i++) {
      tensorflowBatchEvaluator.evaluate(envelopeOne, envelopeTwo);
    }

    assertThat(envelopeOne.evaluation().getValue()).isWithin(TOLERANCE).of(0.3031689f);
    assertThat(envelopeOne.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            new float[] {
              0.11732529f,
              0.10184305f,
              0.11379578f,
              0.102521166f,
              0.12674625f,
              0.101940125f,
              0.1172647f,
              0.10257506f,
              0.11598862f
            })
        .inOrder();
  }
}
