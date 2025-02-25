package org.reinforce4j.evaluation;

import org.reinforce4j.core.GameState;

public interface Evaluator<T extends GameState> {

  void evaluate(GameStateAndEvaluation<T>... envelopes);
}
