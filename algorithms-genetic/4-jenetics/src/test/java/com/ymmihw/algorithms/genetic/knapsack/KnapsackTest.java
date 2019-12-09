package com.ymmihw.algorithms.genetic.knapsack;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Mutator;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.TournamentSelector;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;

public class KnapsackTest {

  @Test
  public void knapsackTest() {
    int nItems = 15;
    double ksSize = nItems * 100.0 / 3.0;

    Knapsack knapsack =
        new Knapsack(Stream.generate(Item::random).limit(nItems).toArray(Item[]::new), ksSize);

    Engine<BitGene, Double> engine = Engine.builder(knapsack, BitChromosome.of(nItems, 0.5))
        .populationSize(500).survivorsSelector(new TournamentSelector<>(5))
        .offspringSelector(new RouletteWheelSelector<>())
        .alterers(new Mutator<>(0.115), new SinglePointCrossover<>(0.16)).build();

    EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

    Phenotype<BitGene, Double> best = engine.stream().limit(bySteadyFitness(7)).limit(100)
        .peek(statistics).collect(toBestPhenotype());

    System.out.println(statistics);
    System.out.println(best);
  }
}
