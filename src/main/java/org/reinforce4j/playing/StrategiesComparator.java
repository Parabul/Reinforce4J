package org.reinforce4j.playing;

import java.util.List;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.core.Outcomes;
import org.reinforce4j.core.Player;
import org.reinforce4j.utils.RandomStateGenerator;

public class StrategiesComparator<T extends GameState> {

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
      System.out.println("Round " + i + ":");
      List<Integer> randomPayout = randomStateGenerator.next();
      Player winner = simulator.playout(randomPayout);
      Player inverseWinner = inverseSimulator.playout(randomPayout);

      outcomes.addWinner(winner);
      inverseOutcomes.addWinner(inverseWinner);
    }

    System.out.println(outcomes);
    System.out.println(inverseOutcomes);

    return true;
  }
}
