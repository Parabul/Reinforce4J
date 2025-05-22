package org.reinforce4j.playing;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.CompositeSamplers;
import org.apache.commons.rng.simple.RandomSource;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.GameStateAndEvaluationImpl;
import org.reinforce4j.evaluation.StateEvaluation;

/** Strategy that leverages the policy vector in the state evaluation to sample. */
public class PolicySamplingStrategy<T extends GameState> implements StateBasedStrategy<T> {

  private final UniformRandomProvider rng = RandomSource.XO_RO_SHI_RO_128_PP.create();
  private final double[] probabilities;
  private final GameService<T> gameService;
  private final Evaluator<T> evaluator;
  private final CompositeSamplers.DiscreteProbabilitySamplerFactory samplerFactory;

  public PolicySamplingStrategy(GameService<T> gameService, Evaluator<T> evaluator) {
    this.probabilities = new double[gameService.numMoves()];
    this.gameService = gameService;
    this.evaluator = evaluator;
    this.samplerFactory = CompositeSamplers.DiscreteProbabilitySampler.ALIAS_METHOD;
  }

  @Override
  public int nextMove(T state) {
    GameStateAndEvaluationImpl envelope =
        GameStateAndEvaluationImpl.create(state, new StateEvaluation(gameService.numMoves()));
    evaluator.evaluate(envelope);

    for (int i = 0; i < gameService.numMoves(); i++) {
      if (state.isMoveAllowed(i)) {
        probabilities[i] = envelope.evaluation().getPolicy()[i];
      } else {
        probabilities[i] = 0;
      }
    }

    return samplerFactory.create(rng, probabilities).sample();
  }
}
