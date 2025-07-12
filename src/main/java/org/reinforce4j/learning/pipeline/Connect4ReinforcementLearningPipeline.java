package org.reinforce4j.learning.pipeline;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.learning.execute.ModelTrainerExecutor;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;
import org.reinforce4j.montecarlo.TreeNode;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchModule;
import org.reinforce4j.montecarlo.TreeSearch;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorflow.example.Example;

public class Connect4ReinforcementLearningPipeline {

  private static final String BASE_PATH = "/tmp/connect4_test/";
  private static final Logger logger =
      LoggerFactory.getLogger(Connect4ReinforcementLearningPipeline.class);

  private static void train() throws IOException, InterruptedException {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    logger.info("Start");
    stopwatch.start();

    for (int i = 1; i < 5; i++) {
      Injector injector =
          Guice.createInjector(
              new MonteCarloTreeSearchModule(
                  MonteCarloTreeSearchSettings.builder()
                      .setPruneMinVisits(10)
                      .setWriteMinVisits(1000)
                      .setEvaluator(() -> new GameOverEvaluator(new ZeroValueUniformEvaluator(7)))
                      .build()));
      TreeSearch treeSearch = injector.getInstance(TreeSearch.class);
      TreeNode root = new TreeNode(new Connect4(), 7);
      List<Example> examples = treeSearch.explore(root);

      DataOutputStream outputStream =
          new DataOutputStream(new FileOutputStream(BASE_PATH + "training-" + i + ".tfrecord"));
      TFRecordWriter writer = new TFRecordWriter(outputStream);
      writer.writeAll(examples);
      outputStream.close();
    }

    int version = 0;

    ModelTrainerExecutor modelTrainerExecutor =
        new ModelTrainerExecutor(
            BASE_PATH,
            ClassLoader.getSystemResource("tensorflow/train_connect4.py").getPath(),
            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
            modelPath(version));
    modelTrainerExecutor.execute();

    logger.info("Training completed on version: {} ", version);
  }

  private static void retrain() throws IOException, ExecutionException, InterruptedException {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    logger.info("Start");
    stopwatch.start();

    for (int i = 1; i < 5; i++) {

      Injector injector =
          Guice.createInjector(
              new MonteCarloTreeSearchModule(
                  MonteCarloTreeSearchSettings.builder()
                      .setPruneMinVisits(10)
                      .setWriteMinVisits(1000)
                      .setEvaluator(
                          () ->
                              new GameOverEvaluator(
                                  new OnnxEvaluator(
                                      OnnxEvaluator.CONNECT4_V0,
                                      new NumberOfFeatures(42),
                                      new NumberOfMoves(7))))
                      .build()));
      TreeSearch treeSearch = injector.getInstance(TreeSearch.class);
      TreeNode root = new TreeNode(new Connect4(), 7);
      List<Example> examples = treeSearch.explore(root);

      DataOutputStream outputStream =
          new DataOutputStream(new FileOutputStream(BASE_PATH + "training-" + i + ".tfrecord"));
      TFRecordWriter writer = new TFRecordWriter(outputStream);
      writer.writeAll(examples);
      outputStream.close();
    }

    int version = 0;

    ModelTrainerExecutor modelTrainerExecutor =
        new ModelTrainerExecutor(
            BASE_PATH,
            ClassLoader.getSystemResource("tensorflow/train_connect4.py").getPath(),
            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
            modelPath(version));
    modelTrainerExecutor.execute();

    logger.info("Training completed on version: {} ", version);
  }

  private static String modelPath(int version) {
    return Paths.get(BASE_PATH, "models", String.format("model_v%d", version)).toString();
  }

  public static void main(String[] args) throws Exception {
    train();
  }
}
