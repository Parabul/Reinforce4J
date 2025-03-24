package org.reinforce4j.montecarlo;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import org.reinforce4j.core.Player;

class BackPropagationStack {

  private final StateNode[] stateNodes;
  private final AverageValue[] averageValues;
  private final ArrayDeque<AverageValue> valuesPool;
  public int pointer;

  BackPropagationStack(int capacity) {
    this.stateNodes = new StateNode[capacity];
    this.averageValues = new AverageValue[capacity];
    this.pointer = -1;
    this.valuesPool = new ArrayDeque<>(capacity);
    for (int i = 0; i < capacity; i++) {
      valuesPool.add(new AverageValue());
    }
  }

  AverageValue getAverageValue() {
    return averageValues[pointer];
  }

  StateNode getStateNode() {
    return stateNodes[pointer];
  }

  void push(StateNode stateNode, AverageValue averageValue) {
    pointer++;
    stateNodes[pointer] = stateNode;
    averageValues[pointer] = averageValue;
  }

  void next() {
    Preconditions.checkState(hasNext());
    pointer--;
  }

  boolean hasNext() {
    return pointer >= 0;
  }

  public AverageValue acquire(Player player, double value) {
    try {
      return valuesPool.pop().set(player, value);
    } catch (NoSuchElementException e) {
      throw new RuntimeException("Failed to acquire, with size of : " + valuesPool.size(), e);
    }
  }

  public void release(final AverageValue node) {
    valuesPool.push(node);
  }
}
