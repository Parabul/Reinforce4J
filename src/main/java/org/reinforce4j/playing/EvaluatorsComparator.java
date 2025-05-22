package org.reinforce4j.playing;

import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Outcomes;
import org.reinforce4j.core.Player;
import org.reinforce4j.evaluation.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluatorsComparator<T extends GameState> {

  private static final Logger logger = LoggerFactory.getLogger(EvaluatorsComparator.class);
  private final GameService<T> service;
  private final int totalGames = 10000;

  public EvaluatorsComparator(GameService<T> service) {
    this.service = service;
  }

  /** 99% confidence interval */
  private double confidenceInterval(double p, int n) {
    return 2.576 * Math.sqrt(p * (1 - p) / n);
  }

  // Returns true when a candidate strategy outperforms the incumbent.
  public boolean candidateIsBetter(Evaluator<T> incumbent, Evaluator<T> candidate) {
    PolicySamplingStrategy incumbentStrategy = new PolicySamplingStrategy(service, incumbent);

    PolicySamplingStrategy candidateStrategy = new PolicySamplingStrategy(service, candidate);

    StateBasedPlayoutSimulator simulator =
        new StateBasedPlayoutSimulator(incumbentStrategy, candidateStrategy, service);

    StateBasedPlayoutSimulator inverseSimulator =
        new StateBasedPlayoutSimulator(candidateStrategy, incumbentStrategy, service);

    Outcomes outcomes = new Outcomes();
    Outcomes inverseOutcomes = new Outcomes();
    for (int i = 0; i < totalGames; i++) {
      if (i % 100 == 0) {
        logger.info("Round: {}", i);
      }

      outcomes.addWinner(simulator.playout());
      inverseOutcomes.addWinner(inverseSimulator.playout());
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
