package org.reinforce4j.core;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class AverageValue {

  // The total observed value is stored w.r.t. the player ONE.
  private double totalObservedValue = 0;
  // Number of samples observed.
  private long support = 0;

  public AverageValue() {}

  public AverageValue(float value, Player player) {
    checkArgument(!player.equals(Player.NONE));
    this.support = 1;
    this.totalObservedValue = value * getMultiply(player);
  }

  public long getSupport() {
    return support;
  }

  private double getMultiply(Player player) {
    checkArgument(!player.equals(Player.NONE));
    if (player.equals(Player.TWO)) {
      return -1;
    } else {
      return 1;
    }
  }

  public void copy(final AverageValue other) {
    this.totalObservedValue = other.totalObservedValue;
    this.support = other.support;
  }

  public void set(Player player, double value) {
    totalObservedValue = value * getMultiply(player);
    support = 1;
  }

  public void add(final AverageValue other) {
    this.totalObservedValue += other.totalObservedValue;
    this.support += other.support;
  }

  public void add(Player player, double value) {
    totalObservedValue += value * getMultiply(player);
    support++;
  }

  public float getValue(Player player) {
    return support > 0 ? (float) (getMultiply(player) * totalObservedValue / support) : 0.0f;
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
}
