package org.reinforce4j.montecarlo;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.reinforce4j.core.Player;

public class AverageValueTest {

  @Test
  public void zeroValueForBothPlayers() {
    AverageValue averageValue = new AverageValue();
    Truth.assertThat(averageValue.getValue(Player.ONE)).isZero();
    Truth.assertThat(averageValue.getValue(Player.TWO)).isZero();
  }

  @Test
  public void negativeValueForTheOpponent() {
    AverageValue averageValue = new AverageValue(1, 1);
    Truth.assertThat(averageValue.getValue(Player.ONE)).isEqualTo(1.0f);
    Truth.assertThat(averageValue.getValue(Player.TWO)).isEqualTo(-1.0f);
  }

  @Test
  public void addWinners() {
    AverageValue averageValue = new AverageValue(2, 2);
    averageValue.addWinner(Player.ONE);
    averageValue.addWinner(Player.TWO);
    averageValue.addWinner(Player.NONE);
    AverageValue averageValueExpected = new AverageValue(2, 5);
    Truth.assertThat(averageValue).isEqualTo(averageValueExpected);
    Truth.assertThat(averageValue.getValue(Player.ONE)).isEqualTo(0.4f);
    Truth.assertThat(averageValue.getValue(Player.TWO)).isEqualTo(-0.4f);
  }

  @Test
  public void addingOppositeValueReturnsZero() {
    AverageValue averageValue = new AverageValue(1, 1);
    averageValue.addWinner(Player.TWO);
    Truth.assertThat(averageValue.getValue(Player.ONE)).isEqualTo(0.0f);
    Truth.assertThat(averageValue.getValue(Player.TWO)).isEqualTo(-0.0f);
  }
}
