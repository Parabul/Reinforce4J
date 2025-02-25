package org.reinforce4j.utils;

import org.tensorflow.example.Feature;
import org.tensorflow.example.FloatList;

public class TensorFlowUtils {
  public static Feature floatList(float[] nums) {
    FloatList.Builder floatList = FloatList.newBuilder();

    for (float num : nums) {
      floatList.addValue(num);
    }

    return Feature.newBuilder().setFloatList(floatList).build();
  }
}
