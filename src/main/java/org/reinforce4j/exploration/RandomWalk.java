package org.reinforce4j.exploration;

import java.util.*;
import org.reinforce4j.core.GameState;

public class RandomWalk implements RandomStateGenerator {

  private final Random random = new Random();
  private final GameState root;
  private final int numMoves;

  public RandomWalk(GameState root, int numMoves) {
    this.root = root;
    this.numMoves = numMoves;
  }

  @Override
  public Set<GameState> get() {
    Set<GameState> path = new HashSet<>();
    GameState state = root;
    while (!state.isGameOver()) {
      List<Integer> possibleMoves = new ArrayList<>(numMoves);
      for (int i = 0; i < numMoves; i++) {
        if (state.isMoveAllowed(i)) {
          possibleMoves.add(i);
        }
      }
      state = state.move(possibleMoves.get(random.nextInt(possibleMoves.size())));
      if (!state.isGameOver()) {
        path.add(state);
      }
    }

    return path;
  }
}
