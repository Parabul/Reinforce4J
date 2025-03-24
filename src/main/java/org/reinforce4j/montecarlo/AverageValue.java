package org.reinforce4j.montecarlo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.reinforce4j.core.Player;

public class AverageValue {

  // The total observed value is stored w.r.t. the player ONE.
  private double totalObservedValue = 0;
  // Number of samples observed.
  private long support = 0;

  public AverageValue() {}

  public AverageValue(float value, Player player) {
    this.support = 1;
    this.totalObservedValue = value * player.getMultiplier();
  }

  public long getSupport() {
    return support;
  }

  public void copy(final AverageValue other) {
    this.totalObservedValue = other.totalObservedValue;
    this.support = other.support;
  }

  public AverageValue set(Player player, double value) {
    totalObservedValue = value * player.getMultiplier();
    support = 1;
    return this;
  }

  public void add(final AverageValue other) {
    this.totalObservedValue += other.totalObservedValue;
    this.support += other.support;
  }

  public void add(Player player, double value) {
    totalObservedValue += value * player.getMultiplier();
    support++;
  }

  public float getValue(Player player) {
    return support > 0 ? (float) (player.getMultiplier() * totalObservedValue / support) : 0.0f;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AverageValue that = (AverageValue) o;
    return Double.compare(totalObservedValue, that.totalObservedValue) == 0
        && support == that.support;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(totalObservedValue, support);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("totalObservedValue", totalObservedValue)
        .add("support", support)
        .toString();
  }

  public void reset() {
    totalObservedValue = 0;
    support = 0;
  }
}
