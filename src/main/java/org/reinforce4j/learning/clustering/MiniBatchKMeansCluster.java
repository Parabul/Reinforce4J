package org.reinforce4j.learning.clustering;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jsat.DataSet;
import jsat.clustering.kmeans.MiniBatchKMeans;
import jsat.linear.Vec;
import jsat.linear.distancemetrics.DistanceMetric;
import org.reinforce4j.core.GameState;

public class MiniBatchKMeansCluster implements GameStatesCluster {

  private final MiniBatchKMeans miniBatchKMeans;

  public MiniBatchKMeansCluster(int batchSize, int numIterations) {
    this.miniBatchKMeans = new MiniBatchKMeans(batchSize, numIterations);
  }

  @Override
  public int[] fit(List<GameState> gameStates, int numClusters) {
    DataSet dataSet = JSATUtils.toDataSet(gameStates);
    int[] assignments = new int[gameStates.size()];
    ExecutorService pool = Executors.newFixedThreadPool(12);
    miniBatchKMeans.cluster(dataSet, numClusters, pool, assignments);

    return assignments;
  }

  @Override
  public int predict(GameState gameState) {
    Vec stateVec = JSATUtils.toVec(gameState);
    DistanceMetric metric = miniBatchKMeans.getDistanceMetric();
    int predictedCluster = -1;
    double minDistance = Double.MAX_VALUE;
    List<Vec> means = miniBatchKMeans.getMeans();
    for (int i = 0; i < means.size(); i++) {
      Vec mean = means.get(i);
      double dist = metric.dist(mean, stateVec);
      if (dist < minDistance) {
        minDistance = metric.dist(mean, stateVec);
        predictedCluster = i;
      }
    }
    return predictedCluster;
  }
}
