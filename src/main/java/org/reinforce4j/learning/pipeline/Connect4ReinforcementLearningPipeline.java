package org.reinforce4j.learning.pipeline;

import com.google.common.base.Stopwatch;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.learning.execute.ModelTrainerExecutor;
import org.reinforce4j.learning.training.ExampleGen;
import org.reinforce4j.learning.training.ExampleGenSettings;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connect4ReinforcementLearningPipeline {

  private static final String BASE_PATH = "/tmp/connect4_test/";
  private static final Logger logger =
      LoggerFactory.getLogger(Connect4ReinforcementLearningPipeline.class);

  private static void train() throws IOException, ExecutionException, InterruptedException {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    logger.info("Start");
    stopwatch.start();

    long nSamples =
        ExampleGen.generate(
            ExampleGenSettings.withDefaults(
                    MonteCarloTreeSearchSettings.<Connect4>builder()
                        .setBackPropagationStackCapacity(50)
                        .setNodesPoolCapacity(8_000_000)
                        .setPruneMinVisits(10)
                        .setWriteMinVisits(100)
                        .setGameService(() -> Connect4Service.INSTANCE)
                        .setEvaluator(
                            () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)))
                        .build())
                .setNumExpansions(3_000_000)
                .setNumThreads(10)
                .setNumIterations(50)
                .setBasePath(BASE_PATH)
                .build());

    logger.info("Wrote {} samples after {}", nSamples, stopwatch);
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

    long nSamples =
        ExampleGen.generate(
            ExampleGenSettings.withDefaults(
                    MonteCarloTreeSearchSettings.<Connect4>builder()
                        .setBackPropagationStackCapacity(50)
                        .setNodesPoolCapacity(8_000_000)
                        .setPruneMinVisits(10)
                        .setWriteMinVisits(100)
                        .setGameService(() -> Connect4Service.INSTANCE)
                        .setEvaluator(
                            () ->
                                new GameOverEvaluator<>(
                                    new OnnxEvaluator<>(
                                        OnnxEvaluator.CONNECT4_ALT_V0, Connect4Service.INSTANCE)))
                        .build())
                .setNumExpansions(3_000_000)
                .setNumThreads(10)
                .setNumIterations(10)
                .setBasePath(BASE_PATH)
                .build());

    logger.info("Wrote {} samples after {}", nSamples, stopwatch);
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
    retrain();
  }
}
