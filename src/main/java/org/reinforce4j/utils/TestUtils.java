package org.reinforce4j.utils;

import java.util.Random;

import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.TicTacToe;

public class TestUtils {
  public static TicTacToe getRandomTicTacToeState() {
    TicTacToe state = new TicTacToe();
    Random random = new Random();
    for (int i = 0; i < random.nextInt(6); i++) {
      int move = random.nextInt(9);
      while (!state.isMoveAllowed(move)) {
        move = random.nextInt(9);
      }
      state = state.move(move);
    }
    return state;
  }

  public static Connect4 getRandomConnect4State() {
    Connect4 state = new Connect4();
    Random random = new Random();
    for (int i = 0; i < random.nextInt(8); i++) {
      int move = random.nextInt(7);
      while (!state.isMoveAllowed(move)) {
        move = random.nextInt(7);
      }
      state = state.move(move);
    }
    return state;
  }
}
