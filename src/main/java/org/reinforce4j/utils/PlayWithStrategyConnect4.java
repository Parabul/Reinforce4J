package org.reinforce4j.utils;

import com.google.common.base.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.montecarlo.MonteCarloTreeSearch;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;
import org.reinforce4j.playing.MonteCarloTreeSearchStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayWithStrategyConnect4 {

  private static final Logger logger = LoggerFactory.getLogger(PlayWithStrategyConnect4.class);

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    logger.info("Start");
    Stopwatch stopwatch = Stopwatch.createStarted();

    MonteCarloTreeSearchSettings<Connect4> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(5000_000)
            .setGameService(() -> Connect4Service.INSTANCE)
            .setEvaluator(
                () ->
                    new GameOverEvaluator<>(
                        new OnnxEvaluator<>(OnnxEvaluator.CONNECT4_V1, Connect4Service.INSTANCE)))
            .build();

    MonteCarloTreeSearch<Connect4> monteCarloTreeSearch = new MonteCarloTreeSearch<>(settings);
    monteCarloTreeSearch.init();

    logger.info("Init complete: " + stopwatch);

    int n = 10_000;

    for (int i = 0; i < n; i++) {
      if (monteCarloTreeSearch.getUsage() > 0.99) {
        int before = monteCarloTreeSearch.getSize();
        monteCarloTreeSearch.prune();
        int after = monteCarloTreeSearch.getSize();

        logger.info("Before prune: {}, after prune: {}, at {}", before, after, stopwatch);
      }
      monteCarloTreeSearch.expand();
    }

    MonteCarloTreeSearchStrategy<Connect4> strategy =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);
    logger.info("Expand complete: " + stopwatch);

    Connect4 game = Connect4Service.INSTANCE.newInitialState();
    List<Integer> history = new ArrayList<>();

    logger.info(game.toString());

    while (!game.isGameOver()) {
      logger.info("Your move... ");
      int humanMove = in.nextInt();
      game.move(humanMove);
      history.add(humanMove);
      logger.info(game.toString());

      if (game.isGameOver()) {
        break;
      }

      int robotMove = strategy.nextMove(history);
      logger.info("Robot move " + robotMove);
      game.move(robotMove);
      logger.info(game.toString());
      history.add(robotMove);
    }

    logger.info("Winner: " + game.getWinner());
  }
}
