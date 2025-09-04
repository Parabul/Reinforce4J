package org.reinforce4j.exploration;

import com.google.common.hash.BloomFilter;
import java.util.*;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.GameStateFunnel;
import org.reinforce4j.learning.clustering.MiniBatchKMeansCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BloomMiniBatchKMeansDiverseSet implements DiverseSet {

  private static final Logger logger =
      LoggerFactory.getLogger(BloomMiniBatchKMeansDiverseSet.class);

  private final BloomFilter<GameState> bloomFilter;
  private final MiniBatchKMeansCluster cluster;
  private final MaxMinDiverseSet[] diverseSets;

  private final List<GameState> initialStates;

  private final int initialCapacity;
  private final int totalCapacity;
  private final int numClusters;

  public BloomMiniBatchKMeansDiverseSet(
      int batchSize, int numIterations, int initialCapacity, int totalCapacity, int numClusters) {
    this.bloomFilter = BloomFilter.create(GameStateFunnel.INSTANCE, Integer.MAX_VALUE);
    this.cluster = new MiniBatchKMeansCluster(batchSize, numIterations);
    this.diverseSets = new MaxMinDiverseSet[numClusters];

    int subsetCapacity = totalCapacity / numClusters;
    for (int i = 0; i < diverseSets.length; i++) {
      diverseSets[i] = new MaxMinDiverseSet(subsetCapacity, new EuclideanDistance());
    }
    this.initialCapacity = initialCapacity;
    this.initialStates = Collections.synchronizedList(new ArrayList<>(initialCapacity));
    this.totalCapacity = totalCapacity;
    this.numClusters = numClusters;
  }

  @Override
  public boolean offer(GameState gameState) {
    if (bloomFilter.mightContain(gameState)) {
      return false;
    }
    bloomFilter.put(gameState);

    if (initialStates.size() <= initialCapacity - 1) {
      initialStates.add(gameState);
      if (initialStates.size() == initialCapacity - 1) {
        logger.info("Starting clustering");
        int[] assignments = cluster.fit(initialStates, numClusters);
        logger.info("Clustering completed");
        for (int i = 0; i < assignments.length; i++) {
          diverseSets[assignments[i]].offer(initialStates.get(i));
        }
        logger.info("Reassigning diverse sets completed");
      }
      return true;
    }

    int assignment = cluster.predict(gameState);
    return diverseSets[assignment].offer(gameState);
  }

  @Override
  public Set<GameState> get() {
    Set<GameState> result = new HashSet<>(totalCapacity);
    for (int i = 0; i < diverseSets.length; i++) {
      result.addAll(diverseSets[i].get());
    }
    return result;
  }
}
