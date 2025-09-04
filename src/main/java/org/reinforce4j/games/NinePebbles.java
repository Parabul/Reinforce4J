package org.reinforce4j.games;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;

public class NinePebbles implements GameState {

  public static final int NUM_MOVES = 9;
  public static final int NUM_FEATURES = 47;
  public static final int SPECIAL_NOT_SET = -1;

  private final Player currentPlayer;
  private final boolean isGameOver;
  @Nullable private final Player winner;
  private final byte scoreOne;
  private final byte scoreTwo;
  // -1 special cell not set
  // For ONE possible range: [9-17]
  // For TWO possible range: [0-8]
  private final byte specialOne;
  private final byte specialTwo;
  // 0-8  player one
  // 9-17 player two
  private final byte[] cells;

  public NinePebbles() {
    cells = new byte[18];
    Arrays.fill(cells, (byte) 9);
    currentPlayer = Player.ONE;
    isGameOver = false;
    winner = null;
    scoreOne = 0;
    scoreTwo = 0;
    specialOne = SPECIAL_NOT_SET;
    specialTwo = SPECIAL_NOT_SET;
  }

  private NinePebbles(
      Player currentPlayer,
      byte scoreOne,
      byte scoreTwo,
      byte specialOne,
      byte specialTwo,
      byte[] cells) {
    this.currentPlayer = currentPlayer;
    this.scoreOne = scoreOne;
    this.scoreTwo = scoreTwo;
    this.specialOne = specialOne;
    this.specialTwo = specialTwo;
    this.cells = cells;
    this.isGameOver = checkGameOver();
    this.winner = checkWinner();
  }

  /** Sparse initialization. */
  public NinePebbles(
      Player currentPlayer,
      Map<Integer, Integer> nonZeroValues,
      int scoreOne,
      int scoreTwo,
      int specialOne,
      int specialTwo) {
    this.currentPlayer = currentPlayer;
    this.scoreOne = (byte) scoreOne;
    this.scoreTwo = (byte) scoreTwo;
    this.specialOne = (byte) specialOne;
    this.specialTwo = (byte) specialTwo;
    checkArgument(
        nonZeroValues.keySet().stream().allMatch(key -> key >= 0 && key < 18),
        "Value key out of range");

    if (specialOne != SPECIAL_NOT_SET) {
      checkArgument((specialOne > 8 && specialOne < 17), "Special one out of range");
    }

    if (specialTwo != SPECIAL_NOT_SET) {
      checkArgument((specialTwo > 0 && specialTwo < 9), "Special two out of range");
    }

    this.cells = new byte[18];

    for (Map.Entry<Integer, Integer> cellValue : nonZeroValues.entrySet()) {
      this.cells[cellValue.getKey()] = cellValue.getValue().byteValue();
    }

    this.isGameOver = checkGameOver();
    this.winner = checkWinner();
  }

  public byte getScoreOne() {
    return scoreOne;
  }

  public byte getScoreTwo() {
    return scoreTwo;
  }

  // Returns Player relevant move by cell index;
  private int moveByCell(int cell) {
    // 8 -> 0
    // 0 -> 8
    if (cell < 9) {
      return 8 - cell;
    }
    // 9 -> 0
    // 17 -> 8;
    return cell - 9;
  }

  private int nextCell(int cell) {
    if (cell == 0) {
      return 9;
    }

    if (cell < 9) {
      return cell - 1;
    }

    if (cell == 17) {
      return 8;
    }

    return cell + 1;
  }

  private boolean checkGameOver() {
    if (scoreOne > 81 || scoreTwo > 81) {
      return true;
    }

    if (scoreOne == 81 && scoreTwo == 81) {
      return true;
    }

    return IntStream.range(0, 9).noneMatch(move -> isMoveAllowed(move));
  }

  private Player isSpecial(int cell) {
    if (specialOne == cell) {
      return Player.ONE;
    }

    if (specialTwo == cell) {
      return Player.TWO;
    }

    return Player.NONE;
  }

  private boolean isReachable(int cell) {
    if (Player.ONE.equals(currentPlayer)) {
      return cell > 8;
    }
    return cell < 9;
  }

  public NinePebbles move(int move) {
    checkArgument(isMoveAllowed(move), "The move is not allowed!");

    int cell = boardCell(move);

    byte[] newCells = Arrays.copyOf(cells, cells.length);

    int hand = newCells[cell];

    newCells[cell] = 0;
    byte newScoreOne = scoreOne;
    byte newScoreTwo = scoreTwo;
    byte newSpecialOne = specialOne;
    byte newSpecialTwo = specialTwo;

    // Rule A
    int currentCell = hand == 1 ? nextCell(cell) : cell;

    while (hand > 0) {
      hand--;

      Player special = isSpecial(currentCell);

      // Rule C
      if (special == Player.NONE) {
        newCells[currentCell]++;
      } else {
        if (special == Player.ONE) {
          newScoreOne += 1;
        }
        if (special == Player.TWO) {
          newScoreTwo += 1;
        }
      }

      // Rule B
      if (hand == 0 && isReachable(currentCell)) {
        if (newCells[currentCell] % 2 == 0) {
          if (currentPlayer == Player.ONE) {
            newScoreOne += newCells[currentCell];
          }
          if (currentPlayer == Player.TWO) {
            newScoreTwo += newCells[currentCell];
          }
          newCells[currentCell] = 0;
        }

        // Rule D
        if (newCells[currentCell] == 3) {
          int possibleSpecialCellMove = moveByCell(currentCell);

          if (currentPlayer == Player.ONE
              // Does not have special cell yet;
              && specialOne == SPECIAL_NOT_SET
              // 9th (most right cell) can not be special;
              && possibleSpecialCellMove != 8
              // Can not mirror opponent's special
              && (specialTwo == SPECIAL_NOT_SET
                  || possibleSpecialCellMove != moveByCell(specialTwo))) {
            newScoreOne += 3;
            newCells[currentCell] = 0;
            newSpecialOne = (byte) currentCell;
          }

          if (currentPlayer == Player.TWO
              // Does not have special cell yet;
              && specialTwo == SPECIAL_NOT_SET
              // 9th (most right cell) can not be special;
              && possibleSpecialCellMove != 8
              // Can not mirror opponent's special
              && (specialOne == SPECIAL_NOT_SET
                  || possibleSpecialCellMove != moveByCell(specialOne))) {
            newScoreTwo += 3;
            newCells[currentCell] = 0;
            newSpecialTwo = (byte) currentCell;
          }
        }
      }

      currentCell = nextCell(currentCell);
    }

    return new NinePebbles(
        currentPlayer.opponent, newScoreOne, newScoreTwo, newSpecialOne, newSpecialTwo, newCells);
  }

