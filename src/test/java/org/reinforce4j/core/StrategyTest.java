package org.reinforce4j.core;

import static com.google.common.truth.Truth.assertThat;

import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.junit.jupiter.api.Test;

class StrategyTest {

//  @Test
//  void suggestMovesEvenlyWhenNoInformation() {
//    GameService<T> factory = GameRegistry.TIC_TAC_TOE;
//    GameState root = factory.initialState();
//    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(factory);
//    StateNode stateNode = new StateNode(root, evaluator.evaluate(root));
//    stateNode.initChildren(factory, evaluator);
//
//    Strategy strategy = new Strategy(factory.numMoves());
//
//    int[] histogram = new int[factory.numMoves()];
//
//    int n = 1_000_000;
//    for (int i = 0; i < n; i++) {
//
//      histogram[strategy.suggestMove(stateNode)]++;
//    }
//
//    float[] dist = new float[factory.numMoves()];
//
//    for (int i = 0; i < factory.numMoves(); i++) {
//      dist[i] = 1.0f * histogram[i] / n;
//    }
//
//    assertThat(dist)
//        .usingTolerance(0.01)
//        .containsExactly(0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11)
//        .inOrder();
//  }
//
//  @Test
//  void suggestMoveWithHigherValue() {
//    GameService<T> factory = GameRegistry.TIC_TAC_TOE;
//    GameState root = factory.initialState();
//    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(factory);
//    StateNode stateNode = new StateNode(root, evaluator.evaluate(root));
//    stateNode.initChildren(factory, evaluator);
//
//    // Move 5 has some value
//    stateNode.getChildStates()[4].getAverageValue().add(new AverageValue(0.2f, Player.ONE));
//
//    Strategy strategy = new Strategy(factory.numMoves());
//
//    int[] histogram = new int[factory.numMoves()];
//
//    int n = 1_000_000;
//    for (int i = 0; i < n; i++) {
//
//      histogram[strategy.suggestMove(stateNode)]++;
//    }
//
//    float[] dist = new float[factory.numMoves()];
//
//    for (int i = 0; i < factory.numMoves(); i++) {
//      dist[i] = 1.0f * histogram[i] / n;
//    }
//
//    assertThat(dist)
//        .usingTolerance(0.01)
//        .containsExactly(0.06, 0.06, 0.06, 0.06, 0.47, 0.06, 0.06, 0.06, 0.06)
//        .inOrder();
//  }
//
//  @Test
//  void suggestMoveWithHigherPriors() {
//    GameService<T> factory = GameRegistry.TIC_TAC_TOE;
//    GameState root = factory.initialState();
//
//    StateNode stateNode =
//        new StateNode(
//            root,
//            new StateEvaluation(
//                0.0f, new float[] {0.2f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f}));
//
//    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(factory);
//    stateNode.initChildren(factory, evaluator);
//
//    Strategy strategy = new Strategy(factory.numMoves());
//
//    int[] histogram = new int[factory.numMoves()];
//
//    int n = 1_000_000;
//    for (int i = 0; i < n; i++) {
//
//      histogram[strategy.suggestMove(stateNode)]++;
//    }
//
//    float[] dist = new float[factory.numMoves()];
//
//    for (int i = 0; i < factory.numMoves(); i++) {
//      dist[i] = 1.0f * histogram[i] / n;
//    }
//
//    assertThat(dist)
//        .usingTolerance(0.01)
//        .containsExactly(0.77, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028)
//        .inOrder();
//  }
}
