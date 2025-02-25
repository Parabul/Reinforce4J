package org.reinforce4j.evaluation;

import org.reinforce4j.core.*;

// Only evaluates states that are GameOver otherwise uses delegate.
public class GameOverEvaluator<T extends GameState> implements Evaluator<T> {

  private final Evaluator<T> delegate;

  public GameOverEvaluator(Evaluator<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void evaluate(GameStateAndEvaluation<T>... envelopes) {
    delegate.evaluate(envelopes);

    for (GameStateAndEvaluation<T> envelope : envelopes) {
      if (envelope == null) {
        continue;
      }

      if (!envelope.getState().isGameOver()) {
        continue;
      }

      if (envelope.getState().getWinner().equals(envelope.getState().getCurrentPlayer())) {
        envelope.getEvaluation().setValue(1);
      } else if (envelope.getState().getWinner().equals(Player.NONE)) {
        envelope.getEvaluation().setValue(0);
      } else {
        envelope.getEvaluation().setValue(-1);
      }
    }
  }
}
