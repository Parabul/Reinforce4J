package org.reinforce4j.playing;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;

public class StateBasedPlayoutSimulator<T extends GameState> {

  private static final int MAX_MOVES = 500;

  private final EnumMap<Player, StateBasedStrategy<T>> strategies;
  private final GameService<T> service;

  public StateBasedPlayoutSimulator(
          StateBasedStrategy<T> playerOneStrategy, StateBasedStrategy<T> playerTwoStrategy, final GameService service) {
    this.strategies =
        new EnumMap<>(
            ImmutableMap.of(Player.ONE, playerOneStrategy, Player.TWO, playerTwoStrategy));
    this.service = service;
  }

  public Player playout() {
    T state = service.newInitialState();

    int moves = 0;
    while (!state.isGameOver()) {
      int move = strategies.get(state.getCurrentPlayer()).nextMove(state);
      if (!state.isMoveAllowed(move)) {
        throw new IllegalArgumentException("Move not allowed: " + move + " in " + state);
      }
      state.move(move);
      if (moves++> MAX_MOVES) {
        throw new IllegalStateException("Too many moves");
      }
    }

    return state.getWinner();
  }
}
