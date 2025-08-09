package org.reinforce4j.learning.pipeline;

import com.google.common.base.Stopwatch;
import com.google.inject.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import org.reinforce4j.constants.NumberOfExpansionsPerNode;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfNodesToExpand;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.evaluation.batch.BatchOnnxEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeModule;
import org.reinforce4j.learning.execute.ModelTrainerExecutor;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchModule;
import org.reinforce4j.montecarlo.TreeSearch;
import org.reinforce4j.montecarlo.tasks.ExecutionCoordinator;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorflow.example.Example;

public class TicTacToeReinforcementLearningPipeline {

  private static final String BASE_PATH = "/tmp/tic_tac_toe_test/";
  private static final Logger logger =
      LoggerFactory.getLogger(TicTacToeReinforcementLearningPipeline.class);

  private static void train() throws IOException, InterruptedException {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    logger.info("Start");
    stopwatch.start();

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
                return new NumberOfExpansionsPerNode(1_000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(100_000);
              }
            });

    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    for (int i = 1; i < 10; i++) {
      Queue<Example> examples = treeSearch.explore(new TicTacToe());

      try (DataOutputStream outputStream =
          new DataOutputStream(new FileOutputStream(BASE_PATH + "training-" + i + ".tfrecord"))) {
        TFRecordWriter writer = new TFRecordWriter(outputStream);
        writer.writeAll(examples);
        examples.clear();
      }
    }

    int version = 1;

    ModelTrainerExecutor modelTrainerExecutor =
        new ModelTrainerExecutor(
            BASE_PATH,
            ClassLoader.getSystemResource("tensorflow/train_tic_tac_toe.py").getPath(),
            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
            modelPath(version));
    modelTrainerExecutor.execute();

    executionCoordinator.shutdown();
    logger.info("Training completed on version: {} ", version);
  }

  private static void retrain() throws IOException, ExecutionException, InterruptedException {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    logger.info("Start");
    stopwatch.start();

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
                        new GameOverEvaluator(
                            new BatchOnnxEvaluator(
                                OnnxEvaluator.CONNECT4_V0,
                                new NumberOfFeatures(Connect4.NUM_FEATURES),
                                true)));
              }

              @Provides
              @Singleton
              public NumberOfExpansionsPerNode provideNumberOfExpansionsPerNode() {
                return new NumberOfExpansionsPerNode(1_000);
              }

              @Provides
              @Singleton
              public NumberOfNodesToExpand provideNumberOfNodesToExpand() {
                return new NumberOfNodesToExpand(100_000);
              }
            });

    ExecutionCoordinator executionCoordinator = injector.getInstance(ExecutionCoordinator.class);

    TreeSearch treeSearch = injector.getInstance(TreeSearch.class);

    for (int i = 1; i < 10; i++) {
      Queue<Example> examples = treeSearch.explore(new TicTacToe());

      try (DataOutputStream outputStream =
          new DataOutputStream(new FileOutputStream(BASE_PATH + "training-" + i + ".tfrecord"))) {
        TFRecordWriter writer = new TFRecordWriter(outputStream);
        writer.writeAll(examples);
        examples.clear();
      }
    }

    int version = 1;

    ModelTrainerExecutor modelTrainerExecutor =
        new ModelTrainerExecutor(
            BASE_PATH,
            ClassLoader.getSystemResource("tensorflow/train_tic_tac_toe.py").getPath(),
            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
            modelPath(version));
    modelTrainerExecutor.execute();

    executionCoordinator.shutdown();
    logger.info("Training completed on version: {} ", version);
  }

  private static String modelPath(int version) {
    return Paths.get(BASE_PATH, "models", String.format("model_v%d", version)).toString();
  }

  public static void main(String[] args) throws Exception {
    train();
  }
}
