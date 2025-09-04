package org.reinforce4j.learning.clustering;

import java.util.List;
import java.util.stream.Collectors;
import jsat.DataSet;
import jsat.SimpleDataSet;
import jsat.classifiers.DataPoint;
import jsat.linear.DenseVector;
import jsat.linear.Vec;
import org.reinforce4j.core.GameState;

/** Utils class */
public class JSATUtils {

  public static DataSet toDataSet(List<GameState> gameStates) {
    return new SimpleDataSet(
        gameStates.stream().map(st -> new DataPoint(toVec(st))).collect(Collectors.toList()));
  }

  public static Vec toVec(GameState gameState) {
    float[] encoded = gameState.encode();
    double[] doubleVectorData = new double[encoded.length];
    for (int j = 0; j < encoded.length; j++) {
      doubleVectorData[j] = encoded[j];
    }
    return new DenseVector(doubleVectorData);
  }
}
