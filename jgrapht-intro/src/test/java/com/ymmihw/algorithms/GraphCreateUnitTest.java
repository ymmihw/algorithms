package com.ymmihw.algorithms;

import java.util.function.Supplier;
import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.util.SupplierUtil;
import org.junit.Test;

public class GraphCreateUnitTest {

  @Test
  public void createSimpleGraph() {
    Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
    g.addVertex("v1");
    g.addVertex("v2");
    g.addEdge("v1", "v2");
  }

  @Test
  public void createDirectedGraph() {
    Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    directedGraph.addVertex("v1");
    directedGraph.addVertex("v2");
    directedGraph.addVertex("v3");
    directedGraph.addEdge("v1", "v2");
  }

  @Test
  public void createCompleteGraph() {
    Supplier<String> vSupplier = new Supplier<String>() {
      private int id = 0;

      @Override
      public String get() {
        return "v" + id++;
      }
    };
    Graph<String, DefaultEdge> completeGraph =
        new SimpleWeightedGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier());
    CompleteGraphGenerator<String, DefaultEdge> completeGenerator =
        new CompleteGraphGenerator<String, DefaultEdge>(10);
    completeGenerator.generateGraph(completeGraph);
  }

  @Test
  public void createMultiGraphWithWeightedEdges() {
    Graph<String, DefaultWeightedEdge> multiGraph =
        new WeightedMultigraph<>(DefaultWeightedEdge.class);
    multiGraph.addVertex("v1");
    multiGraph.addVertex("v2");
    DefaultWeightedEdge edge1 = multiGraph.addEdge("v1", "v2");
    multiGraph.setEdgeWeight(edge1, 5);

    DefaultWeightedEdge edge2 = multiGraph.addEdge("v1", "v2");
    multiGraph.setEdgeWeight(edge2, 3);
  }
}
