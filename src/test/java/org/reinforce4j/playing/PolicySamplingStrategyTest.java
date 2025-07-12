package org.reinforce4j.playing;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;

public class PolicySamplingStrategyTest {

//  @Test
//  public void shouldReturnAllForUniform() {
//
//    TicTacToe root = TicTacToeService.INSTANCE.newInitialState();
//    PolicySamplingStrategy strategy =
//        new PolicySamplingStrategy(
//            TicTacToeService.INSTANCE,
//            new ZeroValueUniformEvaluator<>(TicTacToeService.INSTANCE.numMoves()));
//
//    int n = 90000;
//    int[] suggestions = new int[9];
//    for (int i = 0; i < n; i++) {
//      suggestions[strategy.nextMove(root)]++;
//    }
//
//    for (int i = 0; i < 9; i++) {
//      Truth.assertThat(suggestions[i]).isGreaterThan(9000);
//      Truth.assertThat(suggestions[i]).isLessThan(11000);
//    }
//  }
//
//  @Test
//  public void shouldReturnExpectedDist() {
//
//    TicTacToe state = TicTacToeService.INSTANCE.newInitialState();
//    state.move(4);
//    state.move(5);
//    state.move(0);
//    state.move(8);
//
//    // State:
//    //
//    //   X| |
//    //  ------
//    //   |X|O
//    // ------
//    //  | |O
//    //
//    // Current player: ONE
//
//    PolicySamplingStrategystrategy =
//        new PolicySamplingStrategy<>(
//            TicTacToeService.INSTANCE,
//            new TensorflowEvaluator<>(
//                TensorflowEvaluator.TIC_TAC_TOE_V3, TicTacToeService.INSTANCE));
//
//    int n = 10000;
//    double[] suggestions = new double[9];
//    for (int i = 0; i < n; i++) {
//      suggestions[strategy.nextMove(state)]++;
//    }
//
//    for (int i = 0; i < 9; i++) {
//      suggestions[i] = suggestions[i] / n;
//    }
//
//    Truth.assertThat(suggestions)
//        .usingTolerance(0.1)
//        .containsExactly(new double[] {0, 0, 0.99, 0, 0, 0, 0, 0, 0})
//        .inOrder();
//  }
}
