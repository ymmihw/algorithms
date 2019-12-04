package com.ymmihw.algorithms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MinimaxUnitTest {
  private Tree gameTree;
  private MiniMax miniMax;

  @Before
  public void initMiniMaxUtility() {
    miniMax = new MiniMax();
  }

  @Test
  public void givenMiniMax_whenConstructTree_thenNotNullTree() {
    assertNull(gameTree);
    miniMax.constructTree(6);
    gameTree = miniMax.getTree();
    assertNotNull(gameTree);
  }

  @Test
  public void givenMiniMax_whenCheckWin_thenComputeOptimal() {
    miniMax.constructTree(6);
    boolean result = miniMax.checkWin();
    assertTrue(result);
    miniMax.constructTree(8);
    result = miniMax.checkWin();
    assertFalse(result);
  }
}
