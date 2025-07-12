package org.reinforce4j.evaluation;

import org.reinforce4j.core.*;

// Only evaluates states that are GameOver otherwise uses delegate.
public class GameOverEvaluator implements Evaluator {

  private final Evaluator delegate;

  public GameOverEvaluator(Evaluator delegate) {
    this.delegate = delegate;
  }

  @Override
  public void evaluate(EvaluatedGameState... envelopes) {
    delegate.evaluate(envelopes);

    for (EvaluatedGameState envelope : envelopes) {
      if (envelope == null) {
        continue;
      }

      GameState state = envelope.state();

      if (!state.isGameOver()) {
        continue;
      }

      if (state.getWinner().equals(state.getCurrentPlayer())) {
        envelope.evaluation().setValue(1);
      } else if (state.getWinner().equals(Player.NONE)) {
        envelope.evaluation().setValue(0);
      } else {
        envelope.evaluation().setValue(-1);
      }
    }
  }
}
