package com.ymmihw.algorithms.genetic;

import java.util.Random;
import org.junit.jupiter.api.Test;
import io.jenetics.EnumGene;
import io.jenetics.Mutator;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.Phenotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.prngine.LCG64ShiftRandom;
import io.jenetics.util.ISeq;

public class SubsetSumTest {

  public static SubsetSum of(int n, int k, Random random) {
    return new SubsetSum(
        random.doubles().limit(n).mapToObj(d -> (int) ((d - 0.5) * n)).collect(ISeq.toISeq()), k);
  }

  @Test
  public void subsetSumTest() {
    SubsetSum problem = of(500, 15, new LCG64ShiftRandom(101010));

    Engine<EnumGene<Integer>, Integer> engine =
        Engine.builder(problem).minimizing().maximalPhenotypeAge(5)
            .alterers(new PartiallyMatchedCrossover<>(0.4), new Mutator<>(0.3)).build();

    Phenotype<EnumGene<Integer>, Integer> result = engine.stream().limit(Limits.bySteadyFitness(55))
        .collect(EvolutionResult.toBestPhenotype());

    System.out.print(result);
  }

}
