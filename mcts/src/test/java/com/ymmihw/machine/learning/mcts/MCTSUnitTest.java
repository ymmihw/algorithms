package com.ymmihw.machine.learning.mcts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.Test;
import com.ymmihw.machine.learning.mcts.mcts.montecarlo.MonteCarloTreeSearch;
import com.ymmihw.machine.learning.mcts.mcts.montecarlo.State;
import com.ymmihw.machine.learning.mcts.mcts.montecarlo.UCT;
import com.ymmihw.machine.learning.mcts.mcts.tictactoe.Board;
import com.ymmihw.machine.learning.mcts.mcts.tictactoe.Position;
import com.ymmihw.machine.learning.mcts.mcts.tree.Tree;

public class MCTSUnitTest {

  @Test
  public void givenStats_whenGetUCTForNode_thenUCTMatchesWithManualData()
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
    Method method = UCT.class.getDeclaredMethod("uctValue", int.class, double.class, int.class);
    method.setAccessible(true);
    double uctValue = 15.79;
    double invoke = (double) method.invoke(null, 600, 300, 20);
    assertEquals(invoke, uctValue, 0.01);
  }

  @Test
  public void giveninitBoardState_whenGetAllPossibleStates_thenNonEmptyList() {
    Tree gameTree = new Tree();
    State initState = gameTree.getRoot().getState();
    List<State> possibleStates = initState.getAllPossibleStates();
    assertTrue(possibleStates.size() > 0);
  }

  @Test
  public void givenEmptyBoard_whenPerformMove_thenLessAvailablePossitions() {
    Board board = new Board();
    int initAvailablePositions = board.getEmptyPositions().size();
    board.performMove(Board.P1, new Position(1, 1));
    int availablePositions = board.getEmptyPositions().size();
    assertTrue(initAvailablePositions > availablePositions);
  }

  @Test
  public void givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw() {
    Board board = new Board();
    MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();

    int player = Board.P1;
    int totalMoves = board.getBoardSize() * board.getBoardSize();
    for (int i = 0; i < totalMoves; i++) {
      board = mcts.findNextMove(board, player);
      if (board.checkStatus() != -1) {
        break;
      }
      player = board.getOpponent(player);
    }
    int winStatus = board.checkStatus();
    assertEquals(winStatus, Board.DRAW);
  }

  @Test
  public void givenEmptyBoard_whenLevel1VsLevel3_thenLevel3WinsOrDraw() {
    Board board = new Board();
    MonteCarloTreeSearch mcts1 = new MonteCarloTreeSearch();
    mcts1.setLevel(1);
    MonteCarloTreeSearch mcts3 = new MonteCarloTreeSearch();
    mcts3.setLevel(3);

    int player = Board.P1;
    int totalMoves = board.getBoardSize() * board.getBoardSize();
    for (int i = 0; i < totalMoves; i++) {
      if (player == Board.P1) {
        board = mcts3.findNextMove(board, player);
      } else {
        board = mcts1.findNextMove(board, player);
      }

      player = board.getOpponent(player);

      board.printBoard();
      System.out.println("=============");
      if (board.checkStatus() != -1) {
        break;
      }
    }
    int winStatus = board.checkStatus();
    board.printStatus();
    assertTrue(winStatus == Board.DRAW || winStatus == Board.P1);
  }

}
