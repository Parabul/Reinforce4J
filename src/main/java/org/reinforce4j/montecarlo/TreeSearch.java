package org.reinforce4j.montecarlo;

import com.google.inject.Inject;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import org.reinforce4j.constants.NumberOfNodesToExpand;
import org.reinforce4j.core.GameState;
import org.reinforce4j.montecarlo.tasks.ExecutionCoordinator;
import org.reinforce4j.montecarlo.tasks.ExpandTaskFactory;
import org.tensorflow.example.Example;

public class TreeSearch {

  private final ExpandTaskFactory expandTaskFactory;
  private final ExecutorService executor;
  private final Queue<Example> expandedNodes;
  private final int numberOfNodesToExpand;
  private final ExecutionCoordinator executionCoordinator;

  @Inject
  public TreeSearch(
      ExecutionCoordinator executionCoordinator,
      NumberOfNodesToExpand numberOfNodesToExpand,
      ExpandTaskFactory expandTaskFactory,
      @MonteCarloTreeSearchModule.ExpansionWorkers ExecutorService executor,
      @MonteCarloTreeSearchModule.ExpandedNodes Queue<Example> expandedNodes) {
    this.executionCoordinator = executionCoordinator;
    this.numberOfNodesToExpand = numberOfNodesToExpand.value();
    this.expandTaskFactory = expandTaskFactory;
    this.executor = executor;
    this.expandedNodes = expandedNodes;
  }

  public Queue<Example> explore(final GameState root) throws InterruptedException {
    CountDownLatch expansionsRemaining = new CountDownLatch(numberOfNodesToExpand);
    executor.submit(expandTaskFactory.create(root, expansionsRemaining));
    expansionsRemaining.await();
    executionCoordinator.reset();

    return expandedNodes;
  }
}
