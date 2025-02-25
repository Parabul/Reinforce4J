package org.reinforce4j.games;

import java.util.Arrays;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;

public class Connect4 implements GameState<Connect4> {
  public static final int ROWS = 6;
  public static final int COLS = 7;

  private final int[][] board = new int[ROWS][COLS];
  private Player currentPlayer;

  private boolean isGameOver = false;
  private Player winner = null;

  /*package private*/ Connect4(int[][] board, Player currentPlayer) {
    for (int i = 0; i < ROWS; i++) {
      System.arraycopy(board[i], 0, this.board[i], 0, COLS);
    }
    this.currentPlayer = currentPlayer;
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  public int[][] getBoard() {
    return board;
  }

  @Override
  public boolean isMoveAllowed(int col) {
    return col >= 0 && col < COLS && board[0][col] == 0;
  }

  @Override
  public void move(int col) {
    int lastRow = -1;
    for (int row = ROWS - 1; row >= 0; row--) {
      if (board[row][col] == 0) {
        board[row][col] = currentPlayer == Player.ONE ? 1 : -1;
        lastRow = row;
        break;
      }
    }

    isGameOver = checkGameOver(lastRow, col);
    if (isGameOver) {
      winner = lastRow != -1 ? currentPlayer : Player.NONE;
    }

    currentPlayer = currentPlayer.opponent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Connect4)) return false;
    Connect4 connect4 = (Connect4) o;
    return Arrays.deepEquals(board, connect4.board);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(board);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int[] row : board) {
      for (int cell : row) {
        sb.append(cell == 0 ? '.' : (cell == 1 ? 'X' : 'O')).append(' ');
      }
      sb.append('\n');
    }
    sb.append("0 1 2 3 4 5 6\n");
    return sb.toString();
  }

  @Override
  public void copy(Connect4 other) {
    for (int i = 0; i < Connect4.ROWS; i++) {
      System.arraycopy(other.board[i], 0, this.board[i], 0, Connect4.COLS);
    }
    this.currentPlayer = other.currentPlayer;
    this.winner = other.winner;
    this.isGameOver = other.isGameOver;
  }

  private boolean checkGameOver(int row, int col) {
    int player = board[row][col];
    return countConsecutive(row, col, 1, 0, player) + countConsecutive(row, col, -1, 0, player) >= 3
            || countConsecutive(row, col, 0, 1, player) + countConsecutive(row, col, 0, -1, player) >= 3
            || countConsecutive(row, col, 1, 1, player) + countConsecutive(row, col, -1, -1, player)
            >= 3
            || countConsecutive(row, col, 1, -1, player) + countConsecutive(row, col, -1, 1, player)
            >= 3;
  }

  private int countConsecutive(int row, int col, int dRow, int dCol, int player) {
    int count = 0;
    for (int i = 1; i < 4; i++) {
      int r = row + dRow * i;
      int c = col + dCol * i;
      if (r < 0 || r >= ROWS || c < 0 || c >= COLS || board[r][c] != player) break;
      count++;
    }
    return count;
  }
}
