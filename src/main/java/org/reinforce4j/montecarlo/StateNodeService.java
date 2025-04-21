package org.reinforce4j.montecarlo;

import java.util.*;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.utils.TensorFlowUtils;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;
import org.tensorflow.example.Example;

@SuppressWarnings("unchecked")
public class StateNodeService<T extends GameState> {

  private final Deque<StateNode<T>> pool;
  private final int capacity;

  private final GameService<T> gameService;
  private final Evaluator<T> evaluator;
  private final int minVisits;

  public StateNodeService(
      GameService<T> gameService, Evaluator<T> evaluator, int capacity, int minVisits) {
    this.capacity = capacity;
    this.minVisits = minVisits;
    this.pool = new ArrayDeque<>(capacity);
    this.gameService = gameService;
    this.evaluator = evaluator;
  }
}
