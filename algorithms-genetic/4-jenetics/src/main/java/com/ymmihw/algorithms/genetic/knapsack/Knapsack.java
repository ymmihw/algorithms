package com.ymmihw.algorithms.genetic.knapsack;

import java.util.function.Function;
import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;

public class Knapsack implements Function<Genotype<BitGene>, Double> {
  private KnapsackItem[] items;
  private double size;

  public Knapsack(KnapsackItem[] items, double size) {
    this.items = items;
    this.size = size;
  }

  @Override
  public Double apply(Genotype<BitGene> gt) {
    KnapsackItem sum = ((BitChromosome) gt.getChromosome()).ones().mapToObj(i -> items[i])
        .collect(KnapsackItem.toSum());
    return sum.size <= this.size ? sum.value : 0;
  }
}
