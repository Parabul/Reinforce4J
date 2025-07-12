package org.reinforce4j.core;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/** Game outcomes */
public class Outcomes {

  private int first;
  private int second;
  private int ties;

  public Outcomes() {
    first = 0;
    second = 0;
    ties = 0;
  }

  public float winRateFor(Player player) {
    int total = getTotalOutcomes();
    if (total == 0L) {
      return 0.0f;
    }
    switch (player) {
      case ONE:
        return (1.0f * first + 0.5f * ties) / total;
      case TWO:
        return (1.0f * second + 0.5f * ties) / total;
      default:
        return 1.0f * ties / total;
    }
  }

  public void addWinner(Player winner) {
    switch (winner) {
      case ONE:
        first++;
        break;
      case TWO:
        second++;
        break;
      default:
        ties++;
    }
  }

  public int getTotalOutcomes() {
    return first + second + ties;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Outcomes outcomes = (Outcomes) o;
    return first == outcomes.first && second == outcomes.second && ties == outcomes.ties;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(first, second, ties);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("first", first)
        .add("second", second)
        .add("ties", ties)
        .toString();
  }
}
