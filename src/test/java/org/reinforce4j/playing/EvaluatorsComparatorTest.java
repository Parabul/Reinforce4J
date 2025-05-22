package org.reinforce4j.playing;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

public class EvaluatorsComparatorTest {

  @Test
  public void randomIsNotBetterThanRandom() {
    EvaluatorsComparator<TicTacToe> evaluatorsComparator =
        new EvaluatorsComparator<>(TicTacToeService.INSTANCE);
    Evaluator<TicTacToe> incumbent = new ZeroValueUniformEvaluator<>(9);
    Evaluator<TicTacToe> candidate = new ZeroValueUniformEvaluator<>(9);
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isFalse();
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(candidate, incumbent)).isFalse();
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(candidate, candidate)).isFalse();
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, incumbent)).isFalse();
  }

  @Test
  public void nnEvaluatorBetterThanRandom() {
    EvaluatorsComparator<Connect4> evaluatorsComparator =
        new EvaluatorsComparator(Connect4Service.INSTANCE);
    Evaluator<Connect4> incumbent = new ZeroValueUniformEvaluator<>(9);
    Evaluator<Connect4> candidate =
        new OnnxEvaluator<>(OnnxEvaluator.CONNECT4_V1, Connect4Service.INSTANCE);
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }


  @Test
  public void nnEvaluatorNotBetterThanSelf() {
    EvaluatorsComparator<Connect4> evaluatorsComparator =
            new EvaluatorsComparator(Connect4Service.INSTANCE);
    Evaluator<Connect4> incumbent =
            new OnnxEvaluator<>(OnnxEvaluator.CONNECT4_V1, Connect4Service.INSTANCE);
    Evaluator<Connect4> candidate =
            new OnnxEvaluator<>(OnnxEvaluator.CONNECT4_V1, Connect4Service.INSTANCE);
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }

  @Test
  public void newVersion() {
    EvaluatorsComparator<Connect4> evaluatorsComparator =
            new EvaluatorsComparator(Connect4Service.INSTANCE);
    Evaluator<Connect4> candidate =new OnnxEvaluator<>("/tmp/connect4_test/model_v0.onnx", Connect4Service.INSTANCE);
    Evaluator<Connect4>  incumbent=
            new OnnxEvaluator<>(OnnxEvaluator.CONNECT4_V1, Connect4Service.INSTANCE);
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }

  @Test
  public void ticTacToeTensorflowComparison() {
    EvaluatorsComparator<TicTacToe> evaluatorsComparator =
        new EvaluatorsComparator<>(TicTacToeService.INSTANCE);
    Evaluator<TicTacToe> incumbent =
        new TensorflowEvaluator<>(TensorflowEvaluator.TIC_TAC_TOE_V1, TicTacToeService.INSTANCE);
    Evaluator<TicTacToe> candidate =
        new TensorflowEvaluator<>(TensorflowEvaluator.TIC_TAC_TOE_V3, TicTacToeService.INSTANCE);
    Truth.assertThat(evaluatorsComparator.candidateIsBetter(incumbent, candidate)).isTrue();
  }
}
