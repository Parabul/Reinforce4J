package org.reinforce4j.montecarlo;

import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.reinforce4j.core.GameState;
import org.reinforce4j.montecarlo.tasks.ExpandTaskFactory;
import org.tensorflow.example.Example;

public class TreeSearch {

  private final ExpandTaskFactory expandTaskFactory;
  private final ExecutorService executor;
  private final Queue<Example> expandedNodes;

  @Inject
  public TreeSearch(
      ExpandTaskFactory expandTaskFactory,
      @MonteCarloTreeSearchModule.ExpansionWorkers ExecutorService executor,
      @MonteCarloTreeSearchModule.ExpandedNodes Queue<Example> expandedNodes) {
    this.expandTaskFactory = expandTaskFactory;
    this.executor = executor;
    this.expandedNodes = expandedNodes;
  }

  public Queue<Example> explore(final GameState root) throws InterruptedException {
    executor.submit(expandTaskFactory.create(root));
    executor.awaitTermination(150, TimeUnit.MINUTES);

    return expandedNodes;
  }
}
