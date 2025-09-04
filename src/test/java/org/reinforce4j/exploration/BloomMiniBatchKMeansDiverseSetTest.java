package org.reinforce4j.exploration;

import static com.google.common.truth.Truth.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.reinforce4j.core.GameState;
import org.reinforce4j.utils.TestUtils;

class BloomMiniBatchKMeansDiverseSetTest {

  @Test
  void testGet() {
    BloomMiniBatchKMeansDiverseSet diverseSet =
        new BloomMiniBatchKMeansDiverseSet(1000, 100, 10_000, 200_000, 1000);

    int n = 1_000_000;

    for (int i = 0; i < n; i++) {
      diverseSet.offer(TestUtils.getRandomConnect4State());
    }

    Set<GameState> result = diverseSet.get();
    System.out.println(result.size());
    assertThat(result.size()).isAtLeast(10_000);
  }
}
