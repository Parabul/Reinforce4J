package org.reinforce4j.games;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.reinforce4j.core.Player;

public class NinePebblesTest {
  @Test
  public void stateDefaultConstructor() {
    // 0:0
    // |   9|   9|   9|   9|   9|   9|   9|   9|   9|
    // |   9|   9|   9|   9|   9|   9|   9|   9|   9|
    // Next: ONE
    // Is GameOver: false
    // Winner: null
    NinePebbles state = new NinePebbles();

    assertThat(state.getCurrentPlayer()).isEqualTo(Player.ONE);
    assertThat(state.getWinner()).isNull();
    assertThat(state.isGameOver()).isFalse();
    for (int i = 0; i < 9; i++) {
      assertThat(state.isMoveAllowed(i)).isTrue();
    }

    assertThat(state.encode())
        .usingTolerance(0.001f)
        .containsExactly(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0.11111111f,
            0,
            0,
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

    System.out.println(state);

    // 10:0
    // |   1|   9|   9|   9|   9|   9|   9|   9|   9|
    // |  10|  10|  10|  10|  10|  10|  10|   0|   9|
    // Next: TWO
    // Is GameOver: false
    // Winner: null
    NinePebbles newState = state.move(8);

    System.out.println(newState);
    assertThat(newState.getCurrentPlayer()).isEqualTo(Player.TWO);
    assertThat(newState.getWinner()).isNull();
    assertThat(newState.isGameOver()).isFalse();
    for (int i = 0; i < 9; i++) {
      if (i == 7) {
        assertThat(newState.isMoveAllowed(i)).isFalse();
      } else {
        assertThat(newState.isMoveAllowed(i)).isTrue();
      }
    }

    assertThat(newState.encode())
        .usingTolerance(0.001)
        .containsExactly(
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.0,
            0.11111111,
            0.11111111,
            0.11111111,
            0.11111111,
            0.11111111,
            0.11111111,
            0.11111111,
            0.11111111,
            0.11111111,
            0.012345679,
            0.0,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.12345679,
            0.0,
            0.12345679)
        .inOrder();
  }

  @Test
  public void sparseValuesConstructor() {
    NinePebbles state =
        new NinePebbles(
            Player.TWO,
            ImmutableMap.of(0, 1, 1, 2, 2, 3, 11, 4, 10, 5, 9, 6),
            24,
            21,
            12,
            NinePebbles.SPECIAL_NOT_SET);

    System.out.println(state);
    assertThat(state.encode())
        .usingTolerance(0.001)
        .containsExactly(
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.074074075,
            0.061728396,
            0.049382716,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.037037037,
            0.024691358,
            0.012345679,
            0.25925925,
            0.2962963,
            -0.012345679,
            -0.012345679,
            -0.012345679,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0)
        .inOrder();
    assertThat(state.isGameOver()).isFalse();
    assertThat(state.getCurrentPlayer()).isEqualTo(Player.TWO);
  }

  @Test
  public void sparseValuesConstructorGameOverTie() {
    NinePebbles state = new NinePebbles(Player.TWO, ImmutableMap.of(), 81, 81, 12, 4);
    // 81:81
    // |   0|   0|   0|   0|  0*|   0|   0|   0|   0|
    // |   0|   0|   0|  0*|   0|   0|   0|   0|   0|
    // Current Player: TWO
    // Is GameOver: true
    // Winner: NONE

    System.out.println(state);
    assertThat(state.encode())
        .usingTolerance(0.001)
        .containsExactly(
            0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        .inOrder();
    assertThat(state.isGameOver()).isTrue();
    assertThat(state.getWinner()).isEqualTo(Player.NONE);
    assertThat(state.getCurrentPlayer()).isEqualTo(Player.TWO);
  }

  @Test
  public void sparseValuesConstructorGameOver() {
    NinePebbles state = new NinePebbles(Player.TWO, ImmutableMap.of(0, 9), 81, 72, 12, 4);
    // 81:72
    // |   9|   0|   0|   0|  0*|   0|   0|   0|   0|
    // |   0|   0|   0|  0*|   0|   0|   0|   0|   0|
    // Current Player: TWO
    // Is GameOver: true
    // Winner: ONE

    System.out.println(state);
    assertThat(state.encode())
        .usingTolerance(0.001)
        .containsExactly(
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.11111111,
            0.8888889,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0)
        .inOrder();
    assertThat(state.isGameOver()).isTrue();
    assertThat(state.getWinner()).isEqualTo(Player.ONE);
    assertThat(state.getCurrentPlayer()).isEqualTo(Player.TWO);

    NinePebbles stateInverse = new NinePebbles(Player.ONE, ImmutableMap.of(0, 9), 81, 72, 12, 4);
    // 81:72
    // |   9|   0|   0|   0|  0*|   0|   0|   0|   0|
    // |   0|   0|   0|  0*|   0|   0|   0|   0|   0|
    // Current Player: TWO
    // Is GameOver: true
    // Winner: ONE

    System.out.println(stateInverse);
    assertThat(stateInverse.encode())
        .usingTolerance(0.001)
        .containsExactly(
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.11111111,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.8888889,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.012345679)
        .inOrder();
    assertThat(stateInverse.isGameOver()).isFalse();
    assertThat(stateInverse.getWinner()).isNull();
    assertThat(stateInverse.getCurrentPlayer()).isEqualTo(Player.ONE);
  }
}
