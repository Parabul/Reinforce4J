package org.reinforce4j.playing;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.CompositeSamplers;
import org.apache.commons.rng.simple.RandomSource;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.core.GameState;
import org.reinforce4j.evaluation.EvaluatedGameStateEnvelope;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.StateEvaluation;

/** Strategy that leverages the policy vector in the state evaluation to sample. */
public class PolicySamplingStrategy implements StateBasedStrategy {

  private final UniformRandomProvider rng = RandomSource.XO_RO_SHI_RO_128_PP.create();
  private final double[] probabilities;
  private final Evaluator evaluator;
  private final CompositeSamplers.DiscreteProbabilitySamplerFactory samplerFactory;
  private final int numberOfMoves;

  public PolicySamplingStrategy(NumberOfMoves numberOfMoves, Evaluator evaluator) {
    this.probabilities = new double[numberOfMoves.value()];
    this.numberOfMoves = numberOfMoves.value();
    this.evaluator = evaluator;
    this.samplerFactory = CompositeSamplers.DiscreteProbabilitySampler.ALIAS_METHOD;
  }

  @Override
  public int nextMove(GameState state) {
    EvaluatedGameStateEnvelope envelope =
        EvaluatedGameStateEnvelope.create(state, new StateEvaluation(numberOfMoves));
    evaluator.evaluate(envelope);

    for (int i = 0; i < numberOfMoves; i++) {
      if (state.isMoveAllowed(i)) {
        probabilities[i] = envelope.evaluation().getPolicy()[i];
      } else {
        probabilities[i] = 0;
      }
    }

    return samplerFactory.create(rng, probabilities).sample();
  }
}
