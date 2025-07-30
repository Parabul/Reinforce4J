package org.reinforce4j.games;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.core.GameModule;

public class Connect4Module extends AbstractModule implements GameModule {

  @Provides
  @Singleton
  @Override
  public NumberOfMoves provideNumberOfMoves() {
    return new NumberOfMoves(Connect4.NUM_MOVES);
  }

  @Provides
  @Singleton
  @Override
  public NumberOfFeatures provideNumberFeatures() {
    return new NumberOfFeatures(Connect4.NUM_FEATURES);
  }
}
