package org.reinforce4j.core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class PlayerTest {

    @Test
    public void hasOpponent() {
        assertThat(Player.NONE.opponent).isEqualTo(Player.NONE);
        assertThat(Player.ONE.opponent).isEqualTo(Player.TWO);
        assertThat(Player.TWO.opponent).isEqualTo(Player.ONE);
    }
}
