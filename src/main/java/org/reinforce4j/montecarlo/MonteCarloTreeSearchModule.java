package org.reinforce4j.montecarlo;

import com.google.inject.*;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Queue;
import java.util.concurrent.*;
import org.reinforce4j.montecarlo.strategies.ExpansionStrategy;
import org.reinforce4j.montecarlo.strategies.PredictiveUpperConfidenceBoundExpansionStrategy;
import org.reinforce4j.montecarlo.tasks.DefaultExecutionCoordinator;
import org.reinforce4j.montecarlo.tasks.ExecutionCoordinator;
import org.reinforce4j.montecarlo.tasks.ExpandTaskFactory;
import org.tensorflow.example.Example;

public class MonteCarloTreeSearchModule extends AbstractModule {

  private static final int NUM_THREADS = 16;

  @Provides
  @Singleton
  @ExpandedNodes
  public Queue<Example> provideExpandedNodes() {
    return new ConcurrentLinkedQueue<>();
  }

  @Provides
  @Singleton
  @ExpansionTasksQueue
  public BlockingQueue<Runnable> provideExpansionTasksQueue() {
    return new LinkedBlockingQueue<>();
  }

  @Provides
  @Singleton
  @ExpansionWorkers
  public ExecutorService provideExpansionWorkers(
      @ExpansionTasksQueue final BlockingQueue<Runnable> expansionTasksQueue) {
    return new ThreadPoolExecutor(
        NUM_THREADS, NUM_THREADS, 0L, TimeUnit.MILLISECONDS, expansionTasksQueue);
  }

  @Override
  protected void configure() {
    bind(ExpansionStrategy.class)
        .to(PredictiveUpperConfidenceBoundExpansionStrategy.class)
        .in(Scopes.SINGLETON);

    bind(ExecutionCoordinator.class).to(DefaultExecutionCoordinator.class).in(Scopes.SINGLETON);

    install(new FactoryModuleBuilder().build(ExpandTaskFactory.class));
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface ExpandedNodes {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface ExpansionTasksQueue {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface ExpansionWorkers {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface DefaultEvaluator {}
}
