package org.reinforce4j.evaluation;

import com.google.auto.value.AutoValue;
import org.reinforce4j.core.GameState;

@AutoValue
public abstract class EvaluatedGameStateEnvelope implements EvaluatedGameState {

  public static EvaluatedGameStateEnvelope create(GameState state, StateEvaluation evaluation) {
    return new AutoValue_EvaluatedGameStateEnvelope(state, evaluation);
  }

  @Override
  public abstract GameState state();

  @Override
  public abstract StateEvaluation evaluation();
}
