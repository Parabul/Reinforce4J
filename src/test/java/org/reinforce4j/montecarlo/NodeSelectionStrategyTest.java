package org.reinforce4j.montecarlo;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;

class NodeSelectionStrategyTest {
//
//  @Test
//  void suggestMovesEvenlyWhenNoInformation() {
//    MonteCarloTreeSearchSettingssettings =
//        MonteCarloTreeSearchSettings.withDefaults()
//            .setNodesPoolCapacity(10_000)
//            .setGameService(() -> TicTacToeService.INSTANCE)
//            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
//            .build();
//
//    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
//    monteCarloTreeSearch.init();
//
//    TreeNoderoot = monteCarloTreeSearch.newRoot();
//    monteCarloTreeSearch.initChildren(root);
//
//    int n = TicTacToeService.INSTANCE.numMoves();
//
//    NodeSelectionStrategy nodeSelectionStrategy = new NodeSelectionStrategy(n);
//
//    int[] histogram = new int[n];
//
//    int times = 1_000_000;
//    for (int i = 0; i < times; i++) {
//      histogram[nodeSelectionStrategy.suggestMove(root)]++;
//    }
//
//    //    System.out.println(nodeSelectionStrategy.suggestMoveAndExplain(root).getValue());
//
//    float[] dist = new float[n];
//
//    for (int i = 0; i < n; i++) {
//      dist[i] = 1.0f * histogram[i] / times;
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
//    MonteCarloTreeSearchSettingssettings =
//        MonteCarloTreeSearchSettings.withDefaults()
//            .setNodesPoolCapacity(10_000)
//            .setGameService(() -> TicTacToeService.INSTANCE)
//            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
//            .build();
//
//    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
//    monteCarloTreeSearch.init();
//
//    TreeNodetreeNode = monteCarloTreeSearch.newRoot();
//    monteCarloTreeSearch.initChildren(treeNode);
//
//    int numMoves = TicTacToeService.INSTANCE.numMoves();
//
//    NodeSelectionStrategy nodeSelectionStrategy = new NodeSelectionStrategy(numMoves);
//
//    // Move 5 has some value
//    treeNode.getChildStates()[4].getAverageValue().add(new AverageValue(0.2f, 1));
//
//    int[] histogram = new int[numMoves];
//
//    int n = 1_000_000;
//    for (int i = 0; i < n; i++) {
//
//      histogram[nodeSelectionStrategy.suggestMove(treeNode)]++;
//    }
//
//    float[] dist = new float[numMoves];
//
//    for (int i = 0; i < numMoves; i++) {
//      dist[i] = 1.0f * histogram[i] / n;
//    }
//
//    assertThat(dist)
//        .usingTolerance(0.01)
//        .containsExactly(0.095, 0.095, 0.095, 0.095, 0.23, 0.095, 0.095, 0.095, 0.095)
//        .inOrder();
//  }
//
//  @Test
//  void suggestMoveWithHigherPriors() {
//    MonteCarloTreeSearchSettingssettings =
//        MonteCarloTreeSearchSettings.withDefaults()
//            .setNodesPoolCapacity(10_000)
//            .setGameService(() -> TicTacToeService.INSTANCE)
//            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
//            .build();
//
//    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
//    monteCarloTreeSearch.init();
//
//    TreeNodetreeNode = monteCarloTreeSearch.newRoot();
//    monteCarloTreeSearch.initChildren(treeNode);
//
//    int numMoves = TicTacToeService.INSTANCE.numMoves();
//    NodeSelectionStrategy nodeSelectionStrategy = new NodeSelectionStrategy(numMoves);
//
//    treeNode.evaluation().setValue(0.0f);
//    float[] priors = new float[] {0.2f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f};
//
//    System.arraycopy(priors, 0, treeNode.evaluation().getPolicy(), 0, numMoves);
//
//    int[] histogram = new int[numMoves];
//
//    int n = 1_000_000;
//    for (int i = 0; i < n; i++) {
//
//      histogram[nodeSelectionStrategy.suggestMove(treeNode)]++;
//    }
//
//    float[] dist = new float[numMoves];
//
//    for (int i = 0; i < numMoves; i++) {
//      dist[i] = 1.0f * histogram[i] / n;
//    }
//
//    assertThat(dist)
//        .usingTolerance(0.01)
//        .containsExactly(0.77, 0.02, 0.02, 0.02, 0.02, 0.02, 0.02, 0.02, 0.02)
//        .inOrder();
//  }

  //  @Test
  //  public void dirichletSampler(){
  //    DirichletSampler dirichlet =
  //            DirichletSampler.symmetric(RandomSource.XO_RO_SHI_RO_128_PP.create(), 2, 0.15);
  //    for(int i = 0; i < 100; i++){
  //      System.out.println(Arrays.toString(dirichlet.sample()));
  //    }
  //  }
}
