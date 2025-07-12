package org.reinforce4j.montecarlo.tasks;

import org.reinforce4j.montecarlo.TreeNode;

/** Factory class that generates Tasks executed against SearchTree. */
public interface ExpandTaskFactory {
  ExpandTask create(TreeNode root, int numExpansions);
}
