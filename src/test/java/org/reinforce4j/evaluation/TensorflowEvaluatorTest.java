package org.reinforce4j.evaluation;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TensorflowEvaluatorTest {

  private static final float TOLERANCE = 0.0001f;
  private static final Logger logger = LoggerFactory.getLogger(TensorflowEvaluatorTest.class);


  @Test
  public void shouldEvaluateCorrectly() {
    TensorflowEvaluator tensorflowBatchEvaluator =
        new TensorflowEvaluator(TensorflowEvaluator.TIC_TAC_TOE_V1, TicTacToeService.INSTANCE);

    TicTacToe game1 = TicTacToeService.INSTANCE.newInitialState();
    TicTacToe game2 = TicTacToeService.INSTANCE.newInitialState();
    game2.move(4);
    TicTacToe game3 = TicTacToeService.INSTANCE.newInitialState();
    game3.move(4);
    game3.move(1);

    GameStateAndEvaluation<TicTacToe> node1 =
        GameStateAndEvaluationEnvelope.create(
            game1, new StateEvaluation(TicTacToeService.INSTANCE.numMoves()));
    GameStateAndEvaluation<TicTacToe> node2 =
        GameStateAndEvaluationEnvelope.create(
            game2, new StateEvaluation(TicTacToeService.INSTANCE.numMoves()));
    GameStateAndEvaluation<TicTacToe> node3 =
        GameStateAndEvaluationEnvelope.create(
            game3, new StateEvaluation(TicTacToeService.INSTANCE.numMoves()));

    Stopwatch stopwatch = Stopwatch.createStarted();
    logger.info("Start");
    tensorflowBatchEvaluator.evaluate(node1, node2, null, node3);
    logger.info("End: " + stopwatch.stop());

    assertThat(node1.evaluation().getValue()).isWithin(TOLERANCE).of(0.3031689f);
    assertThat(node1.evaluation().getPolicy())
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
    assertThat(node2.evaluation().getPolicy())
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
    assertThat(node3.evaluation().getPolicy())
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
    TicTacToeService service = TicTacToeService.INSTANCE;
    TicTacToe root = service.newInitialState();
    StateEvaluation evaluation = new StateEvaluation(service.numMoves());

    GameStateAndEvaluation envelope = GameStateAndEvaluationEnvelope.create(root, evaluation);

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
    TicTacToeService service = TicTacToeService.INSTANCE;
    TicTacToe stateOne = service.newInitialState();
    TicTacToe stateTwo = service.newInitialState();
    stateTwo.move(4);

    StateEvaluation evaluationOne = new StateEvaluation(service.numMoves());
    StateEvaluation evaluationTwo = new StateEvaluation(service.numMoves());

    GameStateAndEvaluation envelopeOne = GameStateAndEvaluationEnvelope.create(stateOne, evaluationOne);
    GameStateAndEvaluation envelopeTwo = GameStateAndEvaluationEnvelope.create(stateTwo, evaluationTwo);

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

  @Test
  public void shouldEvaluateCorrectlyConnect4() {
    TensorflowEvaluator<Connect4> tensorflowBatchEvaluator =
        new TensorflowEvaluator<>(TensorflowEvaluator.CONNECT4_V1, Connect4Service.INSTANCE);

    Connect4 game1 = Connect4Service.INSTANCE.newInitialState();
    Connect4 game2 = Connect4Service.INSTANCE.newInitialState();
    game2.move(3);
    Connect4 game3 = Connect4Service.INSTANCE.newInitialState();
    game3.move(3);
    game3.move(3);
    game3.move(2);
    game3.move(3);
    game3.move(1);

    GameStateAndEvaluation<Connect4> node1 =
        GameStateAndEvaluationEnvelope.create(
            game1, new StateEvaluation(Connect4Service.INSTANCE.numMoves()));
    GameStateAndEvaluation<Connect4> node2 =
        GameStateAndEvaluationEnvelope.create(
            game2, new StateEvaluation(Connect4Service.INSTANCE.numMoves()));
    GameStateAndEvaluationEnvelope<Connect4> node3 =
        GameStateAndEvaluationEnvelope.create(
            game3, new StateEvaluation(Connect4Service.INSTANCE.numMoves()));

    Stopwatch stopwatch = Stopwatch.createStarted();
    logger.info("Start");
    tensorflowBatchEvaluator.evaluate(node1, null, node3);
    tensorflowBatchEvaluator.evaluate(node2, null);
    logger.info("End: " + stopwatch.stop());

    assertThat(node1.evaluation().getValue()).isWithin(TOLERANCE).of(0.21751882f);
    assertThat(node1.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
                0.11898205, 0.14070407, 0.1519281, 0.1711144, 0.15199628, 0.14285956, 0.12241552)
        .inOrder();

    assertThat(node2.evaluation().getValue()).isWithin(TOLERANCE).of(-0.28411743f);
    assertThat(node2.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
                0.10639307, 0.14400896, 0.16923025, 0.16191941, 0.16771331, 0.13361344, 0.1171216)
        .inOrder();

    assertThat(node3.evaluation().getValue()).isWithin(TOLERANCE).of(-0.90172696f);
    assertThat(node3.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
                0.15628283, 0.0015531139, 0.0010194125, 0.6490803, 0.19007017, 9.985811E-4, 9.955459E-4)
        .inOrder();
  }
}
