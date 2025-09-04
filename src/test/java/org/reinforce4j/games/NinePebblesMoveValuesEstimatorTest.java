package org.reinforce4j.games;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;

class NinePebblesMoveValuesEstimatorTest {

  @Test
  void estimateMoveValuesForRoot() {
    NinePebbles root = new NinePebbles();
    NinePebblesMoveValuesEstimator estimator = new NinePebblesMoveValuesEstimator();
    Truth.assertThat(estimator.estimateMoveValues(root))
        .usingTolerance(0.01f)
        .containsExactly(
            0.0,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679)
        .inOrder();
  }

  @Test
  void estimateMoveValuesForNonRoot() {
    NinePebbles root = new NinePebbles();
    NinePebbles state1 = root.move(6);
    System.out.println(state1);
    NinePebblesMoveValuesEstimator estimator = new NinePebblesMoveValuesEstimator();
    Truth.assertThat(estimator.estimateMoveValues(state1))
        .usingTolerance(0.01f)
        .containsExactly(
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.0,
            0.12345679,
            0.024691358,
            0.0)
        .inOrder();
  }
}
