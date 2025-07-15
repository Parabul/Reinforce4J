package org.reinforce4j.evaluation.batch;

import com.google.common.truth.Truth;
import com.google.inject.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reinforce4j.evaluation.*;
import org.reinforce4j.games.Connect4;

public class BatchEvaluatorTest {

  private Injector injector;

  @BeforeEach
  void setUp() {
    injector =
        Guice.createInjector(
            new BatchEvaluatorModule(),
            new AbstractModule() {
              @Override
              protected void configure() {
                bind(Evaluator.class)
                    .annotatedWith(BatchEvaluatorModule.BatchEvaluatorDelegate.class)
                    .toInstance(new ZeroValueUniformEvaluator(7));
              }
            });
  }

  @Test
  void testBatchEvaluator() throws InterruptedException, ExecutionException, TimeoutException {
    BlockingQueue<BatchEvaluationRequest> queue =
        injector.getInstance(
            Key.get(new TypeLiteral<>() {}, BatchEvaluatorModule.BatchEvaluatorRequestQueue.class));
    injector.getInstance(BatchEvaluator.class); // Eager singleton

    EvaluatedGameStateEnvelope[] gameStates = {
      EvaluatedGameStateEnvelope.create(new Connect4(), new StateEvaluation(7)),
      EvaluatedGameStateEnvelope.create(new Connect4(), new StateEvaluation(7))
    };

    BatchEvaluationRequest request = new BatchEvaluationRequest(gameStates);

    queue.put(request);

    // Blocking. Wait for the future to complete
    request.getResult().get(5, TimeUnit.SECONDS);
    Truth.assertThat(gameStates[0].evaluation().getPolicy()[0]).isWithin(0.01f).of(1.0f / 7);
  }
}
