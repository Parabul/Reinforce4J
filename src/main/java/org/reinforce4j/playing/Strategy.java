package org.reinforce4j.playing;

import org.reinforce4j.core.GameState;

import java.util.List;

public interface Strategy<T extends GameState> {

  int nextMove(List<Integer> history);
}
