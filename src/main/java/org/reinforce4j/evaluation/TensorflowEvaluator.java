package org.reinforce4j.evaluation;

import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.tensorflow.Result;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Signature;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.ndarray.StdArrays;
import org.tensorflow.ndarray.buffer.DataBuffers;
import org.tensorflow.types.TFloat32;

public class TensorflowEvaluator implements Evaluator {

  public static final String TIC_TAC_TOE_V1 =
      TensorflowEvaluator.class.getResource("/tensorflow/models/tic_tac_toe_v1/").getPath();

  public static final String TIC_TAC_TOE_V3 =
      TensorflowEvaluator.class.getResource("/tensorflow/models/tic_tac_toe_v3/").getPath();

  public static final String CONNECT4_V1 =
      TensorflowEvaluator.class.getResource("/tensorflow/models/connect4/v1/").getPath();

  private static final String SERVE_TAG = "serve";
  private static final String FUNCTION_NAME = "serving_default";
  private static final String INPUT = "input_1";
  private static final String VALUE_OUTPUT = "value_output";
  private static final String POLICY_OUTPUT = "policy_output";

  private final Session session;
  private final String inputName;
  private final String valueOutputName;
  private final String policyOutputName;
  private final float[] batch;
  private final Shape shape;
  private final int numFeatures;

  public TensorflowEvaluator(
      String modelPath, NumberOfMoves numberOfMoves, NumberOfFeatures numberOfFeatures) {
    SavedModelBundle model = SavedModelBundle.load(modelPath, SERVE_TAG);
    Signature signature = model.function(FUNCTION_NAME).signature();
    this.inputName = signature.getInputs().get(INPUT).name;
    this.valueOutputName = signature.getOutputs().get(VALUE_OUTPUT).name;
    this.policyOutputName = signature.getOutputs().get(POLICY_OUTPUT).name;
    this.session = model.session();
    this.batch = new float[numberOfMoves.value() * numberOfFeatures.value()];
    this.shape = Shape.of(numberOfMoves.value(), numberOfFeatures.value());
    this.numFeatures = numberOfFeatures.value();
  }

  @Override
  public void evaluate(EvaluatedGameState... nodes) {
    for (int i = 0; i < nodes.length; i++) {
      if (nodes[i] == null) {
        continue;
      }

      System.arraycopy(nodes[i].state().encode(), 0, batch, i * numFeatures, numFeatures);
    }

    TFloat32 input = TFloat32.tensorOf(shape, DataBuffers.of(batch));

    Result result =
        session
            .runner()
            .feed(inputName, input)
            .fetch(valueOutputName)
            .fetch(policyOutputName)
            .run();

    float[][] valueOutput =
        StdArrays.array2dCopyOf(
            (TFloat32) result.get(valueOutputName).orElseThrow(IllegalStateException::new));
    float[][] policyOutput =
        StdArrays.array2dCopyOf(
            (TFloat32) result.get(policyOutputName).orElseThrow(IllegalStateException::new));

    for (int i = 0; i < nodes.length; i++) {
      if (nodes[i] == null) {
        continue;
      }
      EvaluatedGameState node = nodes[i];
      System.arraycopy(
          policyOutput[i], 0, node.evaluation().getPolicy(), 0, policyOutput[i].length);
      nodes[i].evaluation().setValue(valueOutput[i][0]);
    }
  }
}
