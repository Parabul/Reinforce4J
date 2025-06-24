package org.reinforce4j.core;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/** Game outcomes */
public class Outcomes {

  private int firstPlayerWins;
  private int secondPlayerWins;
  private int ties;

  public Outcomes() {
    firstPlayerWins = 0;
    secondPlayerWins = 0;
    ties = 0;
  }

  public void copy(Outcomes other) {
    firstPlayerWins = other.firstPlayerWins;
    secondPlayerWins = other.secondPlayerWins;
    ties = other.ties;
  }

  public float valueFor(Player player) {
    int total = getTotalOutcomes();
    if (total == 0) {
      return 0.0f;
    }
    if (Player.ONE.equals(player)) {
      return 1.0f * (firstPlayerWins - secondPlayerWins) / total;
    } else {
      return 1.0f * (secondPlayerWins - firstPlayerWins) / total;
    }
  }

  public float winRateFor(Player player) {
    int total = getTotalOutcomes();
    if (total == 0L) {
      return 0.0f;
    }
    switch (player) {
      case ONE:
        return (1.0f * firstPlayerWins + 0.5f * ties) / total;
      case TWO:
        return (1.0f * secondPlayerWins + 0.5f * ties) / total;
      default:
        return 1.0f * ties / total;
    }
  }

  public void addWinner(Player winner) {
    switch (winner) {
      case ONE:
        firstPlayerWins++;
        break;
      case TWO:
        secondPlayerWins++;
        break;
      default:
        ties++;
    }
  }

  public int getTotalOutcomes() {
    return firstPlayerWins + secondPlayerWins + ties;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Outcomes outcomes = (Outcomes) o;
    return firstPlayerWins == outcomes.firstPlayerWins
        && secondPlayerWins == outcomes.secondPlayerWins
        && ties == outcomes.ties;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(firstPlayerWins, secondPlayerWins, ties);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("firstPlayerWins", firstPlayerWins)
        .add("secondPlayerWins", secondPlayerWins)
        .add("ties", ties)
        .toString();
  }

  public void reset() {
    firstPlayerWins = 0;
    secondPlayerWins = 0;
    ties = 0;
  }
}
