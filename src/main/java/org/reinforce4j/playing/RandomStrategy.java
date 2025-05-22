package org.reinforce4j.playing;

import java.util.List;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;

public class RandomStrategy<T extends GameState>
    implements HistoryBasedStrategy<T>, StateBasedStrategy<T> {

  private final UniformRandomProvider random = RandomSource.XO_RO_SHI_RO_128_PP.create();
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

  @Override
  public int nextMove(T state) {
    int numAllowedMoves = 0;
    for (int i = 0; i < gameService.numMoves(); i++) {
      if (state.isMoveAllowed(i)) {
        allowedMoves[numAllowedMoves++] = i;
      }
    }

    int option = random.nextInt(numAllowedMoves);

    return allowedMoves[option];
  }
}
