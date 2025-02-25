package org.reinforce4j.core;

public enum Player {
  ONE,
  TWO,
  // To capture cases of draw (tie) game outcomes.
  NONE;

  static {
    ONE.opponent = TWO;
    TWO.opponent = ONE;
    NONE.opponent = NONE;
  }

  public Player opponent;
}
