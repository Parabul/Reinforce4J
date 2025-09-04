package org.reinforce4j.exploration;

import java.util.*;
import org.apache.commons.math3.util.Pair;
import org.reinforce4j.core.GameState;

/**
 * Maintains most diverse set of game states (up to `capacity`) based on distance provided. Uses
 * Bloom filter to detect unseen states, ensure that the state has "good" hash function and the
 * desired capacity is relatively low (< 10K).
 */
public class MaxMinDiverseSet implements DiverseSet {

  private final int capacity;
  private final Distance distance;
  private final Set<GameState> set = new HashSet<>();

  private float minDistance = Float.MAX_VALUE;
  private Pair<GameState, GameState> leastDiverse = null;

  public MaxMinDiverseSet(int capacity, Distance distance) {
    this.capacity = capacity;
    this.distance = distance;
  }

  @Override
  public  boolean offer(GameState candidate) {
    if (set.contains(candidate)) {
      return false;
    }
    // set.add(candidate);

    if (set.size() < capacity) {
      set.add(candidate);
      return true;
    } else {
      if (leastDiverse == null) {
        recalculateDiversityMetrics();
      }

      if (distanceTo(candidate) > minDistance) {
        if (distance.between(candidate, leastDiverse.getFirst())
            < distance.between(candidate, leastDiverse.getSecond())) {
          set.remove(leastDiverse.getFirst());
        } else {
          set.remove(leastDiverse.getSecond());
        }
        set.add(candidate);
        recalculateDiversityMetrics();
        return true;
      }

      return false;
    }
  }

  private float distanceTo(GameState candidate) {
    float minDistance = Float.MAX_VALUE;
    for (GameState incumbent : set) {
      minDistance = Math.min(minDistance, distance.between(incumbent, candidate));
    }

    return minDistance;
  }

  private void recalculateDiversityMetrics() {
    minDistance = Float.MAX_VALUE;
    for (GameState left : set) {
      for (GameState right : set) {
        if (left.equals(right)) {
          continue;
        }
        float candidate = distance.between(left, right);
        if (candidate < minDistance) {
          minDistance = candidate;
          leastDiverse = Pair.create(left, right);
        }
      }
    }
  }

  @Override
  public Set<GameState> get() {
    return set;
  }

  public Pair<GameState, GameState> getLeastDiverse() {
    return leastDiverse;
  }
}
