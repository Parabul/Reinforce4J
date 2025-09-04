package org.reinforce4j.games;

import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;

public class TicTacToe implements GameState {

  public static final int NUM_MOVES = 9;
  public static final int NUM_FEATURES = 9;
  private static final byte PLAYER_ONE_VALUE = 1;
  private static final byte PLAYER_TWO_VALUE = -1;
  private static final int SIZE = 3;
  private final byte[][] board = new byte[SIZE][SIZE];
  private final Player currentPlayer;
  private final boolean isGameOver;
  private final Player winner;

  public TicTacToe() {
    this(new byte[SIZE][SIZE], Player.ONE, false, null);
  }

  @VisibleForTesting
  TicTacToe(byte[][] board, Player currentPlayer, boolean isGameOver, Player winner) {
    for (int i = 0; i < SIZE; i++) {
      System.arraycopy(board[i], 0, this.board[i], 0, SIZE);
    }
    this.currentPlayer = currentPlayer;
    this.isGameOver = isGameOver;
    this.winner = winner;
  }

  private static Player getWinner(byte[][] board) {
    for (int i = 0; i < SIZE; i++) {
      if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][1] != 0) {
        return board[i][1] == 1 ? Player.ONE : Player.TWO;
      }
      if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[1][i] != 0) {
        return board[1][i] == 1 ? Player.ONE : Player.TWO;
      }
    }

    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != 0) {
      return board[1][1] == 1 ? Player.ONE : Player.TWO;
    }

    if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[1][1] != 0) {
      return board[1][1] == 1 ? Player.ONE : Player.TWO;
    }

    return Player.NONE;
  }

  private static boolean checkGameOver(byte[][] board) {
    for (int i = 0; i < SIZE; i++) {
      if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][1] != 0) {
        return true;
      }
      if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[1][i] != 0) {
        return true;
      }
    }

    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != 0) {
      return true;
    }

    if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[1][1] != 0) {
      return true;
    }

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == 0) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public boolean isMoveAllowed(int move) {
    int i = move / SIZE;
    int j = move % SIZE;
    return board[i][j] == 0;
  }

  @Override
  public float[] encode() {
    float[] buffer = new float[9];
    for (int move = 0; move < 9; move++) {
      int i = move / SIZE;
      int j = move % SIZE;

      buffer[move] = board[i][j];
    }
    return buffer;
  }

  @Override
  public Player getPotentialWinner() {
    return Player.NONE;
  }

  @Override
  public TicTacToe move(int move) {
    int i = move / SIZE;
    int j = move % SIZE;

    byte[][] newBoard = new byte[SIZE][SIZE];
    for (int k = 0; k < SIZE; k++) {
      System.arraycopy(this.board[k], 0, newBoard[k], 0, SIZE);
    }

    newBoard[i][j] = currentPlayer == Player.ONE ? PLAYER_ONE_VALUE : PLAYER_TWO_VALUE;

    boolean isGameOver = checkGameOver(newBoard);
    Player winner = null;
    if (isGameOver) {
      winner = getWinner(newBoard);
    }

    Player currentPlayer = this.currentPlayer.opponent;

    return new TicTacToe(newBoard, currentPlayer, isGameOver, winner);
  }

  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == 0) {
          sb.append(' ');
        } else if (board[i][j] == 1) {
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
    return Arrays.deepEquals(board, ticTacToe.board);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(board);
  }
}
