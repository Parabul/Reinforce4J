package org.reinforce4j.montecarlo;

import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import java.time.Duration;
import java.util.List;

import org.reinforce4j.montecarlo.tasks.ExpandTaskFactory;
import org.tensorflow.example.Example;

public class TreeSearch {

  private final ExpandTaskFactory expandTaskFactory;
  private final ListeningExecutorService executor;
  private final List<Example> expandedNodes;

  @Inject
  public TreeSearch(
      ExpandTaskFactory expandTaskFactory,
      ListeningExecutorService executor,
      @MonteCarloTreeSearchModule.ExpandedNodes List<Example> expandedNodes) {
    this.expandTaskFactory = expandTaskFactory;
    this.executor = executor;
    this.expandedNodes = expandedNodes;
  }

  public List<Example> explore(final TreeNode root) throws InterruptedException {
    executor.submit(expandTaskFactory.create(root, 2000));
    executor.awaitTermination(Duration.ofMinutes(150));

    return expandedNodes;
  }
}
