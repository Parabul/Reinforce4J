package org.reinforce4j.montecarlo;

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
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeModule;
import org.reinforce4j.utils.tfrecord.TensorFlowUtils;
import org.tensorflow.example.Example;

public class TreeSearchTest {

  @Test
  public void rootStateEvalShouldMatchExpectedUniformTicTacToe()
      throws InterruptedException, IOException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new TicTacToeModule());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(BatchEvaluatorModule.BatchEvaluatorDelegate.class)
                    .toInstance(
                        new GameOverEvaluator(new ZeroValueUniformEvaluator(TicTacToe.NUM_MOVES)));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(1000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(10000);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    List<Example> examples = treeSearch.explore(new TicTacToe());

    TicTacToe state = (new TicTacToe()).move(4).move(1).move(0).move(2);

    assertThat(examples.size()).isAtLeast(100);

    for (Example example : examples) {
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(TicTacToe.NUM_FEATURES);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(10);
      if (example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList().stream()
          .allMatch(val -> val == 0f)) {

        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .comparingElementsUsing(Correspondence.tolerance(0.2))
            .containsExactly(0.077, 0.132, 0.086, 0.107, 0.079, 0.225, 0.082, 0.109, 0.074, 0.106);
      }

      if (example
          .getFeatures()
          .getFeatureMap()
          .get("input")
          .equals(TensorFlowUtils.floatList(state.encode()))) {
        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .comparingElementsUsing(Correspondence.tolerance(0.1))
            .containsExactly(0.875, 0.0, 0.0, 0.0, 0.022, 0.0, 0.02, 0.018, 0.016, 0.924);
      }
    }
  }

  @Test
  public void rootStateEvalShouldMatchExpectedUniform() throws InterruptedException, IOException {
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
                        new GameOverEvaluator(new ZeroValueUniformEvaluator(Connect4.NUM_MOVES)));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(10000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(100);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    List<Example> examples = treeSearch.explore(new Connect4());

    assertThat(examples.size()).isAtLeast(100);

    for (Example example : examples) {
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(Connect4.NUM_FEATURES);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(8);
      if (example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList().stream()
          .allMatch(val -> val == 0f)) {

        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .comparingElementsUsing(Correspondence.tolerance(0.03))
            .containsExactly(0.007, 0.12, 0.13, 0.14, 0.20, 0.15, 0.12, 0.12);
      }
    }
  }

  @Test
  public void rootStateEvalShouldMatchExpectedOnnx() throws InterruptedException, IOException {
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
                            OnnxEvaluator.CONNECT4_V1,
                            new NumberOfFeatures(Connect4.NUM_FEATURES)));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(5000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(3);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    List<Example> examples = treeSearch.explore(new Connect4());

    //    assertThat(examples.size()).isGreaterThan(10000);

    for (Example example : examples) {
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");

      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(Connect4.NUM_FEATURES);

      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(8);
      if (example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList().stream()
          .allMatch(val -> val == 0f)) {

        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .comparingElementsUsing(Correspondence.tolerance(0.03))
            .containsExactly(0.065073, 0.0654, 0.0922, 0.1232, 0.4396, 0.1164, 0.0984, 0.0648);
      }
    }
  }
}
