package org.reinforce4j.playing;

import org.reinforce4j.core.GameState;

public interface StateBasedStrategy<T extends GameState> {

  int nextMove(T state);
}
