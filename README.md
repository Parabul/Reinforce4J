# Reinforce4J

## Overview

Reinforce4j is a Java library designed for implementing reinforcement learning algorithms, with a primary focus on Monte Carlo Tree Search (MCTS). It provides tools and frameworks for building agents that can learn to play games, demonstrated with examples like Tic-Tac-Toe and Connect 4. The library supports integration with machine learning models, including TensorFlow and ONNX, for game state evaluation.

## Key Features

* **Monte Carlo Tree Search:** Robust implementation of MCTS for decision making in games.
* **Pluggable Evaluation:** Supports various game state evaluation methods, including:
    * Game outcome-based evaluation.
    * Simple uniform evaluators.
    * Integration with neural network models via TensorFlow and ONNX.
* **Game Implementations:** Ready-to-use implementations for Tic-Tac-Toe and Connect 4, serving as examples and starting points.
* **Reinforcement Learning Data Generation:** Tools to generate training examples (state, policy, value) from self-play simulations using MCTS.
* **Learning Pipelines:** Structured pipelines for orchestrating the reinforcement learning loop (self-play, training, evaluation).
* **TensorFlow Integration:** Utilities for working with TensorFlow models and TFRecord files, including Python scripts for training.

## How to Use / Getting Started

While specific setup and usage instructions would typically be more detailed, the general workflow for using `reinforce4j` would involve:

1.  **Define your Game:** Implement the `GameState` and `GameService` interfaces for the game you want the agent to learn.
2.  **Configure MCTS:** Set up `MonteCarloTreeSearch` with appropriate `MonteCarloTreeSearchSettings` and an `Evaluator`.
3.  **Playing Strategy:** Use `MonteCarloTreeSearchStrategy` to enable an agent to play the game.
4.  **(Optional) Training a Model:**
    * Use `ExampleGen` to generate training data by having the MCTS agent play against itself or other strategies.
    * Utilize the Python scripts in `src/main/resources/tensorflow` (or adapt them) to train a neural network model on the generated data.
    * Integrate the trained model back into `reinforce4j` using `TensorflowEvaluator` or `OnnxEvaluator`.
5.  **Iterate:** Use a `ReinforcementLearningPipeline` to automate the cycle of data generation, model training, and evaluation.

## Testing

The library includes a suite of unit tests located in the `src/test/java` directory, covering various components such as:
* Core MCTS logic (`MonteCarloTreeSearchTest`, `StateNodeTest`, `NodeSelectionStrategyTest`)
* Evaluators (`TensorflowEvaluatorTest`, `OnnxEvaluatorTest`, `ZeroValueUniformEvaluatorTest`)
* Game implementations (`TicTacToeTest`, `Connect4Test`)
* Training data generation (`ExampleGenTest`)
* Playing strategies (`MonteCarloTreeSearchStrategyTest`, `RandomStrategyTest`)
* Utilities (`TfRecordTest`)