package com.ymmihw.algorithms;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
  private int noOfBones;
  private boolean isMaxPlayer;
  private int score;
  private List<Node> children;

  public Node(int noOfBones, boolean isMaxPlayer) {
    this.noOfBones = noOfBones;
    this.isMaxPlayer = isMaxPlayer;
    this.children = new ArrayList<>();
  }

  boolean isMaxPlayer() {
    return isMaxPlayer;
  }


  void addChild(Node newNode) {
    children.add(newNode);
  }
}
