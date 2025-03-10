package org.reinforce4j.utils;

import com.google.common.base.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.montecarlo.MonteCarloTreeSearch;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;
import org.reinforce4j.playing.MonteCarloTreeSearchStrategy;

public class PlayWithStrategyConnect4 {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Start");
    Stopwatch stopwatch = Stopwatch.createStarted();

    MonteCarloTreeSearchSettings<Connect4> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(5000_000)
            .setGameService(() -> Connect4Service.INSTANCE)
            .setEvaluator(
                () ->
                    new GameOverEvaluator<>(
                        new TensorflowEvaluator<>(
                            TensorflowEvaluator.CONNECT4_V1, Connect4Service.INSTANCE)))
            //            .setEvaluator(() -> new GameOverEvaluator<>(new
            // ZeroValueUniformEvaluator<>(7)))
            .build();

    MonteCarloTreeSearch<Connect4> monteCarloTreeSearch = new MonteCarloTreeSearch<>(settings);
    monteCarloTreeSearch.init();

    System.out.println("Init complete: " + stopwatch);

    int n = 1_000_000;

    for (int i = 0; i < n; i++) {
      if (monteCarloTreeSearch.getUsage() > 0.99) {
        int before = monteCarloTreeSearch.getSize();
        monteCarloTreeSearch.prune();
        int after = monteCarloTreeSearch.getSize();

        System.out.println("Prune with " + after + "  nodes, " + before + "  nodes");
        System.out.println(stopwatch);
      }
      monteCarloTreeSearch.expand();
    }

    MonteCarloTreeSearchStrategy<Connect4> strategy =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);
    System.out.println("Expand complete: " + stopwatch);

    Connect4 game = Connect4Service.INSTANCE.newInitialState();
    List<Integer> history = new ArrayList<>();

    System.out.println(game);

    while (!game.isGameOver()) {
      System.out.println("Your move... ");
      int humanMove = in.nextInt();
      game.move(humanMove);
      history.add(humanMove);
      System.out.println(game);

      if (game.isGameOver()) {
        break;
      }

      int robotMove = strategy.nextMove(history);
      System.out.println("Robot move " + robotMove);
      game.move(robotMove);
      System.out.println(game);
      history.add(robotMove);
    }

    System.out.println("Winner: " + game.getWinner());
  }
}
