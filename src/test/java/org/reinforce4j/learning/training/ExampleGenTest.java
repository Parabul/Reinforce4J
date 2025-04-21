package org.reinforce4j.learning.training;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.games.TicTacToeService;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;
import org.reinforce4j.utils.tfrecord.TFRecordReader;
import org.tensorflow.example.Example;

public class ExampleGenTest {

  @Test
  public void shouldGenerateBootstrapData() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ExampleGen dataGenerator =
        new ExampleGen(
            out,
            ExampleGenSettings.withDefaults(
                    MonteCarloTreeSearchSettings.withDefaults()
                        .setGameService(() -> TicTacToeService.INSTANCE)
                        .setNodesPoolCapacity(400_000)
                        .setEvaluator(
                            () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
                        .build())
                .setBasePath("/not/used")
                .setNumIterations(10)
                .setNumExpansions(10000)
                .build());
    long numRecords = dataGenerator.call();
    assertThat(numRecords).isGreaterThan(1000);

    TFRecordReader reader = new TFRecordReader(new ByteArrayInputStream(out.toByteArray()), false);
    for (int i = 0; i < numRecords; i++) {
      Example example = Example.parseFrom(reader.read());
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(9);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(10);
    }
  }

  @Test
  public void shouldGenerateSelfPlayData() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ExampleGen dataGenerator =
        new ExampleGen(
            out,
            ExampleGenSettings.withDefaults(
                    MonteCarloTreeSearchSettings.withDefaults()
                        .setGameService(() -> TicTacToeService.INSTANCE)
                        .setEvaluator(
                            () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
                            .setNodesPoolCapacity(400_000)
                        .build())
                .setBasePath("/not/used")
                .setNumIterations(3)
                .setNumExpansions(100000)
                .build());
    long numRecords = dataGenerator.call();
    assertThat(numRecords).isGreaterThan(1000);

    TFRecordReader reader = new TFRecordReader(new ByteArrayInputStream(out.toByteArray()), false);
    for (int i = 0; i < numRecords; i++) {
      Example example = Example.parseFrom(reader.read());
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(9);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(10);
    }
  }

  @Test
  public void shouldGenerateBootstrapDataConnect4() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ExampleGen dataGenerator =
        new ExampleGen(
            out,
            ExampleGenSettings.withDefaults(
                    MonteCarloTreeSearchSettings.withDefaults()
                        .setGameService(() -> Connect4Service.INSTANCE)
                        .setNodesPoolCapacity(8_000_000)
                        .setEvaluator(
                            () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)))
                        .build())
                .setBasePath("/not/used")
                .setNumIterations(2)
                .setNumExpansions(100_000)
                .build());
    long numRecords = dataGenerator.call();
    assertThat(numRecords).isGreaterThan(100);

    TFRecordReader reader = new TFRecordReader(new ByteArrayInputStream(out.toByteArray()), false);
    for (int i = 0; i < numRecords; i++) {
      Example example = Example.parseFrom(reader.read());
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
          .hasSize(42);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
          .hasSize(8);
    }
  }

  @Test
  public void shouldTriggerPrune() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ExampleGen dataGenerator =
            new ExampleGen(
                    out,
                    ExampleGenSettings.withDefaults(
                                    MonteCarloTreeSearchSettings.withDefaults()
                                            .setGameService(() -> TicTacToeService.INSTANCE)
                                            .setNodesPoolCapacity(100_000)
                                            .setEvaluator(
                                                    () -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
                                            .build())
                            .setBasePath("/not/used")
                            .setNumIterations(10)
                            .setNumExpansions(1000_000)
                            .build());
    long numRecords = dataGenerator.call();
    assertThat(numRecords).isGreaterThan(1000);

    TFRecordReader reader = new TFRecordReader(new ByteArrayInputStream(out.toByteArray()), false);
    for (int i = 0; i < numRecords; i++) {
      Example example = Example.parseFrom(reader.read());
      assertThat(example.getFeatures().getFeatureMap().keySet()).containsExactly("input", "output");
      assertThat(example.getFeatures().getFeatureMap().get("input").getFloatList().getValueList())
              .hasSize(9);
      assertThat(example.getFeatures().getFeatureMap().get("output").getFloatList().getValueList())
              .hasSize(10);
    }
  }
}
