package org.reinforce4j.core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

public class OutcomesTest {

  @Test
  public void initWithZero() {
    Outcomes outcomes = new Outcomes();

    assertThat(outcomes.getTotalOutcomes()).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.ONE)).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.TWO)).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.NONE)).isEqualTo(0);
  }

  @Test
  public void shouldCountWinners() {
    Outcomes outcomes = new Outcomes();

    assertThat(outcomes.getTotalOutcomes()).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.ONE)).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.TWO)).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.NONE)).isEqualTo(0);

    outcomes.addWinner(Player.ONE);
    outcomes.addWinner(Player.ONE);
    outcomes.addWinner(Player.ONE);
    outcomes.addWinner(Player.ONE);
    outcomes.addWinner(Player.ONE);
    outcomes.addWinner(Player.TWO);
    outcomes.addWinner(Player.NONE);
    outcomes.addWinner(Player.NONE);
    outcomes.addWinner(Player.NONE);
    outcomes.addWinner(Player.NONE);

    assertThat(outcomes.getTotalOutcomes()).isEqualTo(10);
    assertThat(outcomes.winRateFor(Player.ONE)).isWithin(0.001f).of(0.7f);
    assertThat(outcomes.winRateFor(Player.TWO)).isWithin(0.001f).of(0.3f);
    assertThat(outcomes.winRateFor(Player.NONE)).isWithin(0.001f).of(0.4f);
  }

  @Test
  public void shouldCountWinnersOneGame() {
    Outcomes outcomes = new Outcomes();

    assertThat(outcomes.getTotalOutcomes()).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.ONE)).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.TWO)).isEqualTo(0);
    assertThat(outcomes.winRateFor(Player.NONE)).isEqualTo(0);

    outcomes.addWinner(Player.NONE);

    assertThat(outcomes.getTotalOutcomes()).isEqualTo(1);
    assertThat(outcomes.winRateFor(Player.ONE)).isWithin(0.001f).of(0.5f);
    assertThat(outcomes.winRateFor(Player.TWO)).isWithin(0.001f).of(0.5f);
    assertThat(outcomes.winRateFor(Player.NONE)).isWithin(0.001f).of(1.0f);
  }
}
