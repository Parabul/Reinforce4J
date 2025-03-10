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

public class PlayWithStrategy {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Start");

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
    System.out.println("Init complete");

    TicTacToe ticTacToe = TicTacToeService.INSTANCE.newInitialState();
    List<Integer> history = new ArrayList<>();

    System.out.println(ticTacToe);

    while (!ticTacToe.isGameOver()) {
      System.out.println("Your move... ");
      int humanMove = in.nextInt();
      ticTacToe.move(humanMove);
      history.add(humanMove);
      System.out.println(ticTacToe);

      if (ticTacToe.isGameOver()) {
        break;
      }

      int robotMove = strategy.nextMove(history);
      System.out.println("Robot move " + robotMove);
      ticTacToe.move(robotMove);
      System.out.println(ticTacToe);
      history.add(robotMove);
    }

    System.out.println("Winner: " + ticTacToe.getWinner());
  }

  //    TicTacToe ticTacToe =
}
