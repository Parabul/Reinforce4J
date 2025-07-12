package org.reinforce4j.playing;

import org.reinforce4j.core.GameState;

public interface StateBasedStrategy {

  int nextMove(GameState state);
}
