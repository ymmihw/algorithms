package com.ymmihw.machine.learning.mcts.mcts.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tree {
  private Node root;

  public Tree() {
    root = new Node();
  }

  public void addChild(Node parent, Node child) {
    parent.getChildren().add(child);
  }

}
