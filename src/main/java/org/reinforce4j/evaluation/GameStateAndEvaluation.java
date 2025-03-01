package org.reinforce4j.evaluation;

import org.reinforce4j.core.GameState;

public interface GameStateAndEvaluation<T extends GameState> {

  T state();

  StateEvaluation evaluation();
}
