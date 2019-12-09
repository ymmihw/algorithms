package com.ymmihw.algorithms.genetic.knapsack;

import java.util.Random;
import java.util.stream.Collector;
import io.jenetics.util.RandomRegistry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Item {
  public final double size;
  public final double value;

  protected static Item random() {
    Random r = RandomRegistry.getRandom();
    return new Item(r.nextDouble() * 100, r.nextDouble() * 100);
  }

  protected static Collector<Item, ?, Item> toSum() {
    return Collector.of(() -> new double[2], (a, b) -> {
      a[0] += b.size;
      a[1] += b.value;
    }, (a, b) -> {
      a[0] += b[0];
      a[1] += b[1];
      return a;
    }, r -> new Item(r[0], r[1]));
  }

}
