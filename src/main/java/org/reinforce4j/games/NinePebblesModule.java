package org.reinforce4j.games;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.reinforce4j.constants.NumberOfFeatures;
import org.reinforce4j.constants.NumberOfMoves;
import org.reinforce4j.core.GameModule;

public class NinePebblesModule extends AbstractModule implements GameModule {
  @Override
  @Provides
  @Singleton
  public NumberOfMoves provideNumberOfMoves() {
    return new NumberOfMoves(NinePebbles.NUM_MOVES);
  }

  @Override
  @Provides
  @Singleton
  public NumberOfFeatures provideNumberFeatures() {
    return new NumberOfFeatures(NinePebbles.NUM_FEATURES);
  }
}
