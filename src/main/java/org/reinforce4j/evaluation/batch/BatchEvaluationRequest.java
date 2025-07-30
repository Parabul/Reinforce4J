package org.reinforce4j.evaluation.batch;

import java.util.concurrent.CompletableFuture;
import org.reinforce4j.evaluation.EvaluatedGameState;

public class BatchEvaluationRequest {

  private final EvaluatedGameState[] evaluatedGameStates;
  private final CompletableFuture<Void> result;

  public BatchEvaluationRequest(EvaluatedGameState[] evaluatedGameStates) {
    this.evaluatedGameStates = evaluatedGameStates;
    this.result = new CompletableFuture<>();
  }

  public CompletableFuture<Void> getResult() {
    return result;
  }

  public EvaluatedGameState[] getEvaluatedGameStates() {
    return evaluatedGameStates;
  }
}
