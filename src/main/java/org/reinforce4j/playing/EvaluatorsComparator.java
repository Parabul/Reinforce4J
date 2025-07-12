package org.reinforce4j.playing;

import java.util.function.Supplier;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Outcomes;
import org.reinforce4j.core.Player;
import org.reinforce4j.evaluation.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluatorsComparator {

  private static final Logger logger = LoggerFactory.getLogger(EvaluatorsComparator.class);
  private final int totalGames;
  private final NumberOfMoves numberOfMoves;
  private final Supplier<GameState> initialStateSupplier;

  public EvaluatorsComparator(
      NumberOfMoves numberOfMoves, Supplier<GameState> initialStateSupplier) {
    this(numberOfMoves, initialStateSupplier, 10000);
  }

  public EvaluatorsComparator(
      NumberOfMoves numberOfMoves, Supplier<GameState> initialStateSupplier, int totalGames) {
    this.numberOfMoves = numberOfMoves;
    this.initialStateSupplier = initialStateSupplier;
    this.totalGames = totalGames;
  }

  /** 99% confidence interval */
  private double confidenceInterval(double p, int n) {
    return 2.576 * Math.sqrt(p * (1 - p) / n);
  }

  // Returns true when a candidate strategy outperforms the incumbent.
  public boolean candidateIsBetter(Evaluator incumbent, Evaluator candidate) {
    PolicySamplingStrategy incumbentStrategy = new PolicySamplingStrategy(numberOfMoves, incumbent);

    PolicySamplingStrategy candidateStrategy = new PolicySamplingStrategy(numberOfMoves, candidate);

    PlayOutSimulator simulator =
        new PlayOutSimulator(incumbentStrategy, candidateStrategy, initialStateSupplier);

    PlayOutSimulator inverseSimulator =
        new PlayOutSimulator(candidateStrategy, incumbentStrategy, initialStateSupplier);

    Outcomes outcomes = new Outcomes();
    Outcomes inverseOutcomes = new Outcomes();
    for (int i = 0; i < totalGames; i++) {
      if (i % 100 == 0) {
        logger.info("Round: {}", i);
      }

      outcomes.addWinner(simulator.playOut());
      inverseOutcomes.addWinner(inverseSimulator.playOut());
    }

    logger.info("Outcomes: {}", outcomes);
    logger.info("Inverse outcomes: {}", inverseOutcomes);

    logger.info("{} {}", outcomes.winRateFor(Player.ONE), inverseOutcomes.winRateFor(Player.TWO));
    logger.info("{} {}", outcomes.winRateFor(Player.TWO), inverseOutcomes.winRateFor(Player.ONE));
    double incumbentWinRate =
        (outcomes.winRateFor(Player.ONE) + inverseOutcomes.winRateFor(Player.TWO)) / 2;
    logger.info("Original incumbent win rate: {}", incumbentWinRate);
    incumbentWinRate = incumbentWinRate + confidenceInterval(incumbentWinRate, totalGames * 2);
    logger.info("Update incumbent win rate: {}", incumbentWinRate);

    double candidateWinRate =
        (outcomes.winRateFor(Player.TWO) + inverseOutcomes.winRateFor(Player.ONE)) / 2;
    logger.info("Original candidate win rate: {}", candidateWinRate);
    candidateWinRate = candidateWinRate - confidenceInterval(candidateWinRate, totalGames * 2);
    logger.info("Update candidate win rate: {}", candidateWinRate);
    return candidateWinRate > incumbentWinRate;
  }
}