  private Player checkWinner() {
    if (!isGameOver()) {
      return null;
    }

    if (scoreOne > 81) {
      return Player.ONE;
    }

    if (scoreTwo > 81) {
      return Player.TWO;
    }

    // Draw
    if (scoreOne == 81 && scoreTwo == 81) {
      return Player.NONE;
    }

    if (IntStream.range(0, 9).noneMatch(move -> isMoveAllowed(move))) {
      return currentPlayer.opponent;
    }

    throw new IllegalStateException("Unknown winner");
  }

  @Override
  public boolean isMoveAllowed(int move) {
    return cells[boardCell(move)] != 0;
  }

  private int boardCell(int move) {
    if (currentPlayer == Player.ONE) {
      return 8 - move;
    }

    return 9 + move;
  }

  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("-------------------------------\n");

    sb.append(scoreOne);
    sb.append(":");
    sb.append(scoreTwo);
    sb.append("\n");

    sb.append("|");
    for (int i = 0; i < 9; i++) {
      sb.append(
          Strings.padStart(
              isSpecial(i) != Player.NONE ? cells[i] + "*" : String.valueOf(cells[i]), 4, ' '));
      sb.append("|");
    }
    sb.append("\n");

    sb.append("|");
    for (int i = 9; i < 18; i++) {
      sb.append(
          Strings.padStart(
              isSpecial(i) != Player.NONE ? cells[i] + "*" : String.valueOf(cells[i]), 4, ' '));
      sb.append("|");
    }
    sb.append("\n");

    sb.append("Current Player: ");

    sb.append(currentPlayer);

    sb.append("\n");

    sb.append("Is GameOver: ");

    sb.append(isGameOver);

    sb.append("\n");

    sb.append("Winner: ");

    sb.append(winner);

    sb.append("\n");

    return sb.toString();
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  @Override
  public float[] encode() {
    float[] encoded = new float[NinePebbles.NUM_FEATURES];

    if (currentPlayer == Player.ONE) {
      // Player special, one hot
      if (specialOne != SPECIAL_NOT_SET) {
        encoded[moveByCell(specialOne)] = 1;
      }
      // Opponent special, one hot
      if (specialTwo != SPECIAL_NOT_SET) {
        encoded[9 + moveByCell(specialTwo)] = 1;
      }
      // Player side
      for (int i = 0; i < 9; i++) {
        encoded[18 + i] = (float) cells[8 - i] / 81.0f;
      }
      // Opponent side
      for (int i = 0; i < 9; i++) {
        encoded[27 + i] = (float) cells[9 + i] / 81.0f;
      }

      // Score
      encoded[36] = (float) scoreOne / 81f;
      encoded[37] = (float) scoreTwo / 81f;
    } else {
      // Player special, one hot
      if (specialTwo != SPECIAL_NOT_SET) {
        encoded[moveByCell(specialTwo)] = 1;
      }
      // Opponent special, one hot
      if (specialOne != SPECIAL_NOT_SET) {
        encoded[9 + moveByCell(specialOne)] = 1;
      }
      // Player side
      for (int i = 0; i < 9; i++) {
        encoded[18 + i] = (float) cells[9 + i] / 81.0f;
      }
      // Opponent side
      for (int i = 0; i < 9; i++) {
        encoded[27 + i] = (float) cells[8 - i] / 81.0f;
      }

      // Score
      encoded[36] = (float) scoreTwo / 81f;
      encoded[37] = (float) scoreOne / 81f;
    }
    NinePebblesMoveValuesEstimator estimator = new NinePebblesMoveValuesEstimator();
    float[] moveValues = estimator.estimateMoveValues(this);
    System.arraycopy(moveValues, 0, encoded, 38, moveValues.length);
    return encoded;
  }

  @Override
  public Player getPotentialWinner() {
    float playerOneAdjustedScore = scoreOne;
    float playerTwoAdjustedScore = scoreTwo;
    for (int move = 0; move < 9; move++) {
      playerOneAdjustedScore += cells[move] / 3f;
      playerTwoAdjustedScore += cells[9 + move] / 3f;
    }
    if (playerOneAdjustedScore > 81 && playerTwoAdjustedScore > 81) {
      return Player.NONE;
    } else if (playerOneAdjustedScore > 81) {
      return Player.ONE;
    } else if (playerTwoAdjustedScore > 81) {
      return Player.TWO;
    } else {
      return Player.NONE;
    }
  }
}
