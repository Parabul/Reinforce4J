package org.reinforce4j.montecarlo.strategies;

import org.reinforce4j.montecarlo.TreeNode;

public interface ExpansionStrategy {

  /**
   * Returns an index of the child node that has the highest estimated value. Assumes that treeNode
   * is initialized.
   */
  int suggestMove(TreeNode treeNode);
}
