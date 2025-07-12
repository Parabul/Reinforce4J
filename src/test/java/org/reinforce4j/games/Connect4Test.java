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
    game = new Connect4();
  }

  @Test
  void testInitialBoardIsEmpty() {
    byte[][] board = game.getBoard();
    for (byte[] row : board) {
      for (byte cell : row) {
        assertEquals(0, cell);
      }
    }
  }

  @Test
  void testMakeMove() {
    game = game.move(3);
    assertEquals(1, game.getBoard()[5][3]); // Player.ONE moves to bottom-most available row
  }

  @Test
  void testMakeMoveAlternatesPlayers() {
    game = game.move(2);
    assertEquals(Player.TWO, game.getCurrentPlayer());
    game = game.move(2);
    assertEquals(Player.ONE, game.getCurrentPlayer());
  }

  @Test
  void testisMoveAllowed() {
    assertTrue(game.isMoveAllowed(2));
    for (int i = 0; i < 6; i++) {
      game = game.move(2);
    }
    assertFalse(game.isMoveAllowed(2)); // Column is full
  }

  @Test
  void testVerticalWin() {
    for (int i = 0; i < 3; i++) {
      game = game.move(2);
      game = game.move(3); // Alternate moves to avoid early win
    }
    game = game.move(2);
    assertTrue(game.isGameOver());
    assertEquals(Player.ONE, game.getWinner());
  }

  @Test
  void testHorizontalWin() {
    for (int i = 0; i < 3; i++) {
      game = game.move(i);
      game = game.move(i); // Alternate moves to avoid early win
    }
    game = game.move(3);
    assertTrue(game.isGameOver());
    assertEquals(Player.ONE, game.getWinner());
  }

  @Test
  void testEncode() {
    Connect4 game = new Connect4();

    game = game.move(3);
    game = game.move(3);
    assertThat(game.encode())
        .usingTolerance(0.0001)
        .containsExactly(
            new float[] {
              0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f
            });
  }

  @Test
  public void noMovesGameOver() {

    Connect4 almostGameOver =
        new Connect4(
            new byte[][] {
              {1, -1, 1, 1, -1, 1, 0},
              {-1, 1, 1, -1, -1, 1, 1},
              {1, -1, -1, -1, 1, 1, -1},
              {-1, 1, 1, -1, -1, -1, 1},
              {1, -1, -1, 1, 1, 1, -1},
              {-1, 1, -1, -1, -1, 1, 1}
            },
            Player.TWO,
            false,
            null);

    assertFalse(almostGameOver.isGameOver());
    assertNull(almostGameOver.getWinner());

    Connect4 gameOver = almostGameOver.move(6);

    assertTrue(gameOver.isGameOver());
    assertEquals(Player.NONE, gameOver.getWinner());
  }
}
