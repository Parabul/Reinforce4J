package org.reinforce4j.evaluation;

import java.util.ArrayList;
import java.util.List;
import org.reinforce4j.core.*;

// If the given state deterministic, if either is true:
//   - Is game over;
//   - Has a winning move for the current player;
//   - All moves result in ties/looses.
public class ExtendedGameOverEvaluator implements Evaluator {

  private final Evaluator delegate;

  public ExtendedGameOverEvaluator(Evaluator delegate) {
    this.delegate = delegate;
  }

  @Override
  public void evaluate(EvaluatedGameState... envelopes) {
    List<EvaluatedGameState> toDelegate = new ArrayList<>();

    for (EvaluatedGameState envelope : envelopes) {
      if (envelope == null) {
        continue;
      }

      GameState state = envelope.state();

      if (state.isGameOver()) {
        if (state.getWinner().equals(state.getCurrentPlayer())) {
          envelope.evaluation().setValue(1);
        } else if (state.getWinner().equals(Player.NONE)) {
          envelope.evaluation().setValue(0);
        } else {
          envelope.evaluation().setValue(-1);
        }
        continue;
      }

      int numWinningMoves = 0;
      int numLoosingMoves = 0;
      int numTieMoves = 0;
      for (int i = 0; i < envelope.evaluation().getNumberOfMoves(); i++) {
        if (!state.isMoveAllowed(i)) {
          continue;
        }
        GameState child = state.move(i);

        if (!child.isGameOver()) {
          continue;
        }

        if (state.getCurrentPlayer().equals(child.getWinner())) {
          envelope.evaluation().setValue(1);
          envelope.evaluation().getPolicy()[i] = 1;
          numWinningMoves++;
        } else if (state.getCurrentPlayer().equals(Player.NONE)) {
          numTieMoves++;
        } else {
          numLoosingMoves++;
        }
      }

      if (numWinningMoves > 0) {
        for (int i = 0; i < envelope.evaluation().getNumberOfMoves(); i++) {
          envelope.evaluation().getPolicy()[i] =
              envelope.evaluation().getPolicy()[i] / numWinningMoves;
        }

        continue;
      }

      if (numLoosingMoves == envelope.evaluation().getNumberOfMoves()) {
        envelope.evaluation().setValue(-1);
        for (int i = 0; i < envelope.evaluation().getNumberOfMoves(); i++) {
          envelope.evaluation().getPolicy()[i] = 1 / numLoosingMoves;
        }

        continue;
      }

      if (numTieMoves == envelope.evaluation().getNumberOfMoves()) {
        envelope.evaluation().setValue(0);
        for (int i = 0; i < envelope.evaluation().getNumberOfMoves(); i++) {
          envelope.evaluation().getPolicy()[i] = 1 / numTieMoves;
        }

        continue;
      }

      toDelegate.add(envelope);
    }

    delegate.evaluate(toDelegate.toArray(new EvaluatedGameState[toDelegate.size()]));
  }
}
