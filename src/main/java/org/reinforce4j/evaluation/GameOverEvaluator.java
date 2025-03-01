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

      if (!envelope.state().isGameOver()) {
        continue;
      }

      if (envelope.state().getWinner().equals(envelope.state().getCurrentPlayer())) {
        envelope.evaluation().setValue(1);
      } else if (envelope.state().getWinner().equals(Player.NONE)) {
        envelope.evaluation().setValue(0);
      } else {
        envelope.evaluation().setValue(-1);
      }
    }
  }
}
