package org.reinforce4j.montecarlo;

import com.google.common.base.Preconditions;

class BackPropagationStack {

  private final StateNode[] stateNodes;
  private final AverageValue[] averageValues;
  public int pointer;

  BackPropagationStack(int capacity) {
    this.stateNodes = new StateNode[capacity];
    this.averageValues = new AverageValue[capacity];
    this.pointer = -1;
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
}
