package org.reinforce4j.exploration;

import java.util.Set;
import org.reinforce4j.core.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiverseSetGenerator implements RandomStateGenerator {

  private static final Logger logger = LoggerFactory.getLogger(DiverseSetGenerator.class);
  private final DiverseSet diverseSet;
  private final RandomStateGenerator delegate;
  private final int numIterations;

  public DiverseSetGenerator(
      DiverseSet diverseSet, RandomStateGenerator delegate, int numIterations) {
    this.diverseSet = diverseSet;
    this.delegate = delegate;
    this.numIterations = numIterations;
  }

  @Override
  public Set<GameState> get() {
    int successfulIterations = 0;
    for (int i = 0; i < numIterations; i++) {
      if (i % 100 == 0) {
        logger.info("Iteration " + i + " of " + numIterations);
      }
      if (diverseSet.offerAll(delegate.get())) {
        successfulIterations++;
      }
    }

    logger.info(
        "Successfully generated {} iterations out of total {}",
        successfulIterations,
        numIterations);

    return diverseSet.get();
  }
}
