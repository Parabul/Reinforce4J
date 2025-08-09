package org.reinforce4j.evaluation.batch;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.evaluation.*;
import org.reinforce4j.games.Connect4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BatchOnnxEvaluatorTest {

  private static final Logger logger = LoggerFactory.getLogger(BatchOnnxEvaluatorTest.class);

  private static final float TOLERANCE = 0.0001f;

  @Test
  public void shouldEvaluateCorrectlyConnect4() {
    BatchOnnxEvaluator evaluator =
        new BatchOnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1, new NumberOfFeatures(Connect4.NUM_FEATURES), true);

    Connect4 game1 = new Connect4();
    Connect4 game2 = game1.move(3);
    Connect4 game3 = game2.move(3).move(2).move(3).move(1);

    EvaluatedGameState node1 =
        EvaluatedGameStateEnvelope.create(game1, new StateEvaluation(Connect4.NUM_MOVES));
    EvaluatedGameState node2 =
        EvaluatedGameStateEnvelope.create(game2, new StateEvaluation(Connect4.NUM_MOVES));
    EvaluatedGameStateEnvelope node3 =
        EvaluatedGameStateEnvelope.create(game3, new StateEvaluation(Connect4.NUM_MOVES));

    evaluator.evaluate(node1, null, node3);
    logger.info("Start");
    Stopwatch stopwatch = Stopwatch.createStarted();
    evaluator.evaluate(node2, null);
    logger.info("End: " + stopwatch.stop());

    assertThat(node1.evaluation().getValue()).isWithin(TOLERANCE).of(0.30404377f);
    assertThat(node1.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.12571333, 0.13688596, 0.15253145, 0.17350292, 0.15220705, 0.13662983, 0.12252942)
        .inOrder();

    assertThat(node2.evaluation().getValue()).isWithin(TOLERANCE).of(-0.40728894f);
    assertThat(node2.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.11568735, 0.14596543, 0.16507275, 0.1474998, 0.17871197, 0.14142436, 0.10563833)
        .inOrder();

    assertThat(node3.evaluation().getValue()).isWithin(TOLERANCE).of(-0.88298804f);
    assertThat(node3.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.112652324,
            4.5090317E-4,
            7.477606E-4,
            0.80139357,
            0.0841063,
            2.3801999E-4,
            4.1114914E-4)
        .inOrder();
  }

  @Test
  public void shouldEvaluateCorrectlyConnect4Cpu() {
    BatchOnnxEvaluator evaluator =
        new BatchOnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1, new NumberOfFeatures(Connect4.NUM_FEATURES), false);

    Connect4 game1 = new Connect4();
    Connect4 game2 = game1.move(3);
    Connect4 game3 = game2.move(3).move(2).move(3).move(1);

    EvaluatedGameState node1 =
        EvaluatedGameStateEnvelope.create(game1, new StateEvaluation(Connect4.NUM_MOVES));
    EvaluatedGameState node2 =
        EvaluatedGameStateEnvelope.create(game2, new StateEvaluation(Connect4.NUM_MOVES));
    EvaluatedGameStateEnvelope node3 =
        EvaluatedGameStateEnvelope.create(game3, new StateEvaluation(Connect4.NUM_MOVES));

    evaluator.evaluate(node1, null, node3);
    Stopwatch stopwatch = Stopwatch.createStarted();
    logger.info("Start");
    evaluator.evaluate(node2, null);
    logger.info("End: " + stopwatch.stop());

    assertThat(node1.evaluation().getValue()).isWithin(TOLERANCE).of(0.30404377f);
    assertThat(node1.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.12571333, 0.13688596, 0.15253145, 0.17350292, 0.15220705, 0.13662983, 0.12252942)
        .inOrder();

    assertThat(node2.evaluation().getValue()).isWithin(TOLERANCE).of(-0.40728894f);
    assertThat(node2.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.11568735, 0.14596543, 0.16507275, 0.1474998, 0.17871197, 0.14142436, 0.10563833)
        .inOrder();

    assertThat(node3.evaluation().getValue()).isWithin(TOLERANCE).of(-0.88298804f);
    assertThat(node3.evaluation().getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.112652324,
            4.5090317E-4,
            7.477606E-4,
            0.80139357,
            0.0841063,
            2.3801999E-4,
            4.1114914E-4)
        .inOrder();
  }
}
