package com.ymmihw.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class State {
  private final List<Stack<String>> stacks;
  private int heuristics;

  public State(List<Stack<String>> stacks) {
    this(stacks, 0);
  }

  public State(List<Stack<String>> stacks, int heuristics) {
    this.stacks = stacks;
    this.heuristics = heuristics;
  }

  public State(State state) {
    this.stacks = new ArrayList<>();
    for (Stack<String> s : state.getStacks()) {
      Stack<String> s1 = (Stack<String>) s.clone();
      this.stacks.add(s1);
    }
    this.heuristics = state.getHeuristics();
  }
}
