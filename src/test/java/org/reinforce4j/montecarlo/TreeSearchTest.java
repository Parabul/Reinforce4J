package org.reinforce4j.montecarlo;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.truth.Correspondence;
import com.google.inject.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.junit.Test;
import org.reinforce4j.constants.NumberOfExpansionsPerNode;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.constants.NumberOfNodesToExpand;
import org.reinforce4j.evaluation.*;
import org.reinforce4j.evaluation.batch.BatchOnnxEvaluator;
import org.reinforce4j.games.*;
import org.reinforce4j.montecarlo.tasks.ExecutionCoordinator;
import org.reinforce4j.utils.TestUtils;
import org.reinforce4j.utils.tfrecord.TensorFlowUtils;
import org.tensorflow.example.Example;

public class TreeSearchTest {

  @Test
  public void exploreAllTicTacToe() throws InterruptedException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new TicTacToeModule());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(MonteCarloTreeSearchModule.DefaultEvaluator.class)
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

    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    int n = 20000;
    List<TicTacToe> gameStates = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      TicTacToe state = TestUtils.getRandomTicTacToeState();
      if (state.isGameOver()) {
        continue;
      }
      gameStates.add(state);
    }

    Queue<Example> examples = treeSearch.exploreAll(gameStates);

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
    executionCoordinator.shutdown();
  }

  @Test
  public void rootStateEvalShouldMatchExpectedUniform() throws InterruptedException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new Connect4Module());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(MonteCarloTreeSearchModule.DefaultEvaluator.class)
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
    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    Queue<Example> examples = treeSearch.explore(new Connect4());

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

    executionCoordinator.shutdown();
  }

  @Test
  public void rootStateEvalShouldMatchExpectedOnnx() throws InterruptedException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new Connect4Module());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(MonteCarloTreeSearchModule.DefaultEvaluator.class)
                    .toInstance(
                        new BatchOnnxEvaluator(
                            OnnxEvaluator.CONNECT4_V0,
                            new NumberOfFeatures(Connect4.NUM_FEATURES),
                            false));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(2000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(3);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);
    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    Queue<Example> examples = treeSearch.explore(new Connect4());

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
            .containsExactly(0.004, 0.14, 0.14, 0.14, 0.14, 0.14, 0.14, 0.14);
      }
    }

    executionCoordinator.shutdown();
  }

  @Test
  public void rootStateEvalShouldMatchExpectedUniformNinePebbles() throws InterruptedException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new NinePebblesModule());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(MonteCarloTreeSearchModule.DefaultEvaluator.class)
                    .toInstance(
                        new GameOverEvaluator(
                            new ZeroValueUniformEvaluator(NinePebbles.NUM_MOVES)));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(2000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(50);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    NinePebbles root = new NinePebbles();
    Queue<Example> examples = treeSearch.explore(root);

    NinePebbles state = (new NinePebbles()).move(7);
    System.out.println(state);

    System.out.println(examples.peek());

    assertThat(examples.size()).isAtLeast(100);

    boolean hitRoot = false;
    boolean hitState = false;
    for (Example example : examples) {
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(NinePebbles.NUM_FEATURES);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(10);
      if (example
          .getFeatures()
          .getFeatureMap()
          .get("input")
          .equals(TensorFlowUtils.floatList(root.encode()))) {
        hitRoot = true;
        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .comparingElementsUsing(Correspondence.tolerance(0.1))
            .containsExactly(0.0030948557, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.110, 0.11, 0.11)
            .inOrder();
      }

      if (example
          .getFeatures()
          .getFeatureMap()
          .get("input")
          .equals(TensorFlowUtils.floatList(state.encode()))) {
        hitState = true;
        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .comparingElementsUsing(Correspondence.tolerance(0.1))
            .containsExactly(
                -0.00284549,
                0.12264151,
                0.12264151,
                0.1273585,
                0.11792453,
                0.13207547,
                0.1273585,
                0.0,
                0.12264151,
                0.1273585)
            .inOrder();
      }
    }
    assertThat(hitRoot).isTrue();
    assertThat(hitState).isTrue();
    executionCoordinator.shutdown();
  }

  @Test
  public void rootStateEvalShouldMatchExpectedOnnxNinePebbles() throws InterruptedException {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new NinePebblesModule());
                install(new MonteCarloTreeSearchModule());
                bind(Evaluator.class)
                    .annotatedWith(MonteCarloTreeSearchModule.DefaultEvaluator.class)
                    .toInstance(
                        new ExtendedGameOverEvaluator(
                            new OnnxEvaluator(
                                OnnxEvaluator.NINE_PEBBLES_V0,
                                new NumberOfFeatures(NinePebbles.NUM_FEATURES),
                                new NumberOfMoves(NinePebbles.NUM_MOVES))));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(2000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(1);
              }
            });
    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    NinePebbles state = (new NinePebbles()).move(5).move(6).move(7).move(8);

    Queue<Example> examples = treeSearch.explore(state);
    System.out.println(state);

    System.out.println(examples.peek());

    boolean hitState = false;
    for (Example example : examples) {
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(NinePebbles.NUM_FEATURES);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(10);

      if (example
          .getFeatures()
          .getFeatureMap()
          .get("input")
          .equals(TensorFlowUtils.floatList(state.encode()))) {
        hitState = true;
        assertThat(
                example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
            .comparingElementsUsing(Correspondence.tolerance(0.1))
            .containsExactly(
                    0.0025136238, 0.19, 0.1595, 0.109, 0.098, 0.113, 0.112, 0.127, 0.0915, 0.0)
            .inOrder();
      }
    }
    assertThat(hitState).isTrue();
    executionCoordinator.shutdown();
  }
}
