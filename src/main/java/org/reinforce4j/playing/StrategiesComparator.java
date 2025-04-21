package org.reinforce4j.playing;

import java.util.List;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Outcomes;
import org.reinforce4j.core.Player;
import org.reinforce4j.utils.RandomStateGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategiesComparator<T extends GameState> {

  private static final Logger logger = LoggerFactory.getLogger(StrategiesComparator.class);
  private final GameService<T> service;
  private final RandomStateGenerator<T> randomStateGenerator;
  private final int totalGames = 10;

  public StrategiesComparator(GameService<T> service) {
    this.service = service;
    this.randomStateGenerator = new RandomStateGenerator(service, 4);
  }

  // Returns true when a candidate strategy outperforms the incumbent.
  public boolean candidateIsBetter(Strategy incumbent, Strategy candidate) {

    PlayoutSimulator simulator = new PlayoutSimulator(incumbent, candidate, service);

    PlayoutSimulator inverseSimulator = new PlayoutSimulator(candidate, incumbent, service);

    Outcomes outcomes = new Outcomes();
    Outcomes inverseOutcomes = new Outcomes();
    for (int i = 0; i < totalGames; i++) {
      logger.info("Round: {}", i);
      List<Integer> randomPlayout = randomStateGenerator.next();
      Player winner = simulator.playout(randomPlayout);
      Player inverseWinner = inverseSimulator.playout(randomPlayout);

      outcomes.addWinner(winner);
      inverseOutcomes.addWinner(inverseWinner);
    }

    logger.info("Outcomes: {}", outcomes);
    logger.info("Inverse Outcomes: {}", inverseOutcomes);

    double metric =
        (outcomes.valueFor(Player.ONE) + inverseOutcomes.valueFor(Player.TWO))
            - (outcomes.valueFor(Player.TWO) + inverseOutcomes.valueFor(Player.ONE));

    logger.info("Metric: {}", metric);
    return metric < 0.0;
  }
}
