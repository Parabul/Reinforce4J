package org.reinforce4j.playing;

import java.util.List;
import org.reinforce4j.core.GameState;

public interface Strategy<T extends GameState> {

  int nextMove(List<Integer> history);
}
