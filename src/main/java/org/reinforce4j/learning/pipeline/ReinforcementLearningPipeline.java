package org.reinforce4j.learning.pipeline;

import com.google.common.base.Stopwatch;
import java.nio.file.Paths;
import org.reinforce4j.core.StateNodeService;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;
import org.reinforce4j.learning.execute.ModelTrainerExecutor;
import org.reinforce4j.learning.training.ExampleGen;
import org.reinforce4j.learning.training.ExampleGenSettings;

public class ReinforcementLearningPipeline {

  private static final String BASE_PATH = "/home/anarbek/tmp/pipeline_test/";

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
                    () ->
                        new StateNodeService(
                            new TicTacToeService(),
                            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)),
                            10000,
                            30))
                .setBasePath(BASE_PATH)
                .build());

    System.out.println(nSamples);
    System.out.println(stopwatch);
    int version = 0;

    ModelTrainerExecutor modelTrainerExecutor =
        new ModelTrainerExecutor(
            BASE_PATH,
            ClassLoader.getSystemResource("tensorflow/train_tic_tac_toe.py").getPath(),
            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
            modelPath(version));
    modelTrainerExecutor.execute();
  }
}
