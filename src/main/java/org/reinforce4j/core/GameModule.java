package org.reinforce4j.core;

import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;

public interface GameModule {

  // Number of possible moves from any state.
  NumberOfMoves provideNumberOfMoves();

  // Number of features is the length of the encoded array.
  NumberOfFeatures provideNumberFeatures();
}
