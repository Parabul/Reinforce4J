package org.reinforce4j.games;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        .usingTolerance(0.001)
        .containsExactly(
            new float[] {
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              -1.0f,
              -1.0f,
              0.0f,
              0.0f,
              0.0f
            })
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
            new float[] {
              0.0061728396f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.055555556f,
              0.061728396f,
              0.061728396f,
              0.061728396f,
              0.061728396f,
              0.061728396f,
              0.061728396f,
              0.061728396f,
              0,
              0.055555556f,
              -1,
              -1,
              0.1234567f,
              0.0f,
              1.0f
            })
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
            new float[] {
              0.0061728396f,
              0.012345679f,
              0.018518519f,
              0,
              0,
              0,
              0,
              0,
              0,
              0.037037037f,
              0.030864198f,
              0.024691358f,
              0,
              0,
              0,
              0,
              0,
              0,
              0.375f,
              -1,
              24f / 81f,
              21f / 81f,
              1
            })
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
            new float[] {
              0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.375f, 0.5f, 1, 1, 1
            })
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
            new float[] {
              0.055555556f,
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
              0.375f,
              0.5f,
              1,
              0.8888889f,
              1
            })
        .inOrder();
    assertThat(state.isGameOver()).isTrue();
    assertThat(state.getWinner()).isEqualTo(Player.ONE);
    assertThat(state.getCurrentPlayer()).isEqualTo(Player.TWO);
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
