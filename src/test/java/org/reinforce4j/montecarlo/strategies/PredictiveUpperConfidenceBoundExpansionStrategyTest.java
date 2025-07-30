package org.reinforce4j.montecarlo.strategies;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.evaluation.Evaluator;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.montecarlo.AverageValue;
import org.reinforce4j.montecarlo.TreeNode;

public class PredictiveUpperConfidenceBoundExpansionStrategyTest {

  @Test
  public void suggestMovesEvenlyWhenNoInformation() {
    int numMoves = 9;

    Evaluator evaluator = new GameOverEvaluator(new ZeroValueUniformEvaluator(numMoves));
    PredictiveUpperConfidenceBoundExpansionStrategy nodeSelectionStrategy =
        new PredictiveUpperConfidenceBoundExpansionStrategy(new NumberOfMoves(numMoves));

    int[] histogram = new int[numMoves];
    int times = 1_000_000;

    TreeNode root = new TreeNode(new TicTacToe(), numMoves);
    root.initChildren(evaluator);

    for (int i = 0; i < times; i++) {
      histogram[nodeSelectionStrategy.suggestMove(root)]++;
    }

    float[] dist = new float[numMoves];

    for (int i = 0; i < numMoves; i++) {
      dist[i] = 1.0f * histogram[i] / times;
    }

    assertThat(dist)
        .usingTolerance(0.01)
        .containsExactly(0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11)
        .inOrder();
  }

  @Test
  public void suggestMoveWithHigherValue() {
    int numMoves = 9;

    Evaluator evaluator = new GameOverEvaluator(new ZeroValueUniformEvaluator(numMoves));
    PredictiveUpperConfidenceBoundExpansionStrategy nodeSelectionStrategy =
        new PredictiveUpperConfidenceBoundExpansionStrategy(new NumberOfMoves(numMoves));

    int[] histogram = new int[numMoves];
    int times = 1_000_000;

    TreeNode root = new TreeNode(new TicTacToe(), numMoves);
    root.initChildren(evaluator);
    // Move 5 has some value
    root.getChildStates()[4].getAverageValue().add(new AverageValue(0.2f, 1));

    for (int i = 0; i < times; i++) {

      histogram[nodeSelectionStrategy.suggestMove(root)]++;
    }

    float[] dist = new float[numMoves];

    for (int i = 0; i < numMoves; i++) {
      dist[i] = 1.0f * histogram[i] / times;
    }

    assertThat(dist)
        .usingTolerance(0.01)
        .containsExactly(0.095, 0.095, 0.095, 0.095, 0.23, 0.095, 0.095, 0.095, 0.095)
        .inOrder();
  }

  @Test
  public void suggestMoveWithHigherPriors() {
    int numMoves = 9;

    Evaluator evaluator = new GameOverEvaluator(new ZeroValueUniformEvaluator(numMoves));
    PredictiveUpperConfidenceBoundExpansionStrategy nodeSelectionStrategy =
        new PredictiveUpperConfidenceBoundExpansionStrategy(new NumberOfMoves(numMoves));

    int[] histogram = new int[numMoves];
    int times = 1_000_000;

    TreeNode treeNode = new TreeNode(new TicTacToe(), numMoves);
    treeNode.initChildren(evaluator);

    treeNode.evaluation().setValue(0.0f);
    float[] priors = new float[] {0.2f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f};

    System.arraycopy(priors, 0, treeNode.evaluation().getPolicy(), 0, numMoves);

    for (int i = 0; i < times; i++) {

      histogram[nodeSelectionStrategy.suggestMove(treeNode)]++;
    }

    float[] dist = new float[numMoves];

    for (int i = 0; i < numMoves; i++) {
      dist[i] = 1.0f * histogram[i] / times;
    }

    assertThat(dist)
        .usingTolerance(0.01)
        .containsExactly(0.77, 0.02, 0.02, 0.02, 0.02, 0.02, 0.02, 0.02, 0.02)
        .inOrder();
  }
}
