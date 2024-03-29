package com.ymmihw.algorithms.genetic;

import org.junit.Test;
import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

public class SimpleGeneticAlgorithm {

  private static Integer eval(Genotype<BitGene> gt) {
    return gt.getChromosome().as(BitChromosome.class).bitCount();
  }

  @Test
  public void simpleGeneticAlgorithmTest() {
    Factory<Genotype<BitGene>> gtf = Genotype.of(BitChromosome.of(10, 0.5));
    System.out.println("Before the evolution:\n" + gtf);

    Engine<BitGene, Integer> engine = Engine.builder(SimpleGeneticAlgorithm::eval, gtf).build();

    Genotype<BitGene> result = engine.stream().limit(500).collect(EvolutionResult.toBestGenotype());

    System.out.println("After the evolution:\n" + result);

  }

}
