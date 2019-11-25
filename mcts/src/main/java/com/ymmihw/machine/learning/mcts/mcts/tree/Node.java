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
  private List<Node> children;

  public Node() {
    this.state = new State();
    children = new ArrayList<>();
  }

  public Node(State state) {
    this.state = state;
    children = new ArrayList<>();
  }

  public Node(State state, Node parent, List<Node> children) {
    this.state = state;
    this.parent = parent;
    this.children = children;
  }

  public Node(Node node) {
    this.children = new ArrayList<>();
    this.state = new State(node.getState());
    if (node.getParent() != null)
      this.parent = node.getParent();
    List<Node> childArray = node.getChildren();
    for (Node child : childArray) {
      this.children.add(new Node(child));
    }
  }

  public Node getRandomChildNode() {
    int noOfPossibleMoves = this.children.size();
    int selectRandom = (int) (Math.random() * noOfPossibleMoves);
    return this.children.get(selectRandom);
  }

  public Node getChildWithMaxScore() {
    return Collections.max(this.children, Comparator.comparing(c -> {
      return c.getState().getVisitCount();
    }));
  }

}
