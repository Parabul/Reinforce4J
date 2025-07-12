package org.reinforce4j.montecarlo;

import com.google.auto.value.AutoValue;
import java.util.function.Supplier;
import org.reinforce4j.evaluation.Evaluator;

@AutoValue
public abstract class MonteCarloTreeSearchSettings {

  public static Builder builder() {
    return new AutoValue_MonteCarloTreeSearchSettings.Builder();
  }

  public static Builder withDefaults() {
    return new AutoValue_MonteCarloTreeSearchSettings.Builder()
        .setPruneMinVisits(10)
        .setWriteMinVisits(30);
  }

  public abstract Supplier<Evaluator> evaluator();

  public abstract int writeMinVisits();

  public abstract int pruneMinVisits();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract MonteCarloTreeSearchSettings.Builder setEvaluator(
        Supplier<Evaluator> evaluator);

    public abstract MonteCarloTreeSearchSettings.Builder setWriteMinVisits(int value);

    public abstract MonteCarloTreeSearchSettings.Builder setPruneMinVisits(int value);

    public abstract MonteCarloTreeSearchSettings build();
  }
}
