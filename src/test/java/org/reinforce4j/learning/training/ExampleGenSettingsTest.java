package org.reinforce4j.learning.training;

import static com.google.common.truth.Truth.assertThat;

import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.junit.jupiter.api.Test;

public class ExampleGenSettingsTest {

  //  @Test
  //  public void shouldBuildDefault() {
  //    ExampleGenSettings settings =
  //        ExampleGenSettings.withDefaults(GameRegistry.TIC_TAC_TOE)
  //            .setBasePath("/some/path")
  //            .setEvaluator(new ZeroValueUniformEvaluator(GameRegistry.TIC_TAC_TOE))
  //            .build();
  //
  //    assertThat(settings.factory()).isNotNull();
  //    assertThat(settings.encoder()).isNotNull();
  //    assertThat(settings.evaluator()).isNotNull();
  //
  //    assertThat(settings.basePath()).isEqualTo("/some/path");
  //  }
  //
  //  @Test
  //  public void shouldBuildForBootstrap() {
  //    ExampleGenSettings settings =
  //        ExampleGenSettings.bootstrapRegisteredGame(GameRegistry.TIC_TAC_TOE)
  //            .setBasePath("/some/path")
  //            .build();
  //
  //    assertThat(settings.factory()).isNotNull();
  //    assertThat(settings.encoder()).isNotNull();
  //    assertThat(settings.evaluator()).isNotNull();
  //
  //    assertThat(settings.basePath()).isEqualTo("/some/path");
  //  }
  //
  //  @Test
  //  public void shouldBuildForSelfPlay() {
  //    ExampleGenSettings settings =
  //        ExampleGenSettings.selfPlayRegisteredGame(
  //                GameRegistry.TIC_TAC_TOE, TensorflowEvaluator.TIC_TAC_TOE_V3)
  //            .setBasePath("/some/path")
  //            .build();
  //
  //    assertThat(settings.factory()).isNotNull();
  //    assertThat(settings.encoder()).isNotNull();
  //    assertThat(settings.evaluator()).isNotNull();
  //
  //    assertThat(settings.basePath()).isEqualTo("/some/path");
  //  }
}
