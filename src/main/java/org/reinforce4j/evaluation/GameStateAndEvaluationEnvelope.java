package org.reinforce4j.evaluation;

import com.google.auto.value.AutoValue;
import org.reinforce4j.core.GameState;

@AutoValue
public abstract class GameStateAndEvaluationEnvelope<T extends GameState>
    implements GameStateAndEvaluation<T> {

  public static <T extends GameState> GameStateAndEvaluationEnvelope<T> create(
      T state, StateEvaluation evaluation) {
    return new AutoValue_GameStateAndEvaluationEnvelope(state, evaluation);
  }

  @Override
  public abstract T state();

  @Override
  public abstract StateEvaluation evaluation();
}
