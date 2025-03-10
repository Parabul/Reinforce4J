package org.reinforce4j.learning.pipeline;

import com.google.common.base.Stopwatch;
import java.nio.file.Paths;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.learning.execute.ModelTrainerExecutor;
import org.reinforce4j.learning.training.ExampleGen;
import org.reinforce4j.learning.training.ExampleGenSettings;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;

public class Connect4ReinforcementLearningPipeline {

  private static final String BASE_PATH = "/home/anarbek/tmp/connect4_test/";

  private static String modelPath(int version) {
    return Paths.get(BASE_PATH, "models", String.format("model_v%d", version)).toString();
  }

  public static void main(String[] args) throws Exception {

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    System.out.println("Start");
    stopwatch.start();

    long nSamples =
        ExampleGen.generate(
            ExampleGenSettings.withDefaults(
                    MonteCarloTreeSearchSettings.withDefaults()
                        .setGameService(() -> Connect4Service.INSTANCE)
                        .setEvaluator(
                            () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)))
                        .build())
                .setBasePath(BASE_PATH)
                .build());

    System.out.println(nSamples);
    System.out.println(stopwatch);
    int version = 0;

    ModelTrainerExecutor modelTrainerExecutor =
        new ModelTrainerExecutor(
            BASE_PATH,
            ClassLoader.getSystemResource("tensorflow/train_connect4.py").getPath(),
            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
            modelPath(version));
    modelTrainerExecutor.execute();

    System.out.println("Training completed on version: " + version);
  }
}
