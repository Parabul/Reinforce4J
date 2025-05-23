package org.reinforce4j.evaluation;

import static com.google.common.truth.Truth.assertThat;

import ai.onnxruntime.OrtException;
import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class OnnxEvaluatorTest {


  private static final Logger logger = LoggerFactory.getLogger(OnnxEvaluatorTest.class);

  private static final float TOLERANCE = 0.0001f;

  @Test
  public void shouldEvaluateCorrectlyConnect4() throws OrtException {
    OnnxEvaluator<Connect4> evaluator =
        new OnnxEvaluator<>(OnnxEvaluator.CONNECT4_V1, Connect4Service.INSTANCE);

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
        GameStateAndEvaluationImpl.create(
            game1, new StateEvaluation(Connect4Service.INSTANCE.numMoves()));
    GameStateAndEvaluation<Connect4> node2 =
        GameStateAndEvaluationImpl.create(
            game2, new StateEvaluation(Connect4Service.INSTANCE.numMoves()));
    GameStateAndEvaluationImpl<Connect4> node3 =
        GameStateAndEvaluationImpl.create(
            game3, new StateEvaluation(Connect4Service.INSTANCE.numMoves()));

    Stopwatch stopwatch = Stopwatch.createStarted();
    logger.info("Start");
    evaluator.evaluate(node1, null, node3);
    evaluator.evaluate(node2, null);
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
