package org.reinforce4j.playing;

import java.util.List;
import org.reinforce4j.core.GameState;
import org.reinforce4j.montecarlo.MonteCarloTreeSearch;
import org.reinforce4j.montecarlo.StateNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonteCarloTreeSearchStrategy<T extends GameState> implements HistoryBasedStrategy<T> {

  private static final int MIN_VISITS = 1600;
  private static final Logger logger = LoggerFactory.getLogger(MonteCarloTreeSearchStrategy.class);

  private final MonteCarloTreeSearch<T> monteCarloTreeSearch;

  public MonteCarloTreeSearchStrategy(MonteCarloTreeSearch<T> monteCarloTreeSearch) {
    this.monteCarloTreeSearch = monteCarloTreeSearch;
  }

  @Override
  public int nextMove(List<Integer> history) {
    StateNode<T> targetNode = monteCarloTreeSearch.getRoot();
    monteCarloTreeSearch.initChildren(targetNode);

    for (int i = 0; i < history.size(); i++) {
      if (!targetNode.state().isMoveAllowed(history.get(i))) {
        throw new IllegalArgumentException(
            "Move not allowed: " + history.get(i) + " in " + targetNode.state());
      }
      targetNode = targetNode.getChildStates()[history.get(i)];
      monteCarloTreeSearch.initChildren(targetNode);
    }

    if (targetNode.getVisits() < MIN_VISITS) {
      long expansions = MIN_VISITS - targetNode.getVisits();
      for (int i = 0; i < expansions; i++) {
        if (monteCarloTreeSearch.getUsage() > 0.99) {
          int before = monteCarloTreeSearch.getSize();
          monteCarloTreeSearch.prune();
          int after = monteCarloTreeSearch.getSize();

          logger.info("Before pruning: {}, after {}", before, after);
        }
        monteCarloTreeSearch.expand(targetNode);
      }
    }

    float maxValue = -Float.MAX_VALUE;
    int argMax = -1;
    for (int i = 0; i < targetNode.getChildStates().length; i++) {
      StateNode<T> childNode = targetNode.getChildStates()[i];
      if (childNode == null) {
        continue;
      }
      float childValue = childNode.getOutcomes().valueFor(targetNode.state().getCurrentPlayer());
      if (childValue > maxValue) {
        argMax = i;
        maxValue = childValue;
      }
    }

    if (argMax == -1) {
      throw new IllegalStateException("Failed to find max value for " + targetNode);
    }

    return argMax;
  }
}
