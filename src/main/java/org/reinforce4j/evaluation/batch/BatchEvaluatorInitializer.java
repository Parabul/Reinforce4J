package org.reinforce4j.evaluation.batch;

import com.google.inject.Inject;

public class BatchEvaluatorInitializer {

  private final BatchEvaluator batchEvaluator;

  @Inject
  public BatchEvaluatorInitializer(BatchEvaluator batchEvaluator) {
    this.batchEvaluator = batchEvaluator;
  }

  public BatchEvaluator getBatchEvaluator() {
    return batchEvaluator;
  }
}
