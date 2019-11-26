package com.ymmihw.algorithms.mcts.montecarlo;

import java.util.Collections;
import java.util.Comparator;
import com.ymmihw.algorithms.mcts.tree.Node;

public class UCT {

  private static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
    if (nodeVisit == 0) {
      return Integer.MAX_VALUE;
    }
    return (nodeWinScore / nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / nodeVisit);
  }

  public static Node findBestNodeWithUCT(Node node) {
    int parentVisit = node.getState().getVisitCount();
    return Collections.max(node.getChildren(), Comparator.comparing(
        c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
  }
}
