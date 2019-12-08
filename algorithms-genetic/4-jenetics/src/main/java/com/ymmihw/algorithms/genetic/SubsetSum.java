package com.ymmihw.algorithms.genetic;

import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import io.jenetics.EnumGene;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

public class SubsetSum implements Problem<ISeq<Integer>, EnumGene<Integer>, Integer> {

  private ISeq<Integer> basicSet;
  private int size;

  public SubsetSum(ISeq<Integer> basicSet, int size) {
    this.basicSet = requireNonNull(basicSet);
    this.size = size;
  }

  @Override
  public Function<ISeq<Integer>, Integer> fitness() {
    return subset -> Math.abs(subset.stream().mapToInt(Integer::intValue).sum());
  }

  @Override
  public Codec<ISeq<Integer>, EnumGene<Integer>> codec() {
    return Codecs.ofSubSet(basicSet, size);
  }

}
