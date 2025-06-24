package org.reinforce4j.montecarlo;

import java.util.Arrays;
import org.apache.commons.rng.sampling.distribution.DirichletSampler;
import org.apache.commons.rng.simple.RandomSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Strategy that estimates values of the alternative moves using Predictive Upper Confidence Bound:
// U(s,a) = Q(s,a) + c * P(s,a) * SQRT(N(s)) / (1+N(s,a)), where `c` is exploration weight;
// P(s,a) = (1 - epsilon) * P_prior(s,a) + epsilon * Dir(1.0), where `epsilon` is noise weight.
class NodeSelectionStrategy {

  // A constant that controls the balance between exploration and exploitation.
  private static final float EXPLORATION_WEIGHT = 4;
  // A constant that determines how much noise to add to prior win probabilities.
  private static final float NOISE_WEIGHT = 0.25f;

  private static final Logger logger = LoggerFactory.getLogger(NodeSelectionStrategy.class);

  private final DirichletSampler dirichlet;
  private final int numMoves;

  NodeSelectionStrategy(int numMoves) {
    this.numMoves = numMoves;
    this.dirichlet =
        DirichletSampler.symmetric(RandomSource.XO_RO_SHI_RO_128_PP.create(), numMoves, 1);
  }

  // Return an index of the child node that has the highest estimated value. Assumes that stateNode
  // is initialized.
  int suggestMove(StateNode stateNode) {

    double[] noises = dirichlet.sample();

    float max = -Float.MAX_VALUE;
    int indexOfMax = -1;
    float[] values = new float[numMoves];

    double parentVisitsSqrt = Math.sqrt(1 + stateNode.getVisits());

    for (int i = 0; i < stateNode.getChildStates().length; i++) {
      StateNode childState = stateNode.getChildStates()[i];
      if (childState == null) {
        continue;
      }

      float priorProbability = stateNode.evaluation().getPolicy()[i];

      float adjustedProbability =
          (float) (priorProbability * (1 - NOISE_WEIGHT) + NOISE_WEIGHT * noises[i]);

      float exploration =
          (float) (adjustedProbability * parentVisitsSqrt / (1 + childState.getVisits()));
      float exploitation =
          childState.getAverageValue().getValue(stateNode.state().getCurrentPlayer());

      float estimatedValue = exploitation + EXPLORATION_WEIGHT * exploration;
      values[i] = estimatedValue;
      if (estimatedValue > max) {
        max = estimatedValue;
        indexOfMax = i;
      }
    }
    if (indexOfMax == -1) {
      logger.error(
          "Could not find any child states: {} values in {}", Arrays.toString(values), stateNode);
      throw new RuntimeException(
          "Could not find any child states: " + Arrays.toString(values) + ", " + stateNode);
    }

    return indexOfMax;
  }
}
