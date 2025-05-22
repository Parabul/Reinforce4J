package org.reinforce4j.playing;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Player;

public class HistoryBasedPlayoutSimulator<T extends GameState> {

  private static final int MAX_MOVES = 500;

  private final EnumMap<Player, HistoryBasedStrategy<T>> strategies;
  private final GameService<T> service;

  public HistoryBasedPlayoutSimulator(
          HistoryBasedStrategy<T> playerOneStrategy, HistoryBasedStrategy<T> playerTwoStrategy, final GameService service) {
    this.strategies =
        new EnumMap<>(
            ImmutableMap.of(Player.ONE, playerOneStrategy, Player.TWO, playerTwoStrategy));
    this.service = service;
  }

  public Player playout(final List<Integer> history) {
    T state = service.newInitialState();
    List<Integer> localHistory = new ArrayList<>(history);

    for (int i = 0; i < localHistory.size(); i++) {
      if (!state.isMoveAllowed(localHistory.get(i))) {
        throw new IllegalArgumentException(
            "Move not allowed: " + localHistory.get(i) + " in " + state);
      }
      state.move(localHistory.get(i));
    }

    while (!state.isGameOver()) {
      int move = strategies.get(state.getCurrentPlayer()).nextMove(localHistory);
      if (!state.isMoveAllowed(move)) {
        throw new IllegalArgumentException("Move not allowed: " + move + " in " + state);
      }
      state.move(move);
      localHistory.add(move);
      if (localHistory.size() > MAX_MOVES) {
        throw new IllegalStateException("Too many moves");
      }
    }

    return state.getWinner();
  }
}
