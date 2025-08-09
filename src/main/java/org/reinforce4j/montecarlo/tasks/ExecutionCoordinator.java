package org.reinforce4j.montecarlo.tasks;

/** Controls executors and queues. */
public interface ExecutionCoordinator {

  /** Purges all queued tasks, does not stop execution. */
  void reset();

  /** Purges all queued tasks and stops all executors. */
  void shutdown();
}
