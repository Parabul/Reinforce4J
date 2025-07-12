package org.reinforce4j.evaluation;

import java.util.Arrays;

// Constant evaluation zero value and uniform distribution for moves.
public class ZeroValueUniformEvaluator implements Evaluator {

  private final float policyValue;

  public ZeroValueUniformEvaluator(int numMoves) {
    this.policyValue = 1.0f / numMoves;
  }

  @Override
  public void evaluate(EvaluatedGameState... envelopes) {
    for (EvaluatedGameState envelope : envelopes) {
      if (envelope == null || envelope.state().isGameOver()) {
        continue;
      }
      envelope.evaluation().setValue(0);
      Arrays.fill(envelope.evaluation().getPolicy(), policyValue);
    }
  }
}
