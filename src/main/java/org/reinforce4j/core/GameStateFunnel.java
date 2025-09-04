package org.reinforce4j.core;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public enum GameStateFunnel implements Funnel<GameState> {
  INSTANCE;

  @Override
  public void funnel(GameState state, PrimitiveSink into) {
    float[] encoded = state.encode();
    for (int i = 0; i < encoded.length; i++) {
      into.putFloat(encoded[i]);
    }
  }
}
