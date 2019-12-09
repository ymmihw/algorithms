package com.ymmihw.algorithms.genetic.knapsack;

import java.util.function.Function;
import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;

public class Knapsack implements Function<Genotype<BitGene>, Double> {
  private Item[] items;
  private double size;

  public Knapsack(Item[] items, double size) {
    this.items = items;
    this.size = size;
  }

  @Override
  public Double apply(Genotype<BitGene> gt) {
    Item sum = ((BitChromosome) gt.getChromosome()).ones().mapToObj(i -> items[i])
        .collect(Item.toSum());
    return sum.size <= this.size ? sum.value : 0;
  }
}
