package com.ymmihw.machine.learning.mcts.mcts.montecarlo;

import static java.util.stream.Collectors.toList;
import java.util.List;
import com.ymmihw.machine.learning.mcts.mcts.tictactoe.Board;
import com.ymmihw.machine.learning.mcts.mcts.tictactoe.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class State {
  private Board board;
  private int player;
  private int visitCount;
  private double winScore;

  public State() {
    board = new Board();
  }

  public State(State state) {
    this.board = new Board(state.getBoard());
    this.player = state.getPlayer();
    this.visitCount = state.getVisitCount();
    this.winScore = state.getWinScore();
  }

  int getOpponent() {
    return board.getOpponent(player);
  }

  public List<State> getAllPossibleStates() {
    List<State> possibleStates = this.board.getEmptyPositions().stream().map(e -> {
      State newState = new State(this);
      newState.setPlayer(getOpponent());
      newState.getBoard().performMove(newState.getPlayer(), e);
      return newState;
    }).collect(toList());
    return possibleStates;
  }

  void incrementVisit() {
    this.visitCount++;
  }

  void addScore(double score) {
    if (this.winScore != Integer.MIN_VALUE)
      this.winScore += score;
  }

  void randomPlay() {
    List<Position> availablePositions = this.board.getEmptyPositions();
    int totalPossibilities = availablePositions.size();
    int selectRandom = (int) (Math.random() * totalPossibilities);
    this.board.performMove(this.player, availablePositions.get(selectRandom));
  }

  void togglePlayer() {
    this.player = board.getOpponent(player);
  }
}
