package com.ymmihw.algorithms.genetic.springsteen;

import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.jenetics.BitGene;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

public class SpringsteenProblem implements Problem<ISeq<SpringsteenRecord>, BitGene, Double> {

  private ISeq<SpringsteenRecord> records;
  private double maxPricePerUniqueSong;

  public SpringsteenProblem(ISeq<SpringsteenRecord> records, double maxPricePerUniqueSong) {
    this.records = requireNonNull(records);
    this.maxPricePerUniqueSong = maxPricePerUniqueSong;
  }

  @Override
  public Function<ISeq<SpringsteenRecord>, Double> fitness() {
    return SpringsteenRecords -> {
      double cost = SpringsteenRecords.stream().mapToDouble(r -> r.price).sum();

      int uniqueSongCount = SpringsteenRecords.stream().flatMap(r -> r.songs.stream())
          .collect(Collectors.toSet()).size();

      double pricePerUniqueSong = cost / uniqueSongCount;

      return pricePerUniqueSong <= maxPricePerUniqueSong ? uniqueSongCount : 0.0;
    };
  }

  @Override
  public Codec<ISeq<SpringsteenRecord>, BitGene> codec() {
    return Codecs.ofSubSet(records);
  }


}
