package com.ymmihw.algorithms.genetic.springsteen;

import java.util.stream.Collectors;
import org.junit.Test;
import io.jenetics.BitGene;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.ISeq;

public class SpringsteenProblemTest {
  @Test
  public void springsteenProblemTest() {
    double maxPricePerUniqueSong = 2.5;

    SpringsteenProblem springsteen = new SpringsteenProblem(ISeq.of(
        new SpringsteenRecord("SpringsteenRecord1", 25,
            ISeq.of("Song1", "Song2", "Song3", "Song4", "Song5", "Song6")),
        new SpringsteenRecord("SpringsteenRecord2", 15,
            ISeq.of("Song2", "Song3", "Song4", "Song5", "Song6", "Song7")),
        new SpringsteenRecord("SpringsteenRecord3", 35,
            ISeq.of("Song5", "Song6", "Song7", "Song8", "Song9", "Song10")),
        new SpringsteenRecord("SpringsteenRecord4", 17,
            ISeq.of("Song9", "Song10", "Song12", "Song4", "Song13", "Song14")),
        new SpringsteenRecord("SpringsteenRecord5", 29,
            ISeq.of("Song1", "Song2", "Song13", "Song14", "Song15", "Song16")),
        new SpringsteenRecord("SpringsteenRecord6", 5,
            ISeq.of("Song18", "Song20", "Song30", "Song40"))),
        maxPricePerUniqueSong);

    Engine<BitGene, Double> engine = Engine.builder(springsteen).build();

    ISeq<SpringsteenRecord> result = springsteen.codec().decoder()
        .apply(engine.stream().limit(10).collect(EvolutionResult.toBestGenotype()));

    double cost = result.stream().mapToDouble(r -> r.price).sum();

    int uniqueSongCount =
        result.stream().flatMap(r -> r.songs.stream()).collect(Collectors.toSet()).size();

    double pricePerUniqueSong = cost / uniqueSongCount;

    System.out.println("Overall cost:  " + cost);
    System.out.println("Unique songs:  " + uniqueSongCount);
    System.out.println("Cost per song: " + pricePerUniqueSong);
    System.out.println("Records:       " + result.map(r -> r.name).toString(", "));

  }

}
