package org.reinforce4j.montecarlo;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.reinforce4j.games.TicTacToe;
import org.reinforce4j.games.TicTacToeService;

class StateNodeServiceTest {

  @Test
  void acquireAndRelease() {
    TicTacToeService ticTacToeService = new TicTacToeService();
    StateNodeService service =
        new StateNodeService<>(
            ticTacToeService, new ZeroValueUniformEvaluator<>(ticTacToeService.numMoves()), 100, 1);
    service.init();
    assertThat(service.getCapacity()).isEqualTo(100);
    assertThat(service.getUsage()).isEqualTo(0);
    StateNode<TicTacToe> node1 = service.acquire(ticTacToeService.initialState());
    node1.state().move(1);
    assertThat(service.getCapacity()).isEqualTo(100);
    assertThat(service.getUsage()).isWithin(0.00001).of(0.01);
    assertThat(node1.state().isMoveAllowed(1)).isEqualTo(false);

    StateNode<TicTacToe> node2 = service.acquire(ticTacToeService.initialState());
    node2.state().move(5);
    assertThat(service.getCapacity()).isEqualTo(100);
    assertThat(service.getUsage()).isWithin(0.00001).of(0.02);
    assertThat(node2.state().isMoveAllowed(5)).isEqualTo(false);

    service.release(node1);
    assertThat(node2.state().isMoveAllowed(5)).isEqualTo(false);
    assertThat(service.getCapacity()).isEqualTo(100);
    assertThat(service.getUsage()).isWithin(0.00001).of(0.01);
  }

  @Test
  public void traverseAndPrune() {
    TicTacToeService ticTacToeService = new TicTacToeService();
    StateNodeService service =
        new StateNodeService<>(
            ticTacToeService, new ZeroValueUniformEvaluator<>(ticTacToeService.numMoves()), 100, 1);
    service.init();

    assertThat(service.getCapacity()).isEqualTo(100);
    assertThat(service.getUsage()).isEqualTo(0);
    StateNode root = service.newRoot();

    assertThat(root.isLeaf()).isTrue();
    service.initChildren(root);
    assertThat(root.isLeaf()).isFalse();
    for (int i = 0; i < 9; i++) {
      assertThat(root.getChildStates()[i]).isNotNull();
      assertThat(root.getChildStates()[i].isLeaf()).isTrue();
    }

    StateNode<TicTacToe> childNode = root.getChildStates()[4];
    service.initChildren(childNode);
    for (int i = 0; i < 9; i++) {
      if(i == 4){
        assertThat(childNode.getChildStates()[i]).isNull();
      }else{
        assertThat(childNode.getChildStates()[i]).isNotNull();
        assertThat(childNode.getChildStates()[i].isLeaf()).isTrue();
      }
    }

    assertThat(service.getUsage()).isWithin(0.00001).of(0.18);
    service.traverseAndPrune(root, 10);
    assertThat(service.getUsage()).isWithin(0.00001).of(0.01);
    assertThat(root.isLeaf()).isTrue();
  }
}
