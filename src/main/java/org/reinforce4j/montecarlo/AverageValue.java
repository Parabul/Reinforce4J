package org.reinforce4j.montecarlo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.reinforce4j.core.Player;

public class AverageValue {

  // The total observed value is stored w.r.t. the player ONE.
  private float playerOneValue = 0;
  // Number of samples observed.
  private int support = 0;

  public AverageValue() {}

  public AverageValue(float playerOneValue, int support) {
    this.playerOneValue = playerOneValue;
    this.support = support;
  }

  public float getValue(Player player) {
    if (support == 0) {
      return 0;
    }
    switch (player) {
      case ONE:
        return playerOneValue / support;
      case TWO:
        return -playerOneValue / support;
      default:
        throw new IllegalArgumentException(player.name());
    }
  }

  // Evaluation returns value in range [-1, 1], where 0 implies a tie.
  public AverageValue fromEvaluation(Player currentPlayer, float evaluatedValue) {
    this.support = 1;
    switch (currentPlayer) {
      case ONE:
        this.playerOneValue = evaluatedValue;
        break;
      case TWO:
        this.playerOneValue = -evaluatedValue;
        break;
      default:
        throw new IllegalArgumentException(currentPlayer.name());
    }

    return this;
  }

  public void copy(final AverageValue other) {
    this.playerOneValue = other.playerOneValue;
    this.support = other.support;
  }

  public AverageValue add(final AverageValue other) {
    this.playerOneValue += other.playerOneValue;
    this.support += other.support;
    return this;
  }

  public AverageValue addWinner(Player player) {
    support++;
    switch (player) {
      case ONE:
        playerOneValue++;
        break;
      case TWO:
        playerOneValue--;
    }

    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AverageValue that = (AverageValue) o;
    return Float.compare(playerOneValue, that.playerOneValue) == 0 && support == that.support;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(playerOneValue, support);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("playerOneValue", playerOneValue)
        .add("support", support)
        .toString();
  }

  public AverageValue reset() {
    playerOneValue = 0;
    support = 0;
    return this;
  }
}
