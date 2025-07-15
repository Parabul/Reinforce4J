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
import org.reinforce4j.evaluation.batch.BatchEvaluatorModule;
import org.reinforce4j.montecarlo.strategies.ExpansionStrategy;
import org.reinforce4j.montecarlo.strategies.PredictiveUpperConfidenceBoundExpansionStrategy;
import org.reinforce4j.montecarlo.tasks.ExpandTaskFactory;
import org.tensorflow.example.Example;

public class MonteCarloTreeSearchModule extends AbstractModule {

  @Provides
  @Singleton
  public AtomicInteger getCompleteExpansions() {
    return new AtomicInteger();
  }

  @Override
  protected void configure() {
    bind(ExpansionStrategy.class).to(PredictiveUpperConfidenceBoundExpansionStrategy.class);
    bind(ListeningExecutorService.class)
        .toInstance(MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(50)));

    install(new FactoryModuleBuilder().build(ExpandTaskFactory.class));
    install(new BatchEvaluatorModule());
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
}
