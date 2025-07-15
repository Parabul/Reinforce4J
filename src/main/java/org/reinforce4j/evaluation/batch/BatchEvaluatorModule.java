package org.reinforce4j.evaluation.batch;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.reinforce4j.evaluation.Evaluator;

public class BatchEvaluatorModule extends AbstractModule {
  //    final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
  //    final ExecutorService workerPool = Executors.newFixedThreadPool(NUM_THREADS);
  //    final ExecutorService batchExecutor = Executors.newSingleThreadExecutor();

  @Override
  protected void configure() {
    bind(BatchEvaluatorInitializer.class).asEagerSingleton();
  }

  @Provides
  @Singleton
  @BatchEvaluatorRequestQueue
  public BlockingQueue<BatchEvaluationRequest> provideBatchEvaluationRequestQueue() {
    return new ArrayBlockingQueue<>(10000);
  }

  @Provides
  @Singleton
  @BatchEvaluatorExecutor
  public ExecutorService provideExecutorService() {
    return Executors.newSingleThreadExecutor();
  }

  @Singleton
  @Provides
  BatchEvaluator provideBatchEvaluator(
      @BatchEvaluatorModule.BatchEvaluatorRequestQueue BlockingQueue<BatchEvaluationRequest> queue,
      @BatchEvaluatorModule.BatchEvaluatorDelegate Evaluator evaluator,
      @BatchEvaluatorExecutor ExecutorService executor) {
    BatchEvaluator batchEvaluator = new BatchEvaluator(queue, evaluator);
    executor.submit(batchEvaluator);
    return batchEvaluator;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface BatchEvaluatorExecutor {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface BatchEvaluatorRequestQueue {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface BatchEvaluatorDelegate {}
}
