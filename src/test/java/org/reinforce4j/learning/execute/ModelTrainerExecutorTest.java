package org.reinforce4j.learning.execute;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ModelTrainerExecutorTest {

  @TempDir Path tempDir;

  @Test
  public void shouldCreateCorrectCommand() {
    ModelTrainerExecutor executor =
        new ModelTrainerExecutor(
            tempDir.toString(),
            getClass().getClassLoader().getResource("test_args.py").getPath(),
            "/home/anarbek/tmp",
            "/home/anarbek/tmp/model");

    assertThat(ImmutableList.copyOf(executor.getCommandLine().toStrings()))
        .containsAtLeast("-i", "-o", "/home/anarbek/tmp", "/home/anarbek/tmp/model");
  }

  @Test
  public void shouldExecuteTestPy() throws IOException {
    ModelTrainerExecutor executor =
        new ModelTrainerExecutor(
            tempDir.toString(),
            getClass().getClassLoader().getResource("test_args.py").getPath(),
            "/home/anarbek/tmp",
            "/home/anarbek/tmp/model");
    assertThat(executor.execute()).isEqualTo(0);
  }
}
