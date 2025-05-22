package org.reinforce4j.playing;

import java.util.List;
import org.reinforce4j.core.GameState;

public interface StateBasedStrategy<T extends GameState> {

  int nextMove(T state);
}
