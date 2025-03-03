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
  public void expandOnceAndTraverse() {

    StateNodeService<TicTacToe> nodeService =
        new StateNodeService(
            new TicTacToeService(),
            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)),
            10000,
            30);
    nodeService.init();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(nodeService);

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

    StateNodeService<TicTacToe> nodeService =
        new StateNodeService(
            new TicTacToeService(),
            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)),
            400000,
            30);

    nodeService.init();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(nodeService);

    int n = 1000_000;

    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    ByteArrayOutputStream outputStreamCandidate = new ByteArrayOutputStream();
    TFRecordWriter candidateWriter =
        new TFRecordWriter(new DataOutputStream(outputStreamCandidate));

    long written = monteCarloTreeSearch.writeTo(candidateWriter);

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
    TicTacToeService service = new TicTacToeService();
    StateNodeService<TicTacToe> nodeService =
        new StateNodeService(
            service,
            new GameOverEvaluator<>(
                new TensorflowEvaluator<>(TensorflowEvaluator.TIC_TAC_TOE_V3, service)),
            400000,
            30);

    nodeService.init();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(nodeService);

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
    Connect4Service service = new Connect4Service();
    StateNodeService<Connect4> nodeService =
        new StateNodeService(
            service,
            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(service.numMoves())),
            6_000_000,
            30);

    nodeService.init();

    Runtime runtime = Runtime.getRuntime();

    long maxMemory = runtime.maxMemory(); // Maximum memory the JVM can use
    long allocatedMemory = runtime.totalMemory(); // Currently allocated memory
    long freeMemory = runtime.freeMemory(); // Free memory in the allocated space

    long availableMemory = maxMemory - (allocatedMemory - freeMemory); // Available memory
    System.out.println("Available memory: " + availableMemory / (1024 * 1024) + "MB");

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(nodeService);

    int n = 100_000;

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    System.out.println("Start");
    stopwatch.start();

    System.out.println(stopwatch);
    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    System.out.println("Expand complete");
    System.out.println(stopwatch);

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    System.out.println(nodeService.toExample(root));

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
    Connect4Service service = new Connect4Service();
    StateNodeService<Connect4> nodeService =
        new StateNodeService(
            service,
            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(service.numMoves())),
            6_000_000,
            30);

    nodeService.init();

    Runtime runtime = Runtime.getRuntime();

    long maxMemory = runtime.maxMemory(); // Maximum memory the JVM can use
    long allocatedMemory = runtime.totalMemory(); // Currently allocated memory
    long freeMemory = runtime.freeMemory(); // Free memory in the allocated space

    long availableMemory = maxMemory - (allocatedMemory - freeMemory); // Available memory
    System.out.println("Available memory: " + availableMemory / (1024 * 1024) + "MB");

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(nodeService);

    int n = 1000_000;

    Stopwatch stopwatch = Stopwatch.createUnstarted();
    System.out.println("Start");
    stopwatch.start();

    System.out.println(stopwatch);
    for (int i = 0; i < n; i++) {
      if (nodeService.getUsage() > 0.95) {
        int before = nodeService.getSize();
        monteCarloTreeSearch.prune(10);
        int after = nodeService.getSize();

        System.out.println("Prune with " + after + "  nodes, " + before + "  nodes");
        System.out.println(stopwatch);
      }
      monteCarloTreeSearch.expand();
    }

    System.out.println("Expand complete");
    System.out.println(stopwatch);

    StateNode root = monteCarloTreeSearch.getRoot();

    assertThat(root.getVisits()).isEqualTo(n);

    System.out.println(nodeService.toExample(root));

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
