package com.ymmihw.algorithms.genetic;

import org.junit.Assert;
import org.junit.Test;

public class AntColonyOptimizationLongRunningUnitTest {

  @Test
  public void testGenerateRandomMatrix() {
    AntColonyOptimization antTSP = new AntColonyOptimization(5);
    Assert.assertNotNull(antTSP.generateRandomMatrix(5));
  }

  @Test
  public void testStartAntOptimization() {
    AntColonyOptimization antTSP = new AntColonyOptimization(5);
    Assert.assertNotNull(antTSP.solve());
  }

}
