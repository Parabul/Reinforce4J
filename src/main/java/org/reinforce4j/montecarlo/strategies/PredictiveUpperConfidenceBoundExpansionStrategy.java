package org.reinforce4j.montecarlo.strategies;

import com.google.inject.Inject;
import java.util.Arrays;
import org.apache.commons.rng.sampling.distribution.DirichletSampler;
import org.apache.commons.rng.simple.RandomSource;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.montecarlo.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Strategy that estimates values of the alternative moves using Predictive Upper Confidence Bound:
// U(s,a) = Q(s,a) + c * P(s,a) * SQRT(N(s)) / (1+N(s,a)), where `c` is exploration weight;
// P(s,a) = (1 - epsilon) * P_prior(s,a) + epsilon * Dir(1.0), where `epsilon` is noise weight.
public class PredictiveUpperConfidenceBoundExpansionStrategy implements ExpansionStrategy {

  // A constant that controls the balance between exploration and exploitation.
  private static final float EXPLORATION_WEIGHT = 4;
  // A constant that determines how much noise to add to prior win probabilities.
  private static final float NOISE_WEIGHT = 0.25f;

  private static final Logger logger =
      LoggerFactory.getLogger(PredictiveUpperConfidenceBoundExpansionStrategy.class);

  private final DirichletSampler dirichlet;
  private final int numberOfMoves;

  @Inject
  public PredictiveUpperConfidenceBoundExpansionStrategy(NumberOfMoves numberOfMoves) {
    this.numberOfMoves = numberOfMoves.value();
    this.dirichlet =
        DirichletSampler.symmetric(
            RandomSource.XO_RO_SHI_RO_128_PP.create(), numberOfMoves.value(), 1);
  }

  // Return an index of the child node that has the highest estimated value. Assumes that treeNode
  // is initialized.
  public int suggestMove(TreeNode treeNode) {
    if (!treeNode.isInitialized()) {
      throw new IllegalStateException("State node is not initialized!");
    }

    if (treeNode.isLeaf()) {
      throw new IllegalStateException("State node is leaf!");
    }

    double[] noises = dirichlet.sample();

    float max = -Float.MAX_VALUE;
    int indexOfMax = -1;
    float[] values = new float[numberOfMoves];

    double parentVisitsSqrt = Math.sqrt(1 + treeNode.getVisits());

    for (int i = 0; i < treeNode.getChildStates().length; i++) {
      TreeNode childState = treeNode.getChildStates()[i];
      if (childState == null) {
        continue;
      }

      float priorProbability = treeNode.evaluation().getPolicy()[i];

      float adjustedProbability =
          (float) (priorProbability * (1 - NOISE_WEIGHT) + NOISE_WEIGHT * noises[i]);

      float exploration =
          (float) (adjustedProbability * parentVisitsSqrt / (1 + childState.getVisits()));
      float exploitation =
          childState.getAverageValue().getValue(treeNode.state().getCurrentPlayer());

      float estimatedValue = exploitation + EXPLORATION_WEIGHT * exploration;
      values[i] = estimatedValue;
      if (estimatedValue > max) {
        max = estimatedValue;
        indexOfMax = i;
      }
    }
    if (indexOfMax == -1) {
      logger.error(
          "Could not find any child states: {} values in {}", Arrays.toString(values), treeNode);
      throw new RuntimeException(
          "Could not find any child states: " + Arrays.toString(values) + ", " + treeNode);
    }

    return indexOfMax;
  }
}
