package com.ymmihw.machine.learning.mcts.mcts.montecarlo;

import java.util.List;
import com.ymmihw.machine.learning.mcts.mcts.tictactoe.Board;
import com.ymmihw.machine.learning.mcts.mcts.tree.Node;
import com.ymmihw.machine.learning.mcts.mcts.tree.Tree;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonteCarloTreeSearch {

  private static final int WIN_SCORE = 10;
  private int level;

  public MonteCarloTreeSearch() {
    this.level = 3;
  }

  private int getMillisForCurrentLevel() {
    return 2 * (this.level - 1) + 1;
  }

  public Board findNextMove(Board board, int playerNo) {
    long start = System.currentTimeMillis();
    long end = start + 60 * getMillisForCurrentLevel();

    int opponent = 3 - playerNo;
    Tree tree = new Tree();
    Node rootNode = tree.getRoot();
    rootNode.getState().setBoard(board);
    rootNode.getState().setPlayerNo(opponent);

    while (System.currentTimeMillis() < end) {
      // Phase 1 - Selection
      Node promisingNode = selectPromisingNode(rootNode);
      // Phase 2 - Expansion
      if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS)
        expandNode(promisingNode);

      // Phase 3 - Simulation
      Node nodeToExplore = promisingNode;
      if (promisingNode.getChildArray().size() > 0) {
        nodeToExplore = promisingNode.getRandomChildNode();
      }
      int playoutResult = simulateRandomPlayout(nodeToExplore, opponent);
      // Phase 4 - Update
      backPropogation(nodeToExplore, playoutResult);
    }

    Node winnerNode = rootNode.getChildWithMaxScore();
    tree.setRoot(winnerNode);
    return winnerNode.getState().getBoard();
  }

  private Node selectPromisingNode(Node rootNode) {
    Node node = rootNode;
    while (node.getChildArray().size() != 0) {
      node = UCT.findBestNodeWithUCT(node);
    }
    return node;
  }

  private void expandNode(Node node) {
    List<State> possibleStates = node.getState().getAllPossibleStates();
    possibleStates.forEach(state -> {
      Node newNode = new Node(state);
      newNode.setParent(node);
      newNode.getState().setPlayerNo(node.getState().getOpponent());
      node.getChildArray().add(newNode);
    });
  }

  private void backPropogation(Node nodeToExplore, int playerNo) {
    Node tempNode = nodeToExplore;
    while (tempNode != null) {
      tempNode.getState().incrementVisit();
      if (tempNode.getState().getPlayerNo() == playerNo)
        tempNode.getState().addScore(WIN_SCORE);
      tempNode = tempNode.getParent();
    }
  }

  private int simulateRandomPlayout(Node node, int opponent) {
    Node tempNode = new Node(node);
    State tempState = tempNode.getState();
    int boardStatus = tempState.getBoard().checkStatus();

    if (boardStatus == opponent) {
      tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
      return boardStatus;
    }
    while (boardStatus == Board.IN_PROGRESS) {
      tempState.togglePlayer();
      tempState.randomPlay();
      boardStatus = tempState.getBoard().checkStatus();
    }

    return boardStatus;
  }

}
