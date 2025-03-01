package org.reinforce4j.games;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reinforce4j.core.Player;

class Connect4Test {
  private Connect4 game;

  @BeforeEach
  void setUp() {
    game = new Connect4(new int[6][7], Player.ONE);
  }

  @Test
  void testInitialBoardIsEmpty() {
    int[][] board = game.getBoard();
    for (int[] row : board) {
      for (int cell : row) {
        assertEquals(0, cell);
      }
    }
  }

  @Test
  void testMakeMove() {
    game.move(3);
    assertEquals(1, game.getBoard()[5][3]); // Player.ONE moves to bottom-most available row
  }

  @Test
  void testMakeMoveAlternatesPlayers() {
    game.move(2);
    assertEquals(Player.TWO, game.getCurrentPlayer());
    game.move(2);
    assertEquals(Player.ONE, game.getCurrentPlayer());
  }

  @Test
  void testisMoveAllowed() {
    assertTrue(game.isMoveAllowed(2));
    for (int i = 0; i < 6; i++) {
      game.move(2);
    }
    assertFalse(game.isMoveAllowed(2)); // Column is full
  }

  @Test
  void testVerticalWin() {
    for (int i = 0; i < 3; i++) {
      game.move(2);
      game.move(3); // Alternate moves to avoid early win
    }
    game.move(2);
    assertTrue(game.isGameOver());
    assertEquals(Player.ONE, game.getWinner());
  }

  @Test
  void testHorizontalWin() {
    for (int i = 0; i < 3; i++) {
      game.move(i);
      game.move(i); // Alternate moves to avoid early win
    }
    game.move(3);
    assertTrue(game.isGameOver());
    assertEquals(Player.ONE, game.getWinner());
  }

  @Test
  public void copiesStates() {
    Connect4 from = (new Connect4Service()).newInitialState();
    from.move(1);
    from.move(5);

    Connect4 to = (new Connect4Service()).newInitialState();
    assertThat(to).isNotEqualTo(from);

    to.copy(from);
    assertThat(to).isEqualTo(from);

    from.move(5);
    assertThat(to).isNotEqualTo(from);
  }

  @Test
  public void noMovesGameOver() {

    Connect4 almostGameOver =
        new Connect4(
            new int[][] {
              {1, -1, 1, 1, -1, 1, 0},
              {-1, 1, 1, -1, -1, 1, 1},
              {1, -1, -1, -1, 1, 1, -1},
              {-1, 1, 1, -1, -1, -1, 1},
              {1, -1, -1, 1, 1, 1, -1},
              {-1, 1, -1, -1, -1, 1, 1}
            },
            Player.TWO);

    assertFalse(almostGameOver.isGameOver());
    assertNull(almostGameOver.getWinner());

    almostGameOver.move(6);

    assertTrue(almostGameOver.isGameOver());
    assertEquals(Player.NONE, almostGameOver.getWinner());
  }
}
