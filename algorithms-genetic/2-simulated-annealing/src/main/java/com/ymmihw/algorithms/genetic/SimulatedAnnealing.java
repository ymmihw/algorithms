package com.ymmihw.algorithms.genetic;

public class SimulatedAnnealing {

  private static Travel travel = new Travel(10);

  public static double simulateAnnealing(double startingTemperature, int numberOfIterations,
      double coolingRate) {
    System.out.println("Starting SA with temperature: " + startingTemperature
        + ", # of iterations: " + numberOfIterations + " and colling rate: " + coolingRate);
    double t = startingTemperature;
    travel.generateInitialTravel();
    double bestDistance = travel.getDistance();
    System.out.println("Initial distance of travel: " + bestDistance);
    Travel bestSolution = travel;
    Travel currentSolution = bestSolution;

    for (int i = 0; i < numberOfIterations && t > 0.1; i++) {
      currentSolution.swapCities();
      double currentDistance = currentSolution.getDistance();
      if (currentDistance < bestDistance) {
        bestDistance = currentDistance;
      } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()) {
        currentSolution.revertSwap();
      }
      t *= coolingRate;
      System.out.println("Iteration #" + i + " bestDistance is " + bestDistance);
    }
    return bestDistance;
  }

}
