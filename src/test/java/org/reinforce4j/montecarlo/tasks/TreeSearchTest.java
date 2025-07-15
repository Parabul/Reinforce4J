package org.reinforce4j.montecarlo.tasks;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.truth.Correspondence;
import com.google.inject.*;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.reinforce4j.constants.NumberOfExpansionsPerNode;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfNodesToExpand;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.evaluation.batch.BatchEvaluatorModule;
import org.reinforce4j.evaluation.batch.BatchOnnxEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Module;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchModule;
import org.reinforce4j.montecarlo.TreeSearch;
import org.tensorflow.example.Example;

public class TreeSearchTest {

  @Test
  public void test() throws InterruptedException, IOException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new Connect4Module());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(BatchEvaluatorModule.BatchEvaluatorDelegate.class)
                    .toInstance(new GameOverEvaluator(new ZeroValueUniformEvaluator(7)));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(10000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberFeatures() {
                return new NumberOfNodesToExpand(100);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    List<Example> examples = treeSearch.explore(new Connect4());

    assertThat(examples.size()).isAtLeast(100);

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
            .comparingElementsUsing(Correspondence.tolerance(0.02))
            .containsExactly(0.007, 0.12, 0.13, 0.14, 0.20, 0.15, 0.12, 0.12);
      }
    }
  }

  @Test
  public void test2() throws InterruptedException, IOException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new Connect4Module());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(BatchEvaluatorModule.BatchEvaluatorDelegate.class)
                    .toInstance(
                        new BatchOnnxEvaluator(
                            OnnxEvaluator.CONNECT4_V1, new NumberOfFeatures(42)));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(5000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberFeatures() {
                return new NumberOfNodesToExpand(3);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    List<Example> examples = treeSearch.explore(new Connect4());

    //    assertThat(examples.size()).isGreaterThan(10000);

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
            .comparingElementsUsing(Correspondence.tolerance(0.02))
            .containsExactly(0.065073, 0.0654, 0.0922, 0.1232, 0.4396, 0.1164, 0.0984, 0.0648);
      }
    }
  }
}
