package org.reinforce4j.learning.training;

import com.google.auto.value.AutoValue;
import org.reinforce4j.core.GameState;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;

@AutoValue
public abstract class ExampleGenSettings<T extends GameState> {

  public static <T extends GameState> Builder<T> builder() {
    return new AutoValue_ExampleGenSettings.Builder();
  }

  public static <T extends GameState> Builder withDefaults(
      MonteCarloTreeSearchSettings monteCarloTreeSearchSettings) {
    return new AutoValue_ExampleGenSettings.Builder()
        .setNumExpansions(500_000)
        .setNumThreads(8)
        .setNumIterations(125)
        .setMonteCarloTreeSearchSettings(monteCarloTreeSearchSettings);
  }

  abstract MonteCarloTreeSearchSettings monteCarloTreeSearchSettings();

  abstract String basePath();

  abstract int numThreads();

  abstract int numIterations();

  abstract int numExpansions();

  @AutoValue.Builder
  public abstract static class Builder<T extends GameState> {

    public abstract ExampleGenSettings.Builder<T> setBasePath(String value);

    public abstract ExampleGenSettings.Builder<T> setNumThreads(int value);

    public abstract ExampleGenSettings.Builder<T> setNumIterations(int value);

    public abstract ExampleGenSettings.Builder<T> setNumExpansions(int value);

    public abstract ExampleGenSettings.Builder<T> setMonteCarloTreeSearchSettings(
        MonteCarloTreeSearchSettings monteCarloTreeSearchSettings);

    public abstract ExampleGenSettings<T> build();
  }
}
