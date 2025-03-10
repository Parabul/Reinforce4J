package org.reinforce4j.playing;

import java.util.List;
import java.util.Random;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;

public class RandomStrategy<T extends GameState> implements Strategy<T> {

  private final Random random = new Random();
  private final int[] allowedMoves;
  private final GameService<T> gameService;

  public RandomStrategy(GameService<T> gameService) {
    this.allowedMoves = new int[gameService.numMoves()];
    this.gameService = gameService;
  }

  @Override
  public int nextMove(List<Integer> history) {
    T targetState = gameService.newInitialState();

    for (int i = 0; i < history.size(); i++) {
      if (!targetState.isMoveAllowed(history.get(i))) {
        throw new IllegalArgumentException(
            "Move not allowed: " + history.get(i) + " in " + targetState);
      }
      targetState.move(history.get(i));
    }

    int numAllowedMoves = 0;
    for (int i = 0; i < gameService.numMoves(); i++) {
      if (targetState.isMoveAllowed(i)) {
        allowedMoves[numAllowedMoves++] = i;
      }
    }

    int option = random.nextInt(numAllowedMoves);

    return allowedMoves[option];
  }
}
