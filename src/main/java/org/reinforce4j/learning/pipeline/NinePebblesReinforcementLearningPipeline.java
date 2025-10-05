package org.reinforce4j.learning.pipeline;

import com.google.common.base.Stopwatch;
import com.google.inject.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.Set;
import org.reinforce4j.constants.NumberOfExpansionsPerNode;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.constants.NumberOfNodesToExpand;
import org.reinforce4j.core.GameState;
import org.reinforce4j.evaluation.*;
import org.reinforce4j.evaluation.batch.BatchOnnxEvaluator;
import org.reinforce4j.exploration.BloomMiniBatchKMeansDiverseSet;
import org.reinforce4j.exploration.DiverseSetGenerator;
import org.reinforce4j.exploration.RandomStateGenerator;
import org.reinforce4j.exploration.RandomWalk;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.NinePebbles;
import org.reinforce4j.games.NinePebblesModule;
import org.reinforce4j.learning.execute.ModelTrainerExecutor;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchModule;
import org.reinforce4j.montecarlo.TreeSearch;
import org.reinforce4j.montecarlo.tasks.ExecutionCoordinator;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorflow.example.Example;

public class NinePebblesReinforcementLearningPipeline {

  private static final String BASE_PATH = "/home/anarbek/tmp/nine_pebbles_test/";
  private static final Logger logger =
      LoggerFactory.getLogger(NinePebblesReinforcementLearningPipeline.class);

  private static void train() throws IOException, InterruptedException {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    logger.info("Start");
    stopwatch.start();

    BloomMiniBatchKMeansDiverseSet diverseSet =
        new BloomMiniBatchKMeansDiverseSet(10_000, 100, 3_000_000, 50_000, 1_000);
    RandomStateGenerator stateGenerator =
        new DiverseSetGenerator(
            diverseSet, new RandomWalk(new NinePebbles(), NinePebbles.NUM_MOVES), 35_000);
    Set<GameState> states = stateGenerator.get();

    logger.info("Generated " + states.size() + " states");

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
                return new NumberOfExpansionsPerNode(5_000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(20_000);
              }
            });

    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    //    for (int i = 1; i < 5; i++) {
    Queue<Example> examples = treeSearch.exploreAll(states);

    try (DataOutputStream outputStream =
        new DataOutputStream(new FileOutputStream(BASE_PATH + "training-0.tfrecord"))) {
      TFRecordWriter writer = new TFRecordWriter(outputStream);
      writer.writeAll(examples);
      examples.clear();
    }
    //    }

    int version = 1;

    ModelTrainerExecutor modelTrainerExecutor =
        new ModelTrainerExecutor(
            BASE_PATH,
            ClassLoader.getSystemResource("tensorflow/train_nine_pebbles.py").getPath(),
            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
            modelPath(version));
    modelTrainerExecutor.execute();

    executionCoordinator.shutdown();
    logger.info("Training completed on version: {} ", version);
  }

  private static void retrain() throws IOException, InterruptedException {

      Stopwatch stopwatch = Stopwatch.createUnstarted();
      logger.info("Start");
      stopwatch.start();

      BloomMiniBatchKMeansDiverseSet diverseSet =
              new BloomMiniBatchKMeansDiverseSet(10_000, 100, 3_000_000, 50_000, 1_000);
      RandomStateGenerator stateGenerator =
              new DiverseSetGenerator(
                      diverseSet, new RandomWalk(new NinePebbles(), NinePebbles.NUM_MOVES), 35_000);
      Set<GameState> states = stateGenerator.get();

      logger.info("Generated " + states.size() + " states");

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
                                                      new BatchOnnxEvaluator(
                                                              OnnxEvaluator.NINE_PEBBLES_V2,
                                                              new NumberOfFeatures(NinePebbles.NUM_FEATURES), false)));
                          }

                          @Provides
                          @Singleton
                          public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                              return new NumberOfExpansionsPerNode(2_000);
                          }

                          @Provides
                          @Singleton
                          public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                              return new NumberOfNodesToExpand(20_000);
                          }
                      });

      ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

      TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

      Queue<Example> examples = treeSearch.exploreAll(states);

      try (DataOutputStream outputStream =
                   new DataOutputStream(new FileOutputStream(BASE_PATH + "training-4.tfrecord"))) {
          TFRecordWriter writer = new TFRecordWriter(outputStream);
          writer.writeAll(examples);
          examples.clear();
      }

      int version = 4;

      ModelTrainerExecutor modelTrainerExecutor =
              new ModelTrainerExecutor(
                      BASE_PATH,
                      ClassLoader.getSystemResource("tensorflow/train_nine_pebbles.py").getPath(),
                      Paths.get(BASE_PATH, "training-4.tfrecord").toString(),
                      modelPath(version));
      modelTrainerExecutor.execute();

      executionCoordinator.shutdown();
      logger.info("Training completed on version: {} ", version);
  }

  private static String modelPath(int version) {
    return Paths.get(BASE_PATH, "models", String.format("model_v%d", version)).toString();
  }

  public static void main(String[] args) throws Exception {
//    train();
      retrain();
  }
}
