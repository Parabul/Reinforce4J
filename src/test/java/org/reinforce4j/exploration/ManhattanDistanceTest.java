package org.reinforce4j.exploration;

import com.google.common.truth.Truth;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.reinforce4j.games.TicTacToe;

public class ManhattanDistanceTest {

  @Test
  public void matchesExpectedForRoot() {
    TicTacToe root = new TicTacToe();
    TicTacToe anotherRoot = new TicTacToe();

    ManhattanDistance distance = new ManhattanDistance();
    Truth.assertThat(distance.between(root, anotherRoot)).isEqualTo(0.0f);
    Truth.assertThat(distance.between(root, root)).isEqualTo(0.0f);
  }

  @Test
  public void matchesExpectedForRandomState() {
    TicTacToe root = new TicTacToe();

    TicTacToe someState = root.move(4).move(1).move(3).move(0);
    TicTacToe sameState = root.move(3).move(0).move(4).move(1);

    System.out.println(Arrays.toString(root.encode()));

    System.out.println(Arrays.toString(someState.encode()));

    System.out.println(Arrays.toString(sameState.encode()));

    ManhattanDistance distance = new ManhattanDistance();
    Truth.assertThat(distance.between(someState, someState)).isEqualTo(0.0f);
    Truth.assertThat(distance.between(someState, sameState)).isEqualTo(0.0f);
    Truth.assertThat(distance.between(root, someState)).isWithin(0.001f).of(4.0f);
    Truth.assertThat(distance.between(root, sameState)).isWithin(0.001f).of(4.0f);
    Truth.assertThat(distance.between(someState, root)).isWithin(0.001f).of(4.0f);
  }
}
