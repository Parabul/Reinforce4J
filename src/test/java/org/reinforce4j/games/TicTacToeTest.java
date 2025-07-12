package org.reinforce4j.games;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.core.Player;

public class TicTacToeTest {

  @Test
  public void expectedInitialState() {
    TicTacToe root = new TicTacToe();

    assertThat(root.isGameOver()).isFalse();
    assertThat(root.getCurrentPlayer()).isEqualTo(Player.ONE);
    assertThat(root.getWinner()).isNull();
    for (int i = 0; i < 9; i++) {
      assertThat(root.isMoveAllowed(i)).isTrue();
    }
  }

  @Test
  public void isNotAllowedSameMove() {
    TicTacToe root = new TicTacToe();

    assertThat(root.isMoveAllowed(5)).isTrue();
    root = root.move(5);
    assertThat(root.isMoveAllowed(5)).isFalse();
  }

  @Test
  public void detectsGameOver() {
    TicTacToe root = new TicTacToe();

    root = root.move(0);
    assertThat(root.isGameOver()).isFalse();
    root = root.move(3);
    assertThat(root.isGameOver()).isFalse();
    root = root.move(1);
    assertThat(root.isGameOver()).isFalse();
    root = root.move(4);
    assertThat(root.isGameOver()).isFalse();
    root = root.move(2);
    assertThat(root.isGameOver()).isTrue();
    assertThat(root.getWinner()).isEqualTo(Player.ONE);
  }

  @Test
  public void testEquals() {
    TicTacToe alpha = new TicTacToe();
    TicTacToe beta = new TicTacToe();
    assertThat(alpha).isEqualTo(beta);

    alpha = alpha.move(1);
    alpha = alpha.move(2);

    beta = beta.move(3);
    beta = beta.move(2);
    assertThat(alpha).isNotEqualTo(beta);

    alpha = alpha.move(3);
    beta = beta.move(1);
    assertThat(alpha).isEqualTo(beta);
  }
}
