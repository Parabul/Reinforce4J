package org.reinforce4j.core;

public enum Player {
  ONE(1.0f),
  TWO(-1.0f),
  // To capture cases of draw (tie) game outcomes.
  NONE(0.0f);

  static {
    ONE.opponent = TWO;
    TWO.opponent = ONE;
    NONE.opponent = NONE;
  }

  private final float multiplier;
  public Player opponent;

  Player(float multiplier) {
    this.multiplier = multiplier;
  }

  public float getMultiplier() {
    return multiplier;
  }
}
