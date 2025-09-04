package org.reinforce4j.exploration;

import java.util.Set;
import org.reinforce4j.core.GameState;

public interface RandomStateGenerator {

  Set<GameState> get();
}
