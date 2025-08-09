package org.reinforce4j.evaluation.batch;

import ai.onnxruntime.*;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import java.nio.FloatBuffer;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.evaluation.EvaluatedGameState;
import org.reinforce4j.evaluation.Evaluator;

public class BatchOnnxEvaluator implements Evaluator {

  private static final String INPUT = "input_1";
  private static final String VALUE_OUTPUT = "value_output";
  private static final String POLICY_OUTPUT = "policy_output";

  private final OrtEnvironment env;
  private final OrtSession session;
  private final int numberOfFeatures;

  @Inject
  public BatchOnnxEvaluator(
      String modelPath, NumberOfFeatures numberOfFeatures, boolean gpuEnabled) {
    this.numberOfFeatures = numberOfFeatures.value();
    env = OrtEnvironment.getEnvironment();
    OrtSession.SessionOptions sessionOptions = new OrtSession.SessionOptions();
    try {
      if (gpuEnabled) {
        sessionOptions.addTensorrt(0);
      } else {
        sessionOptions.addCPU(true);
      }
      //    sessionOptions.addCUDA(0);
      //
      session = env.createSession(modelPath, sessionOptions);
    } catch (OrtException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void evaluate(EvaluatedGameState... nodes) {
    try {
      float[] batch = new float[nodes.length * numberOfFeatures];
      FloatBuffer buffer = FloatBuffer.wrap(batch);
      long[] shape = new long[] {nodes.length, numberOfFeatures};

      for (int i = 0; i < nodes.length; i++) {
        if (nodes[i] == null) {
          continue;
        }

        System.arraycopy(
            nodes[i].state().encode(), 0, batch, i * numberOfFeatures, numberOfFeatures);
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
