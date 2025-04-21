package org.reinforce4j.playing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.OnnxEvaluator;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.Connect4;
import org.reinforce4j.games.Connect4Service;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;
import org.reinforce4j.montecarlo.MonteCarloTreeSearch;
import org.reinforce4j.montecarlo.MonteCarloTreeSearchSettings;

class StrategiesComparatorTest {

  @Test
  void ticTacToeRandom() {

    StrategiesComparator strategiesComparator = new StrategiesComparator(TicTacToeService.INSTANCE);

    Strategy<TicTacToe> strategyOne = new RandomStrategy<>(TicTacToeService.INSTANCE);

    MonteCarloTreeSearchSettings<TicTacToe> settings =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(400_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearch = new MonteCarloTreeSearch(settings);
    monteCarloTreeSearch.init();

    int n = 100_000;
    // Warmup.
    for (int i = 0; i < n; i++) {
      monteCarloTreeSearch.expand();
    }

    MonteCarloTreeSearchStrategy<TicTacToe> strategyTwo =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearch);

    strategiesComparator.candidateIsBetter(strategyOne, strategyTwo);
  }

  @Test
  void ticTacToeTensorflow() {

    StrategiesComparator strategiesComparator = new StrategiesComparator(TicTacToeService.INSTANCE);

    MonteCarloTreeSearchSettings<TicTacToe> settingsUniform =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(400_000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearchUniform = new MonteCarloTreeSearch(settingsUniform);
    monteCarloTreeSearchUniform.init();

    Strategy<TicTacToe> strategyOne =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearchUniform);

    MonteCarloTreeSearchSettings<TicTacToe> settingsTensorflow =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(400000)
            .setGameService(() -> TicTacToeService.INSTANCE)
            .setEvaluator(
                () ->
                    new GameOverEvaluator<>(
                        new TensorflowEvaluator<>(
                            TensorflowEvaluator.TIC_TAC_TOE_V3, TicTacToeService.INSTANCE)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearchTensorflow =
        new MonteCarloTreeSearch(settingsTensorflow);
    monteCarloTreeSearchTensorflow.init();

    //    int n = 100_000;
    //
    //    // Warmup.
    //    for (int i = 0; i < n; i++) {
    //      monteCarloTreeSearchUniform.expand();
    //      monteCarloTreeSearchTensorflow.expand();
    //    }

    MonteCarloTreeSearchStrategy<TicTacToe> strategyTwo =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearchTensorflow);

    strategiesComparator.candidateIsBetter(strategyOne, strategyTwo);
  }

  @Test
  void connect4Compare() {

    StrategiesComparator strategiesComparator = new StrategiesComparator(Connect4Service.INSTANCE);

    MonteCarloTreeSearchSettings<Connect4> settingsUniform =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(4_000_000)
            .setGameService(() -> Connect4Service.INSTANCE)
            .setEvaluator(() -> new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(7)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearchUniform = new MonteCarloTreeSearch(settingsUniform);
    monteCarloTreeSearchUniform.init();

    Strategy<TicTacToe> strategyOne =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearchUniform);

    MonteCarloTreeSearchSettings<Connect4> settingsOnnx =
        MonteCarloTreeSearchSettings.withDefaults()
            .setNodesPoolCapacity(4_000_000)
            .setGameService(() -> Connect4Service.INSTANCE)
            .setEvaluator(
                () ->
                    new GameOverEvaluator<>(
                        new OnnxEvaluator<>(
                                OnnxEvaluator.CONNECT4_V1, Connect4Service.INSTANCE)))
            .build();

    MonteCarloTreeSearch monteCarloTreeSearchOnnx =
        new MonteCarloTreeSearch(settingsOnnx);
    monteCarloTreeSearchOnnx.init();

        int n = 10_000;

        // Warmup.
        for (int i = 0; i < n; i++) {
          monteCarloTreeSearchUniform.expand();
          monteCarloTreeSearchOnnx.expand();
        }

    MonteCarloTreeSearchStrategy<TicTacToe> strategyTwo =
        new MonteCarloTreeSearchStrategy<>(monteCarloTreeSearchOnnx);

    strategiesComparator.candidateIsBetter(strategyOne, strategyTwo);
  }
}
