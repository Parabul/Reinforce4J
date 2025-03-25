package org.reinforce4j.learning.execute;

import com.google.common.annotations.VisibleForTesting;
import java.io.File;
import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Supplies inputs to and executes a Python script that is expected to output a model. */
public class ModelTrainerExecutor {

  private static final Logger logger = LoggerFactory.getLogger(ModelTrainerExecutor.class);

  private final String workingDirectory;
  private final String script;
  private final String inputFilePattern;
  private final String outputModelPath;
  private final long timeoutSeconds = 120L;

  public ModelTrainerExecutor(
      String workingDirectory, String script, String inputFilePattern, String outputModelPath) {
    this.workingDirectory = workingDirectory;
    this.script = script;
    this.inputFilePattern = inputFilePattern;
    this.outputModelPath = outputModelPath;
  }

  @VisibleForTesting
  protected CommandLine getCommandLine() {
    CommandLine cmdLine = new CommandLine("python3");
    cmdLine.addArgument(script);
    cmdLine.addArgument("-i");
    cmdLine.addArgument(inputFilePattern);
    cmdLine.addArgument("-o");
    cmdLine.addArgument(outputModelPath);
    return cmdLine;
  }

  @VisibleForTesting
  protected Executor getExecutor() {
    DefaultExecutor executor =
        DefaultExecutor.builder().setWorkingDirectory(new File(workingDirectory)).get();
    executor.setExitValue(0);
    return executor;
  }

  public int execute() throws IOException {
    CommandLine cmdLine = getCommandLine();
    logger.info(cmdLine.toString());
    Executor executor = getExecutor();
    return executor.execute(cmdLine);
  }
}
