package org.reinforce4j.learning.pipeline;

public class ReinforcementLearningPipeline {

  //  private static final String BASE_PATH = "/tmp/pipeline_test/";
  //  private static final Logger logger =
  // LoggerFactory.getLogger(ReinforcementLearningPipeline.class);
  //
  //  private static String modelPath(int version) {
  //    return Paths.get(BASE_PATH, "models", String.format("model_v%d", version)).toString();
  //  }
  //
  //  public static void main(String[] args) throws Exception {
  //
  //    Stopwatch stopwatch = Stopwatch.createUnstarted();
  //    logger.info("Start");
  //    stopwatch.start();
  //
  //    long nSamples =
  //        ExampleGen.generate(
  //            ExampleGenSettings.withDefaults(
  //                    MonteCarloTreeSearchSettings.<TicTacToe>withDefaults()
  //                        .setGameService(() -> TicTacToeService.INSTANCE)
  //                        .setEvaluator(
  //                            () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
  //                        .build())
  //                .setBasePath(BASE_PATH)
  //                .build());
  //
  //    logger.info("Wrote {} samples after {}", nSamples, stopwatch);
  //    int version = 0;
  //
  //    ModelTrainerExecutor modelTrainerExecutor =
  //        new ModelTrainerExecutor(
  //            BASE_PATH,
  //            ClassLoader.getSystemResource("tensorflow/train_tic_tac_toe.py").getPath(),
  //            Paths.get(BASE_PATH, "training-*.tfrecord").toString(),
  //            modelPath(version));
  //    modelTrainerExecutor.execute();
  //  }
}
