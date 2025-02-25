package org.reinforce4j.evaluation;

import org.reinforce4j.core.GameState;

// Constant evaluation zero value and uniform distribution for moves.
public class ZeroValueUniformEvaluator<T extends GameState> implements Evaluator<T> {

  private final float policyValue;
  private final int numMoves;

  public ZeroValueUniformEvaluator(int numMoves) {
    this.numMoves = numMoves;
    this.policyValue = 1.0f / numMoves;
  }

  @Override
  public void evaluate(GameStateAndEvaluation<T>... envelopes) {
    for (GameStateAndEvaluation<T> envelope : envelopes) {
      if (envelope == null || envelope.getState().isGameOver()) {
        continue;
      }
      envelope.getEvaluation().setValue(0);

      for (int i = 0; i < numMoves; i++) {
        envelope.getEvaluation().getPolicy()[i] = policyValue;
      }
    }
  }
}
