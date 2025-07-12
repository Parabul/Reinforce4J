package org.reinforce4j.montecarlo;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.*;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.games.Connect4Module;
import org.reinforce4j.montecarlo.strategies.ExpansionStrategy;
import org.reinforce4j.montecarlo.strategies.PredictiveUpperConfidenceBoundExpansionStrategy;
import org.reinforce4j.montecarlo.tasks.ExpandTaskFactory;
import org.tensorflow.example.Example;

public class MonteCarloTreeSearchModule extends AbstractModule {

  private final AtomicInteger completeExpansions = new AtomicInteger();
  private final MonteCarloTreeSearchSettings monteCarloTreeSearchSettings;

  public MonteCarloTreeSearchModule(MonteCarloTreeSearchSettings monteCarloTreeSearchSettings) {
    this.monteCarloTreeSearchSettings = monteCarloTreeSearchSettings;
  }

  @Provides
  public AtomicInteger getCompleteExpansions() {
    return completeExpansions;
  }

  @Override
  protected void configure() {
    install(new Connect4Module());
    bind(ReadWriteLock.class).to(ReentrantReadWriteLock.class).in(Singleton.class);
    bind(ExpansionStrategy.class).to(PredictiveUpperConfidenceBoundExpansionStrategy.class);
    bind(ListeningExecutorService.class)
        .toInstance(MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor()));

    install(new FactoryModuleBuilder().build(ExpandTaskFactory.class));
  }

  @Singleton
  @PruneMinVisits
  @Provides
  public int providePruneMinVisits() {
    return 10;
  }

  @Singleton
  @WriteMinVisits
  @Provides
  public int provideWriteMinVisits() {
    return 1000;
  }

  @Provides
  public Evaluator providesEvaluator() {
    return monteCarloTreeSearchSettings.evaluator().get();
  }

  @Provides
  @Singleton
  @ExpandedNodes
  public List<Example> provideExpandedNodes() {
    return Collections.synchronizedList(new ArrayList<>());
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface ExpandedNodes {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface PruneMinVisits {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @BindingAnnotation
  public @interface WriteMinVisits {}
}
