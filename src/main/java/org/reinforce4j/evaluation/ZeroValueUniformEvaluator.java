package org.reinforce4j.evaluation;

import java.util.Arrays;
import org.reinforce4j.core.GameState;

// Constant evaluation zero value and uniform distribution for moves.
public class ZeroValueUniformEvaluator<T extends GameState> implements Evaluator<T> {

  private final float policyValue;

  public ZeroValueUniformEvaluator(int numMoves) {
    this.policyValue = 1.0f / numMoves;
  }

  @Override
  public void evaluate(GameStateAndEvaluation<T>... envelopes) {
    for (GameStateAndEvaluation<T> envelope : envelopes) {
      if (envelope == null || envelope.state().isGameOver()) {
        continue;
      }
      envelope.evaluation().setValue(0);
      Arrays.fill(envelope.evaluation().getPolicy(), policyValue);
    }
  }
}
