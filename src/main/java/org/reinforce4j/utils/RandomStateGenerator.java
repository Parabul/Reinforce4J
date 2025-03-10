package org.reinforce4j.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.playing.RandomStrategy;

public class RandomStateGenerator<T extends GameState> {

  private final Random random = new Random();
  private final GameService<T> gameService;
  private final int maxNumMoves;
  private final RandomStrategy<T> strategy;

  public RandomStateGenerator(GameService<T> service, int maxNumMoves) {
    this.gameService = service;
    this.maxNumMoves = maxNumMoves;
    this.strategy = new RandomStrategy<>(service);
  }

  public T next() {
    T state = gameService.newInitialState();
    int numMoves = random.nextInt(maxNumMoves) + 1;
    List<Integer> history = new ArrayList<>();

    for (int i = 0; i < numMoves; i++) {
      int move = strategy.nextMove(history);
      history.add(move);
      state.move(move);
    }

    return state;
  }
}
