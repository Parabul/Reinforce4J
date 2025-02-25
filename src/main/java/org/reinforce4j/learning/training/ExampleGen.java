package org.reinforce4j.learning.training;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import org.reinforce4j.core.GameState;
import org.reinforce4j.core.MonteCarloTreeSearch;
import org.reinforce4j.core.StateNodeService;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;

/** Generates initial training dataset using self play of random move strategy. */
public class ExampleGen<T extends GameState> implements Callable<Long> {

  private final ExampleGenSettings<T> settings;
  private final TFRecordWriter tfRecordWriter;

  public ExampleGen(OutputStream outputStream, ExampleGenSettings<T> settings) {
    this.settings = settings;
    this.tfRecordWriter = new TFRecordWriter(new DataOutputStream(outputStream));
  }

  /**
   * Runs in parallel nThreads threads and outputs nShards to pathPrefix: each iterates nIterations
   * times to expand Monte Carlo Search Tree nExpansions times.
   */
  public static <T extends GameState> long generate(ExampleGenSettings<T> settings)
      throws IOException, InterruptedException, ExecutionException {
    int totalSamples = 0;
    ExecutorService executor = Executors.newFixedThreadPool(settings.numThreads());

    List<FileOutputStream> fileOutputStreams = new ArrayList<>();
    List<Future<Long>> results = new ArrayList<>();

    // Each thread writes into own file
    for (int i = 0; i < settings.numThreads(); i++) {
      FileOutputStream out =
          new FileOutputStream(settings.basePath() + "/training-" + i + ".tfrecord");

      ExampleGen dataGenerator = new ExampleGen(out, settings);
      fileOutputStreams.add(out);
      results.add(executor.submit(dataGenerator));
    }

    executor.shutdown();
    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

    for (OutputStream stream : fileOutputStreams) {
      stream.close();
    }

    for (Future<Long> future : results) {
      totalSamples += future.get();
    }

    return totalSamples;
  }

  public Long call() {
    long samples = 0;
    for (int iter = 0; iter < settings.numIterations(); iter++) {
      StateNodeService<T> nodeService = settings.serviceSupplier().get();
      nodeService.init();
      MonteCarloTreeSearch<T> monteCarloTreeSearch = new MonteCarloTreeSearch<>(nodeService);

      System.out.println(iter);

      for (int i = 0; i < settings.numExpansions(); i++) {
        monteCarloTreeSearch.expand();
      }

      samples += monteCarloTreeSearch.writeTo(tfRecordWriter);
    }

    return samples;
  }
}
