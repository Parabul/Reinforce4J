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

  //  @Test
  //  public void specialCellHappyPath() {
  //    State state =
  //            new State(
  //                    ImmutableMap.of(0, 1, 1, 1, 9, 2, 10, 9),
  //                    ImmutableMap.of(Player.ONE, 0, Player.TWO, 0),
  //                    ImmutableMap.of(),
  //                    Player.ONE);
  //
  //    State newState = Policy.makeMove(state, 9);
  //
  //    assertThat(newState.nextMove).isEqualTo(Player.TWO);
  //    assertThat(newState.score).isEqualTo(scoreOf(3, 0));
  //    assertThat(newState.specialCells).isEqualTo(specialOne(9));
  //    assertThat(newState.cells)
  //            .asList()
  //            .containsExactly(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0)
  //            .inOrder();
  //  }
  //
  //  @Test
  //  public void specialCellNotAllowedInMirror() {
  //    State state =
  //            new State(
  //                    ImmutableMap.of(0, 1, 1, 1, 9, 2, 10, 9),
  //                    ImmutableMap.of(Player.ONE, 0, Player.TWO, 0),
  //                    ImmutableMap.of(Player.TWO, 8),
  //                    Player.ONE);
  //
  //    State newState = Policy.makeMove(state, 9);
  //
  //    assertThat(newState.nextMove).isEqualTo(Player.TWO);
  //    assertThat(newState.score).isEqualTo(ImmutableMap.of(Player.ONE, 0, Player.TWO, 0));
  //    assertThat(newState.specialCells).isEqualTo(specialTwo(8));
  //    assertThat(newState.cells)
  //            .asList()
  //            .containsExactly(0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 9, 0, 0, 0, 0, 0, 0, 0)
  //            .inOrder();
  //  }
  //
  //  @Test
  //  public void specialCellIsFinal() {
  //    State state =
  //            new State(
  //                    ImmutableMap.of(0, 1, 1, 1, 9, 2, 10, 9),
  //                    ImmutableMap.of(Player.ONE, 0, Player.TWO, 0),
  //                    ImmutableMap.of(Player.ONE, 16),
  //                    Player.ONE);
  //
  //    State newState = Policy.makeMove(state, 9);
  //
  //    assertThat(newState.nextMove).isEqualTo(Player.TWO);
  //    assertThat(newState.score).isEqualTo(ImmutableMap.of(Player.ONE, 0, Player.TWO, 0));
  //    assertThat(newState.specialCells).isEqualTo(specialOne(16));
  //    assertThat(newState.cells)
  //            .asList()
  //            .containsExactly(0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 9, 0, 0, 0, 0, 0, 0, 0)
  //            .inOrder();
  //  }
  //
  //  @Test
  //  public void specialCellIsNotNine() {
  //    State state =
  //            new State(
  //                    ImmutableMap.of(0, 10, 1, 4, 9, 2, 17, 2),
  //                    ImmutableMap.of(Player.ONE, 0, Player.TWO, 0),
  //                    ImmutableMap.of(),
  //                    Player.ONE);
  //
  //    State newState = Policy.makeMove(state, 9);
  //
  //    assertThat(newState.nextMove).isEqualTo(Player.TWO);
  //    assertThat(newState.score).isEqualTo(ImmutableMap.of(Player.ONE, 0, Player.TWO, 0));
  //    assertThat(newState.specialCells).isEqualTo(ImmutableMap.of());
  //    assertThat(newState.cells)
  //            .asList()
  //            .containsExactly(1, 4, 0, 0, 0, 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 3)
  //            .inOrder();
  //  }
  //
  //  @Test
  //  public void specialCellIncrementsScore() {
  //    State state =
  //            new State(
  //                    ImmutableMap.of(0, 6, 1, 7, 9, 4),
  //                    ImmutableMap.of(Player.ONE, 0, Player.TWO, 0),
  //                    ImmutableMap.of(Player.ONE, 10),
  //                    Player.ONE);
  //
  //    State newState = Policy.makeMove(state, 8);
  //
  //    assertThat(newState.nextMove).isEqualTo(Player.TWO);
  //    assertThat(newState.score).isEqualTo(scoreOf(1, 0));
  //    assertThat(newState.specialCells).isEqualTo(specialOne(10));
  //    assertThat(newState.cells)
  //            .asList()
  //            .containsExactly(7, 1, 0, 0, 0, 0, 0, 0, 0, 5, 0, 1, 1, 1, 0, 0, 0, 0)
  //            .inOrder();
  //  }
}
