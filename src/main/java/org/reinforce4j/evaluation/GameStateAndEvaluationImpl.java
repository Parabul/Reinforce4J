package org.reinforce4j.evaluation;

import com.google.auto.value.AutoValue;
import org.reinforce4j.core.GameState;

@AutoValue
public abstract class GameStateAndEvaluationImpl<T extends GameState>
    implements GameStateAndEvaluation<T> {

  public static <T extends GameState> GameStateAndEvaluationImpl create(
      T state, StateEvaluation evaluation) {
    return new AutoValue_GameStateAndEvaluationImpl(state, evaluation);
  }

  @Override
  public abstract T state();

  @Override
  public abstract StateEvaluation evaluation();
}
