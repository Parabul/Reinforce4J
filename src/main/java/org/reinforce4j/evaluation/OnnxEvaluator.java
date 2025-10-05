package org.reinforce4j.evaluation;

import ai.onnxruntime.*;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import java.nio.FloatBuffer;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;

public class OnnxEvaluator implements Evaluator {

  public static final String CONNECT4_V0 =
      TensorflowEvaluator.class.getResource("/onnx/models/connect4_v0.onnx").getPath();

  public static final String CONNECT4_V1 =
      TensorflowEvaluator.class.getResource("/onnx/models/connect4_v1.onnx").getPath();

  public static final String TIC_TAC_TOE_V0 =
          TensorflowEvaluator.class.getResource("/onnx/models/tic_tac_toe_v0.onnx").getPath();

  public static final String NINE_PEBBLES_V0 =
          TensorflowEvaluator.class.getResource("/onnx/models/nine_pebbles_v0.onnx").getPath();

  public static final String NINE_PEBBLES_V1 =
          TensorflowEvaluator.class.getResource("/onnx/models/nine_pebbles_v1.onnx").getPath();

  public static final String NINE_PEBBLES_V2 =
          TensorflowEvaluator.class.getResource("/onnx/models/nine_pebbles_v2.onnx").getPath();


  private static final String INPUT = "input_1";
  private static final String VALUE_OUTPUT = "value_output";
  private static final String POLICY_OUTPUT = "policy_output";

  private final OrtEnvironment env;
  private final OrtSession session;
  private final float[] batch;
  private final long[] shape;
  private final int numFeatures;
  private final FloatBuffer buffer;

  @Inject
  public OnnxEvaluator(
      String modelPath, NumberOfFeatures numberOfFeatures, NumberOfMoves numberOfMoves) {
    env = OrtEnvironment.getEnvironment();
    OrtSession.SessionOptions sessionOptions = new OrtSession.SessionOptions();
    try {
      //    sessionOptions.addCUDA(0);
//      sessionOptions.addTensorrt(0);
      sessionOptions.addCPU(true);
      session = env.createSession(modelPath, sessionOptions);
    } catch (OrtException e) {
      throw new RuntimeException(e);
    }

    this.batch = new float[numberOfMoves.value() * numberOfFeatures.value()];
    this.buffer = FloatBuffer.wrap(batch);
    this.shape = new long[] {numberOfMoves.value(), numberOfFeatures.value()};
    this.numFeatures = numberOfFeatures.value();
  }

  @Override
  public void evaluate(EvaluatedGameState... nodes) {
    try {
      for (int i = 0; i < nodes.length; i++) {
        if (nodes[i] == null) {
          continue;
        }

        System.arraycopy(nodes[i].state().encode(), 0, batch, i * numFeatures, numFeatures);
      }

      try (OnnxTensor inputTensor = OnnxTensor.createTensor(env, buffer, shape);
          OrtSession.Result results = session.run(ImmutableMap.of(INPUT, inputTensor))) {

        float[][] value = (float[][]) results.get(VALUE_OUTPUT).get().getValue();
        float[][] policy = (float[][]) results.get(POLICY_OUTPUT).get().getValue();

        for (int i = 0; i < nodes.length; i++) {
          if (nodes[i] == null) {
            continue;
          }
          EvaluatedGameState node = nodes[i];
          System.arraycopy(policy[i], 0, node.evaluation().getPolicy(), 0, policy[i].length);
          nodes[i].evaluation().setValue(value[i][0]);
        }
      }
    } catch (OrtException e) {
      throw new RuntimeException(e);
    }
  }
}
