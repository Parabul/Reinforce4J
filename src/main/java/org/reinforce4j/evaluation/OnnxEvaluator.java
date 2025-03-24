package org.reinforce4j.evaluation;

import ai.onnxruntime.*;
import com.google.common.collect.ImmutableMap;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Map;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;

public class OnnxEvaluator<T extends GameState> implements Evaluator<T> {

  public static final String CONNECT4_V1 =
      TensorflowEvaluator.class.getResource("/onnx/models/model.onnx").getPath();

  public static final String CONNECT4_V2 =
          TensorflowEvaluator.class.getResource("/onnx/models/opt_model.onnx").getPath();

  private static final String INPUT = "input_1";
  private static final String VALUE_OUTPUT = "value_output";
  private static final String POLICY_OUTPUT = "policy_output";

  private final OrtEnvironment env;
  private final OrtSession session;
  private final float[] batch;
  private final long[] shape;
  private final int numFeatures;
  private final FloatBuffer buffer;

  public OnnxEvaluator(String modelPath, GameService<T> gameService) throws OrtException {
    env = OrtEnvironment.getEnvironment();
    OrtSession.SessionOptions sessionOptions = new OrtSession.SessionOptions();
//    sessionOptions.addCUDA(0);
    sessionOptions.addTensorrt(0);
    session = env.createSession(modelPath, sessionOptions);
    this.batch = new float[gameService.numMoves() * gameService.numFeatures()];
    this.buffer = FloatBuffer.wrap(batch);
    this.shape = new long[] {gameService.numMoves(), gameService.numFeatures()};
    this.numFeatures = gameService.numFeatures();
  }

  public static void main(String[] args) throws Exception {
    OrtEnvironment env = OrtEnvironment.getEnvironment();
    OrtSession.SessionOptions sessionOptions = new OrtSession.SessionOptions();
    sessionOptions.addCUDA(0);
    OrtSession session =
        env.createSession(
            "/home/anarbek/projects/Reinforce4J/src/main/resources/onnx/models/model.onnx",
            sessionOptions);

    OnnxTensor input =
        OnnxTensor.createTensor(env, FloatBuffer.wrap(new float[42]), new long[] {1, 42});
    Map<String, OnnxTensor> inputs = ImmutableMap.of("input_1", input);
    try (OrtSession.Result results = session.run(inputs)) {
      System.out.println("Outputs:");
      for (NodeInfo i : session.getOutputInfo().values()) {
        System.out.println(i.toString());
      }

      float[][] policy = (float[][]) results.get("policy_output").get().getValue();
      for (float[] row : policy) {
        System.out.println(Arrays.toString(row));
      }

      float[][] value = (float[][]) results.get("value_output").get().getValue();

      for (float[] row : value) {
        System.out.println(Arrays.toString(row));
      }
    }
  }

  @Override
  public void evaluate(GameStateAndEvaluation<T>... nodes) {
    try {
      for (int i = 0; i < nodes.length; i++) {
        if (nodes[i] == null) {
          continue;
        }

        System.arraycopy(nodes[i].state().encode(), 0, batch, i * numFeatures, numFeatures);
      }

      try (OrtSession.Result results =
          session.run(ImmutableMap.of(INPUT, OnnxTensor.createTensor(env, buffer, shape)))) {

        float[][] value = (float[][]) results.get(VALUE_OUTPUT).get().getValue();
        float[][] policy = (float[][]) results.get(POLICY_OUTPUT).get().getValue();

        for (int i = 0; i < nodes.length; i++) {
          if (nodes[i] == null) {
            continue;
          }
          GameStateAndEvaluation<T> node = nodes[i];
          System.arraycopy(policy[i], 0, node.evaluation().getPolicy(), 0, policy[i].length);
          nodes[i].evaluation().setValue(value[i][0]);
        }
      }
    } catch (OrtException e) {
      throw new RuntimeException(e);
    }
  }
}
