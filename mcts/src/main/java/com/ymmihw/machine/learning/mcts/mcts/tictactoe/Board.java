package com.ymmihw.machine.learning.mcts.mcts.tictactoe;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
  private final int[][] boardValues;
  private final int boardSize;
  private final int winLength;
  private int winner = 0;
  private int totalMoves;

  private static final int DEFAULT_BOARD_SIZE = 3;
  private static final int DEFAULT_WIN_LENGTH = 3;

  public static final int IN_PROGRESS = -1;
  public static final int DRAW = 0;
  public static final int P1 = 1;
  public static final int P2 = 2;

  public Board() {
    this(DEFAULT_BOARD_SIZE, DEFAULT_WIN_LENGTH);
  }

  public Board(int boardSize, int winLength) {
    this.boardValues = new int[boardSize][boardSize];
    this.boardSize = boardSize;
    this.winLength = winLength;
  }

  public Board(Board board) {
    this(board.boardSize, board.getWinLength());
    this.winner = board.getWinner();
    int[][] boardValues = board.getBoardValues();
    for (int i = 0; i < boardValues.length; i++) {
      int m = boardValues[i].length;
      for (int j = 0; j < m; j++) {
        this.boardValues[i][j] = boardValues[i][j];
      }
    }
  }

  private void checkStatus(int player, Position p) {
    StringBuilder row = new StringBuilder();
    for (int i = 0; i < boardSize; i++) {
      row.append(boardValues[i][p.getY()]);
    }

    if (findWinner(row, player)) {
      return;
    }

    StringBuilder col = new StringBuilder();
    for (int i = 0; i < boardSize; i++) {
      col.append(boardValues[p.getX()][i]);
    }
    if (findWinner(col, player)) {
      return;
    }
    StringBuilder diag1 = new StringBuilder();
    diag1.append(player);
    for (int i = 1; p.getX() - i >= 0 && p.getY() - i >= 0; i++) {
      diag1.insert(0, boardValues[p.getX() - i][p.getY() - i]);
    }
    for (int i = 1; p.getX() + i < boardSize && p.getY() + i < boardSize; i++) {
      diag1.append(boardValues[p.getX() + i][p.getY() + i]);
    }

    if (findWinner(diag1, player)) {
      return;
    }
    StringBuilder diag2 = new StringBuilder();
    diag2.append(player);
    for (int i = 1; p.getX() - i >= 0 && p.getY() + i < boardSize; i++) {
      diag2.insert(0, boardValues[p.getX() - i][p.getY() + i]);
    }
    for (int i = 1; p.getX() + i < boardSize && p.getY() - i >= 0; i++) {
      diag2.append(boardValues[p.getX() + i][p.getY() - i]);
    }
    if (findWinner(diag2, player)) {
      return;
    }
  }

  private boolean findWinner(StringBuilder sb, int player) {
    StringBuilder sample = new StringBuilder();
    for (int i = 0; i < winLength; i++) {
      sample.append(player);
    }

    if (sb.toString().contains(sample)) {
      this.winner = player;
      return true;
    }

    return false;
  }

  public void performMove(int player, Position p) {
    this.totalMoves++;
    this.boardValues[p.getX()][p.getY()] = player;
    checkStatus(player, p);
  }

  public int checkStatus() {
    if (this.winner != DRAW) {
      return this.winner;
    }
    if (getEmptyPositions().size() > 0)
      return IN_PROGRESS;
    else
      return DRAW;
  }

  public void printBoard() {
    int size = this.boardValues.length;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        System.out.print(boardValues[i][j] + " ");
      }
      System.out.println();
    }
  }

  public List<Position> getEmptyPositions() {
    int size = this.boardValues.length;
    List<Position> emptyPositions = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (boardValues[i][j] == 0)
          emptyPositions.add(new Position(i, j));
      }
    }
    return emptyPositions;
  }

  public void printStatus() {
    switch (this.checkStatus()) {
      case P1:
        System.out.println("Player 1 wins");
        break;
      case P2:
        System.out.println("Player 2 wins");
        break;
      case DRAW:
        System.out.println("Game Draw");
        break;
      case IN_PROGRESS:
        System.out.println("Game In Progress");
        break;
    }
  }

  public int getOpponent(int player) {
    return player == Board.P1 ? Board.P2 : Board.P1;
  }

  public boolean isInProgress() {
    return checkStatus() == IN_PROGRESS;
  }
}
