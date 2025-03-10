package org.reinforce4j.montecarlo;

import com.google.auto.value.AutoValue;
import java.util.function.Supplier;
import org.reinforce4j.core.GameService;
import org.reinforce4j.core.GameState;
import org.reinforce4j.evaluation.Evaluator;

@AutoValue
public abstract class MonteCarloTreeSearchSettings<T extends GameState> {

  public static <T extends GameState> Builder<T> builder() {
    return new AutoValue_MonteCarloTreeSearchSettings.Builder<>();
  }

  public static <T extends GameState> Builder withDefaults() {
    return new AutoValue_MonteCarloTreeSearchSettings.Builder<>()
        .setBackPropagationStackCapacity(50)
        .setNodesPoolCapacity(1_000_000)
        .setPruneMinVisits(10)
        .setWriteMinVisits(30);
  }

  abstract Supplier<GameService<T>> gameService();

  abstract Supplier<Evaluator<T>> evaluator();

  abstract int backPropagationStackCapacity();

  abstract int nodesPoolCapacity();

  abstract int writeMinVisits();

  abstract int pruneMinVisits();

  @AutoValue.Builder
  public abstract static class Builder<T extends GameState> {

    public abstract MonteCarloTreeSearchSettings.Builder<T> setGameService(
        Supplier<GameService<T>> service);

    public abstract MonteCarloTreeSearchSettings.Builder<T> setEvaluator(
        Supplier<Evaluator<T>> evaluator);

    public abstract MonteCarloTreeSearchSettings.Builder<T> setBackPropagationStackCapacity(
        int value);

    public abstract MonteCarloTreeSearchSettings.Builder<T> setNodesPoolCapacity(int value);

    public abstract MonteCarloTreeSearchSettings.Builder<T> setWriteMinVisits(int value);

    public abstract MonteCarloTreeSearchSettings.Builder<T> setPruneMinVisits(int value);

    public abstract MonteCarloTreeSearchSettings<T> build();
  }
}
