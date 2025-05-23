package org.reinforce4j.games;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.core.Player;

public class TicTacToeTest {

  @Test
  public void expectedInitialState() {
    TicTacToe root = TicTacToeService.INSTANCE.newInitialState();

    assertThat(root.isGameOver()).isFalse();
    assertThat(root.getCurrentPlayer()).isEqualTo(Player.ONE);
    assertThat(root.getWinner()).isNull();
    for (int i = 0; i < 9; i++) {
      assertThat(root.isMoveAllowed(i)).isTrue();
    }
  }

  @Test
  public void isNotAllowedSameMove() {
    TicTacToe root = TicTacToeService.INSTANCE.newInitialState();

    assertThat(root.isMoveAllowed(5)).isTrue();
    root.move(5);
    assertThat(root.isMoveAllowed(5)).isFalse();
  }

  @Test
  public void detectsGameOver() {
    TicTacToe root = TicTacToeService.INSTANCE.newInitialState();

    root.move(0);
    assertThat(root.isGameOver()).isFalse();
    root.move(3);
    assertThat(root.isGameOver()).isFalse();
    root.move(1);
    assertThat(root.isGameOver()).isFalse();
    root.move(4);
    assertThat(root.isGameOver()).isFalse();
    root.move(2);
    assertThat(root.isGameOver()).isTrue();
    assertThat(root.getWinner()).isEqualTo(Player.ONE);
  }

  @Test
  public void testEquals() {
    TicTacToe alpha = TicTacToeService.INSTANCE.newInitialState();
    TicTacToe beta = TicTacToeService.INSTANCE.newInitialState();
    assertThat(alpha).isEqualTo(beta);

    alpha.move(1);
    alpha.move(2);

    beta.move(3);
    beta.move(2);
    assertThat(alpha).isNotEqualTo(beta);

    alpha.move(3);
    beta.move(1);
    assertThat(alpha).isEqualTo(beta);
  }

  @Test
  public void copiesStates() {
    TicTacToe from = TicTacToeService.INSTANCE.newInitialState();
    from.move(1);
    from.move(5);

    TicTacToe to = TicTacToeService.INSTANCE.newInitialState();
    assertThat(to).isNotEqualTo(from);

    to.copy(from);
    assertThat(to).isEqualTo(from);

    from.move(3);
    assertThat(to).isNotEqualTo(from);
  }
}
