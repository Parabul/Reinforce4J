package org.reinforce4j.learning.evaluation;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.core.StateNode;
import org.reinforce4j.core.StateNodeService;
import org.reinforce4j.evaluation.GameOverEvaluator;
import org.reinforce4j.evaluation.TensorflowEvaluator;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

public class TensorflowEvaluatorTest {

  private static final float TOLERANCE = 0.0001f;

  @Test
  public void shouldEvaluateCorrectly() {

    StateNodeService<TicTacToe> stateNodeService =
        new StateNodeService(
            new TicTacToeService(),
            new GameOverEvaluator<>(new ZeroValueUniformEvaluator<>(9)),
            10000,
            30);

    stateNodeService.init();

    TensorflowEvaluator tensorflowBatchEvaluator =
        new TensorflowEvaluator(
            TensorflowEvaluator.TIC_TAC_TOE_V1, stateNodeService.getGameService());

    TicTacToe game1 = stateNodeService.getGameService().newInitialState();
    TicTacToe game2 = stateNodeService.getGameService().newInitialState();
    game2.move(4);
    TicTacToe game3 = stateNodeService.getGameService().newInitialState();
    game3.move(4);
    game3.move(1);

    StateNode<TicTacToe> node1 = stateNodeService.create(game1);
    StateNode<TicTacToe> node2 = stateNodeService.create(game2);
    StateNode<TicTacToe> node3 = stateNodeService.create(game3);

    tensorflowBatchEvaluator.evaluate(node1, node2, node3);

    assertThat(node1.getAverageValue().getValue(node1.getState().getCurrentPlayer()))
        .isWithin(TOLERANCE)
        .of(0.3031689f);
    assertThat(node1.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.11732529f,
            0.10184305f,
            0.11379578f,
            0.102521166f,
            0.12674625f,
            0.101940125f,
            0.1172647f,
            0.10257506f,
            0.11598862f)
        .inOrder();

    assertThat(node2.getAverageValue().getValue(node2.getState().getCurrentPlayer()))
        .isWithin(TOLERANCE)
        .of(-0.48276043f);
    assertThat(node2.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.15116668f,
            0.10973671f,
            0.13573483f,
            0.10371558f,
            3.211865E-9f,
            0.10486766f,
            0.13961151f,
            0.11444233f,
            0.14072475f)
        .inOrder();

    assertThat(node3.getAverageValue().getValue(node3.getState().getCurrentPlayer()))
        .isWithin(TOLERANCE)
        .of(0.539513f);
    assertThat(node3.getPolicy())
        .usingTolerance(TOLERANCE)
        .containsExactly(
            0.15158418f,
            1.4859517E-12f,
            0.16057102f,
            0.14095221f,
            1.7542738E-10f,
            0.14514937f,
            0.13938312f,
            0.11960253f,
            0.14275756f)
        .inOrder();

    //
    //    StateEvaluation eval3 =
    //        new StateEvaluation(
    //            0.539513f,
    //            new float[] {
    //              0.15158418f,
    //              1.4859517E-12f,
    //              0.16057102f,
    //              0.14095221f,
    //              1.7542738E-10f,
    //              0.14514937f,
    //              0.13938312f,
    //              0.11960253f,
    //              0.14275756f
    //            });
    //
    //    Map<GameState, StateEvaluation> evaluations =
    //        tensorflowBatchEvaluator.evaluate(ImmutableList.of(game1, game2, game3));
    //    assertThat(evaluations).hasSize(3);
    //    assertThat(evaluations.keySet()).containsExactly(game1, game2, game3);
    //
    //    assertThat(evaluations.get(game1).getValue()).isWithin(TOLERANCE).of(eval1.getValue());
    //    assertThat(evaluations.get(game1).getMoveValues())
    //        .usingTolerance(TOLERANCE)
    //        .containsExactly(eval1.getMoveValues())
    //        .inOrder();
    //
    //    assertThat(evaluations.get(game2).getValue()).isWithin(TOLERANCE).of(eval2.getValue());
    //    assertThat(evaluations.get(game2).getMoveValues())
    //        .usingTolerance(TOLERANCE)
    //        .containsExactly(eval2.getMoveValues())
    //        .inOrder();
    //
    //    assertThat(evaluations.get(game3).getValue()).isWithin(TOLERANCE).of(eval3.getValue());
    //    assertThat(evaluations.get(game3).getMoveValues())
    //        .usingTolerance(TOLERANCE)
    //        .containsExactly(eval3.getMoveValues())
    //        .inOrder();
  }
  //
  //  @Test
  //  public void shouldEvaluateCorrectlyRootState() {
  //
  //    TensorflowEvaluator tensorflowBatchEvaluator =
  //        new TensorflowEvaluator(
  //            TensorflowEvaluator.TIC_TAC_TOE_V1, stateNodeService.getGameService());
  //
  //    GameState root = stateNodeService.getGameService().newInitialState();
  //
  //    StateEvaluation rootEval =
  //        new StateEvaluation(
  //            0.3031689f,
  //            new float[] {
  //              0.11732529f,
  //              0.10184305f,
  //              0.11379578f,
  //              0.102521166f,
  //              0.12674625f,
  //              0.101940125f,
  //              0.1172647f,
  //              0.10257506f,
  //              0.11598862f
  //            });
  //
  //    Map<GameState, StateEvaluation> evaluations =
  //        tensorflowBatchEvaluator.evaluate(ImmutableList.of(root));
  //    assertThat(evaluations).hasSize(1);
  //
  //    assertThat(evaluations.get(root).getValue()).isWithin(TOLERANCE).of(rootEval.getValue());
  //    assertThat(evaluations.get(root).getMoveValues())
  //        .usingTolerance(TOLERANCE)
  //        .containsExactly(rootEval.getMoveValues())
  //        .inOrder();
  //  }
  //
  //  @Test
  //  public void buffers() {
  //    float[] src = new float[] {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f};
  //
  //    Tensor t = TFloat32.tensorOf(NdArrays.wrap(Shape.of(3, 2), DataBuffers.of(src)));
  //
  //    System.out.println(t.asRawTensor().data().asFloats().getFloat(0));
  //
  //    src[0] = 0.7f;
  //
  //    System.out.println(t.asRawTensor().data().asFloats().getFloat(0));
  //  }
}
