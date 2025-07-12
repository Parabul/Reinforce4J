package org.reinforce4j.evaluation;

import org.reinforce4j.core.GameState;

public interface EvaluatedGameState {

  GameState state();

  StateEvaluation evaluation();
}
