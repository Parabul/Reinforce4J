package org.reinforce4j.core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

public class OutcomesTest {

  @Test
  public void initWithZero() {
    Outcomes outcomes = new Outcomes();

    assertThat(outcomes.getTotalOutcomes()).isEqualTo(0);
    assertThat(outcomes.valueFor(Player.ONE)).isEqualTo(0);
    assertThat(outcomes.valueFor(Player.TWO)).isEqualTo(0);
    assertThat(outcomes.valueFor(Player.NONE)).isEqualTo(0);
  }

//  @Test
//  public void shouldMerge() {
//    Outcomes outcomes1 = new Outcomes();
//    Outcomes outcomes2 = new Outcomes();
//
//
//    outcomes1.addWinner(Player.ONE);
//    outcomes1.addWinner(Player.TWO);
//    outcomes1.addWinner(Player.NONE);
//
//    outcomes2.addWinner(Player.ONE);
//    outcomes2.addWinner(Player.TWO);
//    outcomes2.addWinner(Player.NONE);
//
//    outcomes2.merge(outcomes1);
//
//    assertThat(outcomes2.getTotalOutcomes()).isEqualTo(6);
//    assertThat(outcomes2.winsPerPlayer(Player.ONE)).isEqualTo(2);
//    assertThat(outcomes2.winsPerPlayer(Player.TWO)).isEqualTo(2);
//    assertThat(outcomes2.winsPerPlayer(Player.NONE)).isEqualTo(2);
//
//    outcomes1.merge(outcomes1);
//    assertThat(outcomes1).isEqualTo(outcomes2);
//  }
//
//  @Test
//  public void shouldCountWinners() {
//    Outcomes outcomes = new Outcomes();
//
//    assertThat(outcomes.getTotalOutcomes()).isEqualTo(0);
//    assertThat(outcomes.winsPerPlayer(Player.ONE)).isEqualTo(0);
//    assertThat(outcomes.winsPerPlayer(Player.TWO)).isEqualTo(0);
//    assertThat(outcomes.winsPerPlayer(Player.NONE)).isEqualTo(0);
//
//    assertThat(outcomes.valueFor(Player.ONE)).isEqualTo(0);
//    assertThat(outcomes.valueFor(Player.TWO)).isEqualTo(0);
//    assertThat(outcomes.valueFor(Player.NONE)).isEqualTo(0);
//
//    outcomes.addWinner(Player.ONE);
//    outcomes.addWinner(Player.ONE);
//    outcomes.addWinner(Player.ONE);
//    outcomes.addWinner(Player.ONE);
//    outcomes.addWinner(Player.ONE);
//    outcomes.addWinner(Player.TWO);
//    outcomes.addWinner(Player.NONE);
//    outcomes.addWinner(Player.NONE);
//    outcomes.addWinner(Player.NONE);
//    outcomes.addWinner(Player.NONE);
//
//    assertThat(outcomes.getTotalOutcomes()).isEqualTo(10);
//    assertThat(outcomes.winsPerPlayer(Player.ONE)).isEqualTo(5);
//    assertThat(outcomes.winsPerPlayer(Player.TWO)).isEqualTo(1);
//    assertThat(outcomes.winsPerPlayer(Player.NONE)).isEqualTo(4);
//
//    assertThat(outcomes.valueFor(Player.ONE)).isEqualTo(0.7f);
//    assertThat(outcomes.valueFor(Player.TWO)).isEqualTo(0.3f);
//  }
}
