package org.reinforce4j.playing;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.TicTacToe;

public class EvaluatorsComparatorTest {

  @Test
  public void randomIsNotBetterThanRandom() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(9), TicTacToe::new);
    Evaluator incumbent = new ZeroValueUniformEvaluator(9);
    Evaluator candidate = new ZeroValueUniformEvaluator(9);
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isFalse();
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(candidate, incumbent)).isFalse();
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(candidate, candidate)).isFalse();
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, incumbent)).isFalse();
  }

  //  @Test
  public void nnEvaluatorBetterThanRandom() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(7), Connect4::new);
    Evaluator incumbent = new ZeroValueUniformEvaluator(9);
    Evaluator candidate =
        new OnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1, new NumberOfFeatures(42), new NumberOfMoves(7));
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }

  // @Test
  public void nnEvaluatorNotBetterThanSelf() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(7), Connect4::new);
    Evaluator incumbent =
        new OnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1, new NumberOfFeatures(42), new NumberOfMoves(7));
    Evaluator candidate =
        new OnnxEvaluator(
            OnnxEvaluator.CONNECT4_V1, new NumberOfFeatures(42), new NumberOfMoves(7));
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isFalse();
  }

//  @Test
//  public void newVersion() {
//    EvaluatorsComparator evaluatorsComparator =
//        new EvaluatorsComparator(new NumberOfMoves(7), Connect4::new);
//    Evaluator candidate =
//        new OnnxEvaluator(
//            OnnxEvaluator.CONNECT4_V0, new NumberOfFeatures(42), new NumberOfMoves(7));
//    Evaluator incumbent =
//        new OnnxEvaluator(
//            OnnxEvaluator.CONNECT4_V1, new NumberOfFeatures(42), new NumberOfMoves(7));
//    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
//  }
//
//  @Test
//  public void newVersionv2() {
//    EvaluatorsComparator evaluatorsComparator =
//        new EvaluatorsComparator(new NumberOfMoves(7), Connect4::new);
//    Evaluator candidate =
//        new OnnxEvaluator(
//            "/tmp/connect4_test/model_v0.onnx", new NumberOfFeatures(42), new NumberOfMoves(7));
//    Evaluator incumbent =
//        new OnnxEvaluator(
//            OnnxEvaluator.CONNECT4_V2, new NumberOfFeatures(42), new NumberOfMoves(7));
//    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
//  }

  @Test
  public void ticTacToeTensorflowComparison() {
    EvaluatorsComparator evaluatorsComparator =
        new EvaluatorsComparator(new NumberOfMoves(9), TicTacToe::new);
    Evaluator incumbent =
        new TensorflowEvaluator(
            TensorflowEvaluator.TIC_TAC_TOE_V1, new NumberOfMoves(9), new NumberOfFeatures(9));
    Evaluator candidate =
        new TensorflowEvaluator(
            TensorflowEvaluator.TIC_TAC_TOE_V3, new NumberOfMoves(9), new NumberOfFeatures(9));
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }
}
