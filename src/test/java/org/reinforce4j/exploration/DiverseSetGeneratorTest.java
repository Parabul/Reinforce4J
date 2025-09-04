package org.reinforce4j.exploration;

import com.google.common.truth.Truth;
import java.util.Arrays;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.reinforce4j.core.GameState;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.NinePebbles;
import org.reinforce4j.games.TicTacToe;

class DiverseSetGeneratorTest {

  @Test
  void shouldGenerateDiverseSet() {
    RandomStateGenerator stateGenerator =
        new DiverseSetGenerator(
            new MaxMinDiverseSet(10_000, new ManhattanDistance()),
            new RandomWalk(new TicTacToe(), TicTacToe.NUM_MOVES),
            10_000);

    Set<GameState> states = stateGenerator.get();

    Truth.assertThat(states.size()).isAtLeast(1000);
  }

  @Test
  void shouldGenerateDiverseSetConnect4() {
    BloomMiniBatchKMeansDiverseSet diverseSet =
        new BloomMiniBatchKMeansDiverseSet(1_000, 100, 5_000, 10_000, 500);
    RandomStateGenerator stateGenerator =
        new DiverseSetGenerator(
            diverseSet, new RandomWalk(new Connect4(), Connect4.NUM_MOVES), 20_000);
    Set<GameState> states = stateGenerator.get();

    Truth.assertThat(states.size()).isAtLeast(9_000);
  }

  @Test
  void shouldGenerateDiverseSetNinePebbles() {
    BloomMiniBatchKMeansDiverseSet diverseSet =
        new BloomMiniBatchKMeansDiverseSet(10_000, 100, 800_000, 10_000, 1000);
    RandomStateGenerator stateGenerator =
        new DiverseSetGenerator(
            diverseSet, new RandomWalk(new NinePebbles(), NinePebbles.NUM_MOVES), 10_000);
    Set<GameState> states = stateGenerator.get();
    float[] specials = new float[18];
    for (GameState state : states) {
      float[] encoded = state.encode();
      for (int i = 0; i < 18; i++) {
        specials[i] += encoded[i];
      }
    }
    System.out.println(Arrays.toString(specials));

    Truth.assertThat(specials)
        .usingTolerance(0.001)
        .containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    Truth.assertThat(states.size()).isAtLeast(9_000);
  }

//  @Test
  void shouldGenerateDiverseSetNinePebblesExtended() {
    BloomMiniBatchKMeansDiverseSet diverseSet =
        new BloomMiniBatchKMeansDiverseSet(10_000, 100, 3_000_000, 50_000, 1000);
    RandomStateGenerator stateGenerator =
        new DiverseSetGenerator(
            diverseSet, new RandomWalk(new NinePebbles(), NinePebbles.NUM_MOVES), 35_000);
    Set<GameState> states = stateGenerator.get();

    Truth.assertThat(states.size()).isAtLeast(50_000);
  }
}
