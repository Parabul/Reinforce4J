package org.reinforce4j.games;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;

public class TicTacToe implements GameState<TicTacToe> {

  private final int[][] cells = new int[3][3];
  private Player currentPlayer;

  private boolean isGameOver = false;
  private Player winner = null;

  /*package private*/ TicTacToe(int[][] cells, Player currentPlayer) {
    for (int i = 0; i < 3; i++) {
      System.arraycopy(cells[i], 0, this.cells[i], 0, 3);
    }
    this.currentPlayer = currentPlayer;
  }

  @Override
  public void copy(TicTacToe other) {
    for (int i = 0; i < 3; i++) {
      System.arraycopy(other.cells[i], 0, this.cells[i], 0, 3);
    }
    this.currentPlayer = other.currentPlayer;
    this.winner = other.winner;
    this.isGameOver = other.isGameOver;
  }

  public int[][] getCells() {
    return cells;
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public boolean isMoveAllowed(int move) {
    int i = move / 3;
    int j = move % 3;
    return cells[i][j] == 0;
  }

  @Override
  public void encode(float[] buffer) {
    Preconditions.checkArgument(buffer.length == 9);
    for (int move = 0; move < 9; move++) {
      int i = move / 3;
      int j = move % 3;

      buffer[move] = cells[i][j];
    }
  }

  @Override
  public float[] encode() {
    float[] buffer = new float[9];
    encode(buffer);
    return buffer;
  }

  @Override
  public void move(int move) {
    int i = move / 3;
    int j = move % 3;
    cells[i][j] = currentPlayer == Player.ONE ? 1 : -1;

    isGameOver = checkGameOver();
    if (isGameOver) {
      winner = checkWinner();
    }

    currentPlayer = currentPlayer.opponent;
  }

  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  public boolean checkGameOver() {
    for (int i = 0; i < 3; i++) {
      if (cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2] && cells[i][1] != 0) {
        return true;
      }
      if (cells[0][i] == cells[1][i] && cells[1][i] == cells[2][i] && cells[1][i] != 0) {
        return true;
      }
    }

    if (cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2] && cells[1][1] != 0) {
      return true;
    }

    if (cells[2][0] == cells[1][1] && cells[1][1] == cells[0][2] && cells[1][1] != 0) {
      return true;
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (cells[i][j] == 0) {
          return false;
        }
      }
    }

    return true;
  }

  public Player checkWinner() {
    if (!isGameOver()) {
      return null;
    }

    for (int i = 0; i < 3; i++) {
      if (cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2] && cells[i][1] != 0) {
        return cells[i][1] == 1 ? Player.ONE : Player.TWO;
      }
      if (cells[0][i] == cells[1][i] && cells[1][i] == cells[2][i] && cells[1][i] != 0) {
        return cells[1][i] == 1 ? Player.ONE : Player.TWO;
      }
    }

    if (cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2] && cells[1][1] != 0) {
      return cells[1][1] == 1 ? Player.ONE : Player.TWO;
    }

    if (cells[2][0] == cells[1][1] && cells[1][1] == cells[0][2] && cells[1][1] != 0) {
      return cells[1][1] == 1 ? Player.ONE : Player.TWO;
    }

    return Player.NONE;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (cells[i][j] == 0) {
          sb.append(' ');
        } else if (cells[i][j] == 1) {
          sb.append('X');
        } else {
          sb.append('O');
        }
        if (j < 2) {
          sb.append('|');
        }
      }
      sb.append('\n');
      if (i < 2) {
        sb.append("-----\n");
      }
    }
    sb.append("\n");
    sb.append("Current player: " + currentPlayer + "\n");
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TicTacToe)) return false;
    TicTacToe ticTacToe = (TicTacToe) o;
    return Arrays.deepEquals(cells, ticTacToe.cells);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(cells);
  }
}
