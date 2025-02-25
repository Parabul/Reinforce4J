package org.reinforce4j.core;

import com.google.common.truth.Truth;
import org.junit.Test;

public class AverageValueTest {

  @Test
  public void zeroValueForBothPlayers() {
    AverageValue averageValue = new AverageValue();
    Truth.assertThat(averageValue.getValue(Player.ONE)).isZero();
    Truth.assertThat(averageValue.getValue(Player.TWO)).isZero();
  }

  @Test
  public void negativeValueForTheOpponent() {
    AverageValue averageValue = new AverageValue(1, Player.ONE);
    Truth.assertThat(averageValue.getValue(Player.ONE)).isEqualTo(1.0f);
    Truth.assertThat(averageValue.getValue(Player.TWO)).isEqualTo(-1.0f);
  }

  @Test
  public void addingZeroValueUpdatesAverage() {
    AverageValue averageValue = new AverageValue(1, Player.ONE);
    averageValue.add(new AverageValue(0, Player.ONE));
    Truth.assertThat(averageValue.getValue(Player.ONE)).isEqualTo(0.5f);
    Truth.assertThat(averageValue.getValue(Player.TWO)).isEqualTo(-0.5f);
  }

  @Test
  public void addingOppositeValueReturnsZero() {
    AverageValue averageValue = new AverageValue(1, Player.ONE);
    averageValue.add(new AverageValue(1, Player.TWO));
    Truth.assertThat(averageValue.getValue(Player.ONE)).isEqualTo(0.0f);
    Truth.assertThat(averageValue.getValue(Player.TWO)).isEqualTo(-0.0f);
  }
}
