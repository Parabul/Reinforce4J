package org.reinforce4j.evaluation.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.reinforce4j.evaluation.EvaluatedGameState;
import org.reinforce4j.evaluation.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Batching Evaluator that drains the evaluation requests queue, combines the envelopes and invokes
 * delegate evaluator.
 */
public class BatchEvaluator implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(BatchEvaluator.class);
  private static final int BATCH_SIZE = 1024;

  private final BlockingQueue<BatchEvaluationRequest> queue;
  private final Evaluator evaluator;

  public BatchEvaluator(BlockingQueue<BatchEvaluationRequest> queue, Evaluator evaluator) {
    this.queue = queue;
    this.evaluator = evaluator;
  }

  @Override
  public void run() {
    logger.info("Batch evaluator started");

    while (!Thread.currentThread().isInterrupted()) {
      try {

        List<BatchEvaluationRequest> batch = new ArrayList<>();
        queue.drainTo(batch, BATCH_SIZE);

        if (!batch.isEmpty()) {
          if (batch.size() == BATCH_SIZE) {
            logger.info("Hitting the batch limit!");
          }
          int n = 0;
          for (BatchEvaluationRequest request : batch) {
            n += request.getEvaluatedGameStates().length;
          }

          EvaluatedGameState[] evaluatedGameStates = new EvaluatedGameState[n];

          for (int i = 0; i < batch.size(); i++) {
            System.arraycopy(
                batch.get(i).getEvaluatedGameStates(),
                0,
                evaluatedGameStates,
                i,
                batch.get(i).getEvaluatedGameStates().length);
          }

          evaluator.evaluate(evaluatedGameStates);
          for (BatchEvaluationRequest request : batch) {
            request.getResult().complete(null);
          }
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        Thread.currentThread().interrupt();
        System.out.println("Batch processor was interrupted.");
      }
    }
  }
}
