package org.reinforce4j.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;
import org.reinforce4j.montecarlo.MonteCarloTreeSearch;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;
import org.reinforce4j.playing.MonteCarloTreeSearchStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayWithStrategy {

  private static final Logger logger = LoggerFactory.getLogger(PlayWithStrategy.class);

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    logger.info("Start");

    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(400000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(
                () ->
                    new GameOverEvaluator<>(
                        new TensorflowEvaluator<>(
                            TensorflowEvaluator.TIC_TAC_TOE_V3, TicTacToeService.INSTANCE)))
            .build();

    MonteCarloTreeSearch<TicTacToe> monteCarloTreeSearch = new MonteCarloTreeSearch<>(settings);
    monteCarloTreeSearch.init();

    int n = 1_000_000;

    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    MonteCarloTreeSearchStrategy<TicTacToe> strategy =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);
    logger.info("Init complete");

    TicTacToe ticTacToe = TicTacToeService.INSTANCE.newInitialState();
    List<Integer> history = new ArrayList<>();

    logger.info(ticTacToe.toString());

    while (!ticTacToe.isGameOver()) {
      logger.info("Your move... ");
      int humanMove = in.nextInt();
      ticTacToe.move(humanMove);
      history.add(humanMove);
      logger.info(ticTacToe.toString());

      if (ticTacToe.isGameOver()) {
        break;
      }

      int robotMove = strategy.nextMove(history);
      logger.info("Robot move " + robotMove);
      ticTacToe.move(robotMove);
      logger.info(ticTacToe.toString());
      history.add(robotMove);
    }

    logger.info("Winner: " + ticTacToe.getWinner());
  }
}
