package org.reinforce4j.montecarlo;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.base.Stopwatch;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import org.junit.Test;
import org.reinforce4j.core.Player;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;
import org.reinforce4j.utils.tfrecord.TFRecordWriter;

public class MonteCarloTreeSearchTest {

  private static final double TOLERANCE = 0.00001;

  @Test
  public void acquireAndRelease() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(100)
            .setPruneMinVisits(1)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch<TicTacToe> monteCarloTreeSearch = new MonteCarloTreeSearch<>(settings);
    monteCarloTreeSearch.init();

    // Root initialized:
    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(0.01);
    StateNode<TicTacToe> node1 =
        monteCarloTreeSearch.acquire(TicTacToeService.INSTANCE.initialState());
    node1.state().move(1);
    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(0.02);
    assertThat(node1.state().isMoveAllowed(1)).isEqualTo(false);

    StateNode<TicTacToe> node2 =
        monteCarloTreeSearch.acquire(TicTacToeService.INSTANCE.initialState());
    node2.state().move(5);
    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(0.03);
    assertThat(node2.state().isMoveAllowed(5)).isEqualTo(false);

    monteCarloTreeSearch.release(node1);
    assertThat(node2.state().isMoveAllowed(5)).isEqualTo(false);

    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(0.02);
  }

  @Test
  public void traverseAndPrune() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(100)
            .setPruneMinVisits(10)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch<TicTacToe> monteCarloTreeSearch = new MonteCarloTreeSearch(settings);

    assertThat(monteCarloTreeSearch.getUsage()).isEqualTo(1.0);

    monteCarloTreeSearch.init();
    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(0.01);

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.isLeaf()).isTrue();
    monteCarloTreeSearch.initChildren(root);
    assertThat(root.isLeaf()).isFalse();
    for (int i = 0; i < 9; i++) {
      assertThat(root.getChildStates()[i]).isNotNull();
      assertThat(root.getChildStates()[i].isLeaf()).isTrue();
    }

    StateNode<TicTacToe> childNode = root.getChildStates()[4];
    monteCarloTreeSearch.initChildren(childNode);
    for (int i = 0; i < 9; i++) {
      if (i == 4) {
        assertThat(childNode.getChildStates()[i]).isNull();
      } else {
        assertThat(childNode.getChildStates()[i]).isNotNull();
        assertThat(childNode.getChildStates()[i].isLeaf()).isTrue();
      }
    }

    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(0.18);
    monteCarloTreeSearch.prune();
    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(0.01);
    assertThat(root.isLeaf()).isTrue();
  }

  @Test
  public void expandOnceAndTraverse() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(10_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    monteCarloTreeSearch.expand();

    StateNode root = monteCarloTreeSearch.getRoot();
    // System.out.println(root);

    assertThat(root.getVisits()).isEqualTo(1);
    assertThat(root.getAverageValue().getSupport()).isAtLeast(10);
    assertThat(root.getAverageValue().getValue(Player.ONE)).isNotEqualTo(0.0);

    float[] input = root.state().encode();

    assertThat(input).usingTolerance(TOLERANCE).containsExactly(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);

    float[] output = root.encode();

    assertThat(output).usingTolerance(TOLERANCE).contains(1);
    assertThat(output).usingTolerance(TOLERANCE).contains(0);
  }

  @Test
  public void expandsUsingUniformTicTacToe() {

    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(200_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    int n = 1000_000;

    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    ByteArrayOutputStream outputStreamCandidate = new ByteArrayOutputStream();
    TFRecordWriter candidateWriter =
        new TFRecordWriter(new DataOutputStream(outputStreamCandidate));

    assertThat(monteCarloTreeSearch.getUsage()).isGreaterThan(0.25);
    long written = monteCarloTreeSearch.writeTo(candidateWriter);

    // writeTo dumps nodes into pool.
    assertThat(monteCarloTreeSearch.getUsage()).isWithin(TOLERANCE).of(1.0 / 200_000);

    assertThat(written).isGreaterThan(3000);

    float[] input = root.state().encode();

    assertThat(input).usingTolerance(TOLERANCE).containsExactly(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);

    float[] output = root.encode();

    assertThat(output)
        .usingTolerance(0.01)
        .containsExactly(0.049, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11);
  }

  @Test
  public void expandsUsingTensorFlowTicTacToe() {
    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(400000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(
                () ->
                    new GameOverEvaluator<>(
                        new TensorflowEvaluator<>(
                            TensorflowEvaluator.TIC_TAC_TOE_V3, TicTacToeService.INSTANCE)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    int n = 1_000_000;

    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    ByteArrayOutputStream outputStreamCandidate = new ByteArrayOutputStream();
    TFRecordWriter candidateWriter =
        new TFRecordWriter(new DataOutputStream(outputStreamCandidate));

    long written = monteCarloTreeSearch.writeTo(candidateWriter);

    assertThat(written).isGreaterThan(1000);
    float[] input = root.state().encode();

    assertThat(input).usingTolerance(TOLERANCE).containsExactly(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);

    float[] output = root.encode();
    assertThat(output)
        .usingTolerance(0.01)
        .containsExactly(0.03, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11);
  }

  @Test
  public void expandsUsingUniformConnect4() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    System.out.println("Start");
    System.out.println(stopwatch);

    MonteCarloTreeSearchSettings<Connect4> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(6_000_000)
            .setGameService(() -> Connect4Service.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    Runtime runtime = Runtime.getRuntime();

    long maxMemory = runtime.maxMemory(); // Maximum memory the JVM can use
    long allocatedMemory = runtime.totalMemory(); // Currently allocated memory
    long freeMemory = runtime.freeMemory(); // Free memory in the allocated space

    long availableMemory = maxMemory - (allocatedMemory - freeMemory); // Available memory
    System.out.println("Available memory: " + availableMemory / (1024 * 1024) + "MB");

    int n = 100_000;

    System.out.println("Init complete");
    System.out.println(stopwatch);

    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    System.out.println("Expand complete");
    System.out.println(stopwatch);

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    System.out.println(root.toExample());

    ByteArrayOutputStream outputStreamCandidate = new ByteArrayOutputStream();
    TFRecordWriter candidateWriter =
        new TFRecordWriter(new DataOutputStream(outputStreamCandidate));

    long written = monteCarloTreeSearch.writeTo(candidateWriter);

    assertThat(written).isGreaterThan(1000);

    System.out.println("Traverse complete");
    System.out.println(stopwatch);

    float[] input = root.state().encode();

    Float[] expectedInput = new Float[42];
    Arrays.fill(expectedInput, 0f);
    assertThat(input).usingTolerance(TOLERANCE).containsExactlyElementsIn(expectedInput);
    float[] output = root.encode();

    assertThat(output)
        .usingTolerance(0.02)
        .containsExactly(0.25, 0.12, 0.13, 0.15, 0.17, 0.15, 0.13, 0.12);
  }

  @Test
  public void expandsUsingTensorflowConnect4() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    System.out.println("Start");
    System.out.println(stopwatch);

    MonteCarloTreeSearchSettings<Connect4> settings =
            MonteCarloTreeSearchSettings.withDefaults()
                    .setNodesPoolCapacity(6_000_000)
                    .setGameService(() -> Connect4Service.INSTANCE)
                    .setEvaluator(
                            () ->
                                    new GameOverEvaluator<>(
                                            new TensorflowEvaluator<>(
                                                    TensorflowEvaluator.CONNECT4_V1, Connect4Service.INSTANCE)))
                    .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    Runtime runtime = Runtime.getRuntime();

    long maxMemory = runtime.maxMemory(); // Maximum memory the JVM can use
    long allocatedMemory = runtime.totalMemory(); // Currently allocated memory
    long freeMemory = runtime.freeMemory(); // Free memory in the allocated space

    long availableMemory = maxMemory - (allocatedMemory - freeMemory); // Available memory
    System.out.println("Available memory: " + availableMemory / (1024 * 1024) + "MB");

    int n = 10_000;

    System.out.println("Init complete");
    System.out.println(stopwatch);

    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    System.out.println("Expand complete");
    System.out.println(stopwatch);

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    System.out.println(root.toExample());

    ByteArrayOutputStream outputStreamCandidate = new ByteArrayOutputStream();
    TFRecordWriter candidateWriter =
            new TFRecordWriter(new DataOutputStream(outputStreamCandidate));

    long written = monteCarloTreeSearch.writeTo(candidateWriter);

    assertThat(written).isGreaterThan(1000);

    System.out.println("Traverse complete");
    System.out.println(stopwatch);

    float[] input = root.state().encode();

    Float[] expectedInput = new Float[42];
    Arrays.fill(expectedInput, 0f);
    assertThat(input).usingTolerance(TOLERANCE).containsExactlyElementsIn(expectedInput);
    float[] output = root.encode();

    assertThat(output)
            .usingTolerance(0.02)
            .containsExactly(0.25, 0.12, 0.13, 0.15, 0.17, 0.15, 0.13, 0.12);
  }

  @Test
  public void expandsAndPruneConnect4() {
    MonteCarloTreeSearchSettings<Connect4> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(6_000_000)
            .setPruneMinVisits(10)
            .setGameService(() -> Connect4Service.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    Runtime runtime = Runtime.getRuntime();

    long maxMemory = runtime.maxMemory(); // Maximum memory the JVM can use
    long allocatedMemory = runtime.totalMemory(); // Currently allocated memory
    long freeMemory = runtime.freeMemory(); // Free memory in the allocated space

    long availableMemory = maxMemory - (allocatedMemory - freeMemory); // Available memory
    System.out.println("Available memory: " + availableMemory / (1024 * 1024) + "MB");

    int n = 1000_000;

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    System.out.println("Start");
    stopwatch.start();

    System.out.println(stopwatch);
    for (int i = 0; i < n; i++) {
      if (monteCarloTreeSearch.getUsage() > 0.95) {
        int before = monteCarloTreeSearch.getSize();
        monteCarloTreeSearch.prune();
        int after = monteCarloTreeSearch.getSize();

        System.out.println("Prune with " + after + "  nodes, " + before + "  nodes");
        System.out.println(stopwatch);
      }
      monteCarloTreeSearch.expand();
    }

    System.out.println("Expand complete");
    System.out.println(stopwatch);

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    System.out.println(root.toExample());

    ByteArrayOutputStream outputStreamCandidate = new ByteArrayOutputStream();
    TFRecordWriter candidateWriter =
        new TFRecordWriter(new DataOutputStream(outputStreamCandidate));

    long written = monteCarloTreeSearch.writeTo(candidateWriter);

    assertThat(written).isGreaterThan(1000);

    System.out.println("Traverse complete");
    System.out.println(stopwatch);

    float[] input = root.state().encode();

    Float[] expectedInput = new Float[42];
    Arrays.fill(expectedInput, 0f);
    assertThat(input).usingTolerance(TOLERANCE).containsExactlyElementsIn(expectedInput);
    float[] output = root.encode();

    assertThat(output)
        .usingTolerance(0.02)
        .containsExactly(0.31, 0.12, 0.13, 0.15, 0.17, 0.15, 0.13, 0.12);
  }
}
