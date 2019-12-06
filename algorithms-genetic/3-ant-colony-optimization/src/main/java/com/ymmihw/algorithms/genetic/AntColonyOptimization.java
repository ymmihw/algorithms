package com.ymmihw.algorithms.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;

public class AntColonyOptimization {

  private final double c = 1.0;
  private final double alpha = 1;
  private final double beta = 5;
  private final double evaporation = 0.5;
  private final double Q = 500;
  private final double antFactor = 0.8;
  private final double randomFactor = 0.01;

  private final int maxIterations = 200;

  private final int numberOfCities;
  private final int numberOfAnts;
  private final double graph[][];
  private final double trails[][];
  private final List<Ant> ants = new ArrayList<>();
  private final Random random = new Random();

  private int currentIndex;

  private int[] bestTourOrder;
  private double bestTourLength;

  public AntColonyOptimization(int noOfCities) {
    graph = generateRandomMatrix(noOfCities);
    printGraph();
    numberOfCities = graph.length;
    numberOfAnts = (int) (numberOfCities * antFactor);

    trails = new double[numberOfCities][numberOfCities];
    IntStream.range(0, numberOfAnts).forEach(i -> ants.add(new Ant(numberOfCities)));
  }

  private void printGraph() {
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph.length; j++) {
        System.out.print(graph[i][j] + " ");
      }
      System.out.println();
    }
  }

  /**
   * Generate initial solution
   */
  public double[][] generateRandomMatrix(int n) {
    double[][] randomMatrix = new double[n][n];
    IntStream.range(0, n).forEach(i -> IntStream.range(0, n)
        .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(100) + 1)));
    return randomMatrix;
  }

  /**
   * Perform ant optimization
   */
  public void startAntOptimization() {
    IntStream.rangeClosed(1, 3).forEach(i -> {
      System.out.println("Attempt #" + i);
      solve();
    });
  }

  /**
   * Use this method to run the main logic
   */
  public int[] solve() {
    clearTrails();
    for (int i = 0; i < maxIterations; i++) {
      setupAnts();
      moveAnts();
      updateTrails();
      updateBest();
    }
    printResult();
    return bestTourOrder.clone();
  }

  private void printResult() {
    System.out.println("Best tour length: " + bestTourLength);
    System.out.println("Best tour order: " + Arrays.toString(bestTourOrder));

    double length = 0;
    for (int i = 0; i < bestTourOrder.length - 1; i++) {
      System.out.print(graph[bestTourOrder[i]][bestTourOrder[i + 1]] + " ");
      length += graph[bestTourOrder[i]][bestTourOrder[i + 1]];
    }
    System.out.println(graph[bestTourOrder[bestTourOrder.length - 1]][bestTourOrder[0]]);

    System.out.println(length + graph[bestTourOrder[bestTourOrder.length - 1]][bestTourOrder[0]]);
  }

  /**
   * Prepare ants for the simulation
   */
  private void setupAnts() {
    for (Ant ant : ants) {
      ant.clear();
      ant.visitCity(-1, random.nextInt(numberOfCities));
    }
    currentIndex = 0;
  }

  /**
   * At each iteration, move ants
   */
  private void moveAnts() {
    for (int i = currentIndex; i < numberOfCities - 1; i++) {
      ants.forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
      currentIndex++;
    }
  }

  /**
   * Select next city for each ant
   */
  private int selectNextCity(Ant ant) {
    int t = random.nextInt(numberOfCities - currentIndex);
    if (random.nextDouble() < randomFactor) {
      OptionalInt cityIndex =
          IntStream.range(0, numberOfCities).filter(i -> i == t && !ant.visited(i)).findFirst();
      if (cityIndex.isPresent()) {
        return cityIndex.getAsInt();
      }
    }
    double[] probabilities = calculateProbabilities(ant);
    double r = random.nextDouble();
    double total = 0;
    for (int i = 0; i < numberOfCities; i++) {
      total += probabilities[i];
      if (total >= r) {
        return i;
      }
    }

    throw new RuntimeException("There are no other cities");
  }

  /**
   * Calculate the next city picks probabilites
   * 
   * @return
   */
  private double[] calculateProbabilities(Ant ant) {
    int i = ant.trail[currentIndex];
    double pheromone = 0.0;
    for (int l = 0; l < numberOfCities; l++) {
      if (!ant.visited(l)) {
        pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
      }
    }

    double[] probabilities = new double[numberOfCities];
    for (int j = 0; j < numberOfCities; j++) {
      if (ant.visited(j)) {
        probabilities[j] = 0.0;
      } else {
        double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
        probabilities[j] = numerator / pheromone;
      }
    }
    return probabilities;
  }

  /**
   * Update trails that ants used
   */
  private void updateTrails() {
    for (int i = 0; i < numberOfCities; i++) {
      for (int j = 0; j < numberOfCities; j++) {
        trails[i][j] *= evaporation;
      }
    }
    for (Ant ant : ants) {
      double contribution = Q / ant.trailLength(graph);
      for (int i = 0; i < numberOfCities - 1; i++) {
        trails[ant.trail[i]][ant.trail[i + 1]] += contribution;
      }
      trails[ant.trail[numberOfCities - 1]][ant.trail[0]] += contribution;
    }
  }

  /**
   * Update the best solution
   */
  private void updateBest() {
    if (bestTourOrder == null) {
      bestTourOrder = ants.get(0).trail;
      bestTourLength = ants.get(0).trailLength(graph);
    }
    for (Ant ant : ants) {
      if (ant.trailLength(graph) < bestTourLength) {
        bestTourLength = ant.trailLength(graph);
        bestTourOrder = ant.trail.clone();
      }
    }
  }

  /**
   * Clear trails after simulation
   */
  private void clearTrails() {
    for (int i = 0; i < numberOfCities; i++) {
      for (int j = 0; j < numberOfCities; j++) {
        trails[i][j] = c;
      }
    }
  }
}
