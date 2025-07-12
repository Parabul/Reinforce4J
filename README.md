# Reinforce4J: A Reinforcement Learning Framework for Board Games

## About The Project

Reinforce4J is a Java-based framework for building and training reinforcement learning agents to play classic two-player board games. This project provides a complete end-to-end implementation of a self-play training pipeline inspired by DeepMind's AlphaZero. It uses a Monte Carlo Tree Search (MCTS) algorithm to explore game states and generate training data for a neural network, which then acts as a powerful evaluator to guide the search. The framework is designed to be modular and extensible, allowing developers to easily add new games and experiment with different reinforcement learning models.

-----

## Key Features

* **Complete Reinforcement Learning Pipeline**: Implements a full self-play and training loop.
* **Monte Carlo Tree Search (MCTS)**: Sophisticated game tree exploration for intelligent move selection.
* **Neural Network Integration**: Supports both **TensorFlow** and **ONNX** for model evaluation, allowing for flexibility in model deployment.
* **Extensible Game Framework**: Easily add new games by implementing a simple `GameState` interface. Comes with implementations for **Tic-Tac-Toe** and **Connect Four**.
* **Modular and Configurable**: Uses Guice for dependency injection, making the components highly decoupled and easy to configure.
* **Comprehensive Testing**: Includes a robust suite of unit tests to ensure code quality and correctness.

## Technology Stack

* **Core Framework**: Java
* **Deep Learning**: TensorFlow, ONNX Runtime
* **Dependency Injection**: Google Guice
* **Build Tool**: Maven
* **Testing**: JUnit 5, Google Truth

-----

## Project Structure

The codebase is organized into the following main packages:

* `core`: Contains core abstractions like `GameState`, `Player`, and `Outcomes`.
* `games`: Implementations of specific games like `TicTacToe` and `Connect4`.
* `evaluation`: Provides different model evaluators, including `TensorflowEvaluator` and `OnnxEvaluator`.
* `montecarlo`: The core MCTS implementation, including `TreeNode`, `TreeSearch`, and expansion strategies.
* `playing`: Includes game simulation logic (`PlayOutSimulator`) and strategies for move selection.
* `learning`: Contains the reinforcement learning pipelines and model training execution logic.
* `resources`: Holds the Python scripts for training the neural network models.

-----

## Contributing

Contributions are welcome\! If you'd like to contribute, please fork the repository and create a pull request. You can also open an issue with the tag "enhancement".

Possible areas for contribution include:

* Adding new games.
* Implementing more sophisticated MCTS strategies.
* Improving the neural network models.
* Adding support for other deep learning frameworks.