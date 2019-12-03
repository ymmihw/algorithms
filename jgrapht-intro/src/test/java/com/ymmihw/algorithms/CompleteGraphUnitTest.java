package com.ymmihw.algorithms;

import static org.junit.Assert.assertEquals;
import java.util.function.Supplier;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.TwoApproxMetricTSP;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.util.SupplierUtil;
import org.junit.Before;
import org.junit.Test;

public class CompleteGraphUnitTest {

  static SimpleWeightedGraph<String, DefaultEdge> completeGraph;
  static int size = 10;

  @Before
  public void createCompleteGraph() {
    Supplier<String> vSupplier = new Supplier<String>() {
      private int id = 0;

      @Override
      public String get() {
        return "v" + id++;
      }
    };
    completeGraph = new SimpleWeightedGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier());
    CompleteGraphGenerator<String, DefaultEdge> completeGenerator =
        new CompleteGraphGenerator<String, DefaultEdge>(size);
    completeGenerator.generateGraph(completeGraph);
  }

  @Test
  public void givenCompleteGraph_whenGetHamiltonianCyclePath_thenGetVerticeListInSequence() {
    GraphPath<String, DefaultEdge> graphPath =
        new TwoApproxMetricTSP<String, DefaultEdge>().getTour(completeGraph);
    assertEquals(graphPath.getVertexList().size(), completeGraph.vertexSet().size() + 1);
  }
}
