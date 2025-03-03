package org.reinforce4j.learning.training;

import com.google.auto.value.AutoValue;
import org.reinforce4j.core.GameState;
import org.reinforce4j.montecarlo.StateNodeService;

import java.util.function.Supplier;

@AutoValue
public abstract class ExampleGenSettings<T extends GameState> {

  public static <T extends GameState> Builder<T> builder() {
    return new AutoValue_ExampleGenSettings.Builder();
  }

  public static <T extends GameState> Builder withDefaults(
      Supplier<StateNodeService<T>> nodeServiceSupplier) {
    return new AutoValue_ExampleGenSettings.Builder()
        .setNumExpansions(50_000)
        .setNumThreads(4)
        .setNumIterations(300)
        .setMinNumOutcomes(30)
        .setServiceSupplier(nodeServiceSupplier);
  }

  abstract Supplier<StateNodeService<T>> serviceSupplier();

  abstract String basePath();

  abstract int numThreads();

  abstract int numIterations();

  abstract int numExpansions();

  abstract int minNumOutcomes();

  @AutoValue.Builder
  public abstract static class Builder<T extends GameState> {

    public abstract ExampleGenSettings.Builder<T> setBasePath(String value);

    public abstract ExampleGenSettings.Builder<T> setNumThreads(int value);

    public abstract ExampleGenSettings.Builder<T> setNumIterations(int value);

    public abstract ExampleGenSettings.Builder<T> setNumExpansions(int value);

    public abstract ExampleGenSettings.Builder<T> setMinNumOutcomes(int value);

    public abstract ExampleGenSettings.Builder<T> setServiceSupplier(
        Supplier<StateNodeService<T>> value);

    public abstract ExampleGenSettings<T> build();
  }
}
