package org.reinforce4j.montecarlo.tasks;

import org.reinforce4j.montecarlo.TreeNode;

/** Processes expanded node. */
public interface PostExpansionProcessor {
  void process(TreeNode treeNode);
}
