package com.ymmihw.machine.learning.mcts.mcts.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.ymmihw.machine.learning.mcts.mcts.montecarlo.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
  private State state;
  private Node parent;
  private List<Node> childArray;

  public Node() {
    this.state = new State();
    childArray = new ArrayList<>();
  }

  public Node(State state) {
    this.state = state;
    childArray = new ArrayList<>();
  }

  public Node(State state, Node parent, List<Node> childArray) {
    this.state = state;
    this.parent = parent;
    this.childArray = childArray;
  }

  public Node(Node node) {
    this.childArray = new ArrayList<>();
    this.state = new State(node.getState());
    if (node.getParent() != null)
      this.parent = node.getParent();
    List<Node> childArray = node.getChildArray();
    for (Node child : childArray) {
      this.childArray.add(new Node(child));
    }
  }

  public Node getRandomChildNode() {
    int noOfPossibleMoves = this.childArray.size();
    int selectRandom = (int) (Math.random() * noOfPossibleMoves);
    return this.childArray.get(selectRandom);
  }

  public Node getChildWithMaxScore() {
    return Collections.max(this.childArray, Comparator.comparing(c -> {
      return c.getState().getVisitCount();
    }));
  }

}
