package org.reinforce4j.playing;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.TicTacToe;

public class EvaluatorsComparatorTest {

  @Test
  public void randomIsNotBetterThanRandom() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(TicTacToe.NUM_MOVES), TicTacToe::new);
    Evaluator incumbent = new ZeroValueUniformEvaluator(TicTacToe.NUM_MOVES);
    Evaluator candidate = new ZeroValueUniformEvaluator(TicTacToe.NUM_MOVES);
    assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isFalse();
    assertThat(evaluatorsComparator.candidateIsBetter(candidate, incumbent)).isFalse();
    assertThat(evaluatorsComparator.candidateIsBetter(candidate, candidate)).isFalse();
    assertThat(evaluatorsComparator.candidateIsBetter(incumbent, incumbent)).isFalse();
  }

  //  @Test
  public void nnEvaluatorBetterThanRandom() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(Connect4.NUM_MOVES), Connect4::new);
    Evaluator incumbent = new ZeroValueUniformEvaluator(TicTacToe.NUM_MOVES);
    Evaluator candidate =
        new OnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1,
            new NumberOfFeatures(Connect4.NUM_FEATURES),
            new NumberOfMoves(Connect4.NUM_MOVES));
    assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }

  // @Test
  public void nnEvaluatorNotBetterThanSelf() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(Connect4.NUM_MOVES), Connect4::new);
    Evaluator incumbent =
        new OnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1,
            new NumberOfFeatures(Connect4.NUM_FEATURES),
            new NumberOfMoves(Connect4.NUM_MOVES));
    Evaluator candidate =
        new OnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1,
            new NumberOfFeatures(Connect4.NUM_FEATURES),
            new NumberOfMoves(Connect4.NUM_MOVES));
    assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isFalse();
  }

  @Test
  public void newVersion() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(Connect4.NUM_MOVES), Connect4::new);
    Evaluator incumbent =
        new GameOverEvaluator(
            new OnnxEvaluator(
                    "/home/anarbek/tmp/connect4/20250804/connect_v0.onnx",
                new NumberOfFeatures(Connect4.NUM_FEATURES),
                new NumberOfMoves(Connect4.NUM_MOVES)));

    Evaluator candidate =
        new GameOverEvaluator(
            new OnnxEvaluator(
                "/home/anarbek/tmp/connect4/20250804/connect_v0_5.onnx",
                new NumberOfFeatures(Connect4.NUM_FEATURES),
                new NumberOfMoves(Connect4.NUM_MOVES)));

    assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }

  //
  //  @Test
  //  public void newVersionv2() {
  //    EvaluatorsComparator evaluatorsComparator =
  //        new EvaluatorsComparator(new NumberOfMoves(Connect4.NUM_MOVES), Connect4::new);
  //    Evaluator candidate =
  //        new OnnxEvaluator(
  //            "/tmp/connect4_test/model_v0.onnx", new NumberOfFeatures(Connect4.NUM_FEATURES), new
  // NumberOfMoves(Connect4.NUM_MOVES));
  //    Evaluator incumbent =
  //        new OnnxEvaluator(
  //            OnnxEvaluator.CONNECT4_V2, new NumberOfFeatures(Connect4.NUM_FEATURES), new
  // NumberOfMoves(Connect4.NUM_MOVES));
  //    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  //  }

  @Test
  public void ticTacToeTensorflowComparison() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(TicTacToe.NUM_MOVES), TicTacToe::new);
    Evaluator incumbent =
        new OnnxEvaluator(
            "/tmp/tic_tac_toe_test/model_v0.onnx",
            new NumberOfFeatures(TicTacToe.NUM_MOVES),
            new NumberOfMoves(TicTacToe.NUM_MOVES));
    Evaluator candidate =
        new OnnxEvaluator(
            "/tmp/tic_tac_toe_test/model_v1.onnx",
            new NumberOfFeatures(TicTacToe.NUM_MOVES),
            new NumberOfMoves(TicTacToe.NUM_MOVES));
    assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }
}
