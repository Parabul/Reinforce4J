package org.reinforce4j.montecarlo.tasks;

import static com.google.common.truth.Truth.assertThat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchModule;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;
import org.reinforce4j.montecarlo.TreeNode;
import org.reinforce4j.montecarlo.TreeSearch;
import org.tensorflow.example.Example;

public class TreeSearchTest {

  @Test
  public void test() throws InterruptedException, IOException {
    Injector injector =
        Guice.createInjector(
            new MonteCarloTreeSearchModule(
                MonteCarloTreeSearchSettings.builder()
                    .setPruneMinVisits(10)
                    .setWriteMinVisits(100)
                    .setEvaluator(() -> new GameOverEvaluator(new ZeroValueUniformEvaluator(7)))
                    .build()));
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    TreeNode root = new TreeNode(new Connect4(), 7);
    List<Example> examples = treeSearch.explore(root);

    assertThat(examples.size()).isGreaterThan(10000);

    for (Example example : examples) {
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(42);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(8);
      if (example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList().stream()
          .allMatch(val -> val == 0f)) {

        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .containsExactly(0f, 0f, 0f, 0f);
      }
    }
  }

//  @Test
//  public void test2() throws InterruptedException, IOException {
//    Injector injector =
//        Guice.createInjector(
//            new MonteCarloTreeSearchModule(
//                MonteCarloTreeSearchSettings.builder()
//                    .setPruneMinVisits(10)
//                    .setWriteMinVisits(100)
//                    .setEvaluator(
//                        () ->
//                            new GameOverEvaluator(
//                                new OnnxEvaluator(
//                                    OnnxEvaluator.CONNECT4_V0,
//                                    new NumberOfFeatures(42),
//                                    new NumberOfMoves(7))))
//                    .build()));
//    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);
//
//    TreeNode root = new TreeNode(new Connect4(), 7);
//    List<Example> examples = treeSearch.explore(root);
//
//    //    assertThat(examples.size()).isGreaterThan(10000);
//
//    for (Example example : examples) {
//      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
//      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
//          .hasSize(42);
//      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
//          .hasSize(8);
//      if (example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList().stream()
//          .allMatch(val -> val == 0f)) {
//
//        assertThat(
//                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
//            .containsExactly(0f, 0f, 0f, 0f);
//      }
//    }
//  }
}
