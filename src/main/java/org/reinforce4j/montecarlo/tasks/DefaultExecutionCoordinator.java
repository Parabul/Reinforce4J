package org.reinforce4j.montecarlo.tasks;

import com.google.inject.Inject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExecutionCoordinator implements ExecutionCoordinator {

  private static final Logger logger = LoggerFactory.getLogger(DefaultExecutionCoordinator.class);

  private final ExecutorService workersExecutor;
  private final BlockingQueue<Runnable> expansionTasksQueue;

  @Inject
  DefaultExecutionCoordinator(
      @MonteCarloTreeSearchModule.ExpansionTasksQueue
          final BlockingQueue<Runnable> expansionTasksQueue,
      @MonteCarloTreeSearchModule.ExpansionWorkers final ExecutorService workersExecutor) {
    this.expansionTasksQueue = expansionTasksQueue;
    this.workersExecutor = workersExecutor;
  }

  @Override
  public void reset() {
    expansionTasksQueue.clear();
  }

  public void shutdown() {
    logger.info("Shutting down");
    workersExecutor.shutdownNow();
  }
}
