package com.ymmihw.machine.learning.mcts.mcts.montecarlo;

import java.util.ArrayList;
import java.util.List;
import com.ymmihw.machine.learning.mcts.mcts.tictactoe.Board;
import com.ymmihw.machine.learning.mcts.mcts.tictactoe.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class State {
  private Board board;
  private int playerNo;
  private int visitCount;
  private double winScore;

  public State() {
    board = new Board();
  }

  public State(State state) {
    this.board = new Board(state.getBoard());
    this.playerNo = state.getPlayerNo();
    this.visitCount = state.getVisitCount();
    this.winScore = state.getWinScore();
  }

  public State(Board board) {
    this.board = new Board(board);
  }

  int getOpponent() {
    return 3 - playerNo;
  }

  public List<State> getAllPossibleStates() {
    List<State> possibleStates = new ArrayList<>();
    List<Position> availablePositions = this.board.getEmptyPositions();
    availablePositions.forEach(p -> {
      State newState = new State(this.board);
      newState.setPlayerNo(3 - this.playerNo);
      newState.getBoard().performMove(newState.getPlayerNo(), p);
      possibleStates.add(newState);
    });
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
    this.board.performMove(this.playerNo, availablePositions.get(selectRandom));
  }

  void togglePlayer() {
    this.playerNo = 3 - this.playerNo;
  }
}
