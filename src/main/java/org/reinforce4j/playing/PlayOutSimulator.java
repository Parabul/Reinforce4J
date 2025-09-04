package org.reinforce4j.playing;

import com.google.common.collect.ImmutableMap;
import java.util.EnumMap;
import java.util.function.Supplier;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;

public class PlayOutSimulator {

  private static final int MAX_MOVES = 5000;

  private final EnumMap<Player, StateBasedStrategy> strategies;
  private final Supplier<GameState> initialStateSupplier;

  public PlayOutSimulator(
      StateBasedStrategy playerOneStrategy,
      StateBasedStrategy playerTwoStrategy,
      Supplier<GameState> initialStateSupplier) {
    this.strategies =
        new EnumMap<>(
            ImmutableMap.of(Player.ONE, playerOneStrategy, Player.TWO, playerTwoStrategy));
    this.initialStateSupplier = initialStateSupplier;
  }

  public Player playOut() {
    GameState state = initialStateSupplier.get();

    int moves = 0;
    while (!state.isGameOver()) {
      int move = strategies.get(state.getCurrentPlayer()).nextMove(state);
      if (!state.isMoveAllowed(move)) {
        throw new IllegalArgumentException("Move not allowed: " + move + " in " + state);
      }
      state = state.move(move);
      if (moves++ > MAX_MOVES) {
        return Player.NONE;
        //        throw new IllegalStateException("Too many moves");
      }
    }

    return state.getWinner();
  }
}
