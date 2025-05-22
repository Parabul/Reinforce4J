package org.reinforce4j.utils.tfrecord;

import org.tensorflow.example.Example;
import org.tensorflow.example.Feature;
import org.tensorflow.example.Features;
import org.tensorflow.example.FloatList;

public class TensorFlowUtils {

  public static final String INPUT = "input";
  public static final String OUTPUT = "output";

  private static Feature floatList(float[] nums) {
    FloatList.Builder floatList = FloatList.newBuilder();

    for (float num : nums) {
      floatList.addValue(num);
    }

    return Feature.newBuilder().setFloatList(floatList).build();
  }

  public static Example toExample(float[] input, float[] output) {
    return Example.newBuilder()
        .setFeatures(
            Features.newBuilder()
                .putFeature(INPUT, TensorFlowUtils.floatList(input))
                .putFeature(OUTPUT, TensorFlowUtils.floatList(output)))
        .build();
  }
}
