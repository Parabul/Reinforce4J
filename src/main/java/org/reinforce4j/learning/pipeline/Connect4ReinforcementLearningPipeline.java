package org.reinforce4j.learning.pipeline;

import com.google.common.base.Stopwatch;
import java.nio.file.Paths;
import org.reinforce4j.evaluation.GameOverEvaluator;
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

  private static final String BASE_PATH = "/home/anarbek/tmp/connect4_test/";
  private static final Logger logger =
      LoggerFactory.getLogger(Connect4ReinforcementLearningPipeline.class);

  private static String modelPath(int version) {
    return Paths.get(BASE_PATH, "models", String.format("model_v%d", version)).toString();
  }

  public static void main(String[] args) throws Exception {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    logger.info("Start");
    stopwatch.start();

    long nSamples =
        ExampleGen.generate(
            ExampleGenSettings.withDefaults(
                    MonteCarloTreeSearchSettings.<Connect4>builder()
                        .setBackPropagationStackCapacity(50)
                        .setNodesPoolCapacity(4_000_000)
                        .setPruneMinVisits(10)
                        .setWriteMinVisits(500)
                        .setGameService(() -> Connect4Service.INSTANCE)
                        .setEvaluator(
                            () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)))
                        .build())
                .setNumExpansions(3_000_000)
                .setNumThreads(5)
                .setNumIterations(100)
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
}
