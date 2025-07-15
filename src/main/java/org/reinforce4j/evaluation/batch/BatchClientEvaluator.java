package org.reinforce4j.evaluation.batch;

import com.google.inject.Inject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import org.reinforce4j.evaluation.EvaluatedGameState;
import org.reinforce4j.evaluation.Evaluator;

// Client of the BatchEvaluator, adds evaluation request to the queue and waits for its completion.
public class BatchClientEvaluator implements Evaluator {

  private final BlockingQueue<BatchEvaluationRequest> queue;

  @Inject
  BatchClientEvaluator(
      @BatchEvaluatorModule.BatchEvaluatorRequestQueue
          BlockingQueue<BatchEvaluationRequest> queue) {
    this.queue = queue;
  }

  @Override
  public void evaluate(EvaluatedGameState... envelopes) {
    BatchEvaluationRequest request = new BatchEvaluationRequest(envelopes);
    queue.add(request);

    try {
      request.getResult().get();
    } catch (InterruptedException | ExecutionException e) {
      //throw new RuntimeException(e);
    }
  }
}
