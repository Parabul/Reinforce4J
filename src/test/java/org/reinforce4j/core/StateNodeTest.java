package org.reinforce4j.core;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TextFormat;
import org.reinforce4j.evaluation.ZeroValueUniformEvaluator;
import org.junit.jupiter.api.Test;
import org.tensorflow.example.Example;

class StateNodeTest {

//  @Test
//  void shouldCreateNodeForRootState() {
//    GameService<T> factory = GameRegistry.TIC_TAC_TOE;
//    GameState root = factory.initialState();
//    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(factory);
//    StateNode stateNode = new StateNode(root, evaluator.evaluate(root));
//
//    assertThat(stateNode.getState()).isEqualTo(root);
//    assertThat(stateNode.getAverageValue()).isEqualTo(new AverageValue(0.0f, Player.ONE));
//    assertThat(stateNode.getChildStates()).hasLength(9);
//    for (int i = 0; i < 9; i++) {
//      assertThat(stateNode.getChildStates()[i]).isNull();
//    }
//
//    assertThat(stateNode.isLeaf()).isTrue();
//    IllegalStateException e =
//        assertThrows(
//            IllegalStateException.class, () -> stateNode.toExample(GameRegistry.TIC_TAC_TOE));
//    assertThat(e).hasMessageThat().contains("Leaf nodes have no moves");
//  }
//
//  @Test
//  void shouldInitChildrenOnce() {
//    GameService<T> factory = GameRegistry.TIC_TAC_TOE;
//    GameState root = factory.initialState();
//    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(factory);
//    StateNode stateNode = new StateNode(root, evaluator.evaluate(root));
//
//    assertThat(stateNode.initChildren(factory, evaluator)).isTrue();
//    for (int i = 0; i < 9; i++) {
//      assertThat(stateNode.getChildStates()[i]).isNotNull();
//    }
//
//    assertThat(stateNode.initChildren(factory, evaluator)).isFalse();
//    assertThat(stateNode.initChildren(factory, evaluator)).isFalse();
//  }
//
//  @Test
//  void shouldUpdateNode() throws InvalidProtocolBufferException, TextFormat.ParseException {
//    GameService<T> factory = GameRegistry.TIC_TAC_TOE;
//    GameState root = factory.initialState();
//    ZeroValueUniformEvaluator evaluator = new ZeroValueUniformEvaluator(factory);
//    StateNode stateNode = new StateNode(root, evaluator.evaluate(root));
//
//    assertThat(stateNode.initChildren(factory, evaluator)).isTrue();
//
//    assertThat(stateNode.isLeaf()).isFalse();
//    assertThat(stateNode.getState()).isEqualTo(root);
//
//    stateNode.update(Player.ONE, new AverageValue(1, Player.ONE));
//    stateNode.update(Player.ONE, new AverageValue(1, Player.ONE));
//    stateNode.update(Player.NONE, AverageValue.ZERO.get());
//
//    assertThat(stateNode.getVisits()).isEqualTo(4);
//    assertThat(stateNode.getAverageValue().getValue(Player.ONE)).isWithin(0.01f).of(0.66f);
//
//    assertThat(stateNode.toExample(GameRegistry.TIC_TAC_TOE))
//        .isEqualTo(
//            TextFormat.parse(
//                "features {"
//                    + "  feature {"
//                    + "    key: \"input\""
//                    + "    value {"
//                    + "      float_list {"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "      }"
//                    + "    }"
//                    + "  }"
//                    + "  feature {"
//                    + "    key: \"output\""
//                    + "    value {"
//                    + "      float_list {"
//                    + "        value: 0.6666666"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "        value: 0.0"
//                    + "      }"
//                    + "    }"
//                    + "  }"
//                    + "}",
//                Example.class));
//  }
}
