package org.reinforce4j.learning.clustering;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.reinforce4j.core.GameState;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.utils.TestUtils;

public class MiniBatchKMeansClusterTest {

  @Test
  public void shouldClusterWithRootInLargestCluster() {
    int n = 100000;
    int numClusters = 10;
    int batchSize = 1000;
    int iterations = 100;

    GameStatesCluster gameStatesCluster = new MiniBatchKMeansCluster(batchSize, iterations);

    List<GameState> gameStates = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      gameStates.add(TestUtils.getRandomTicTacToeState());
    }
    int[] assignments = gameStatesCluster.fit(gameStates, numClusters);

    float[] dist = new float[numClusters];
    for (int i = 0; i < n; i++) {
      int assignment = assignments[i];
      dist[assignment] += 1.0f / n;
    }

    // One large bucket
    assertThat(dist)
        .usingTolerance(0.1f)
        .containsExactly(0.3f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f);

    int rootCluster = gameStatesCluster.predict(new TicTacToe());

    System.out.println("rootCluster: " + rootCluster + ", rootCluster Dist: " + dist[rootCluster]);

    // Root falls in the largest cluster
    assertThat(dist[rootCluster]).isGreaterThan(0.2f);
  }
}
