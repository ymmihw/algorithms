package com.ymmihw.algorithms;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.junit.Before;
import org.junit.Test;

public class DirectedGraphUnitTest {
  Graph<String, DefaultEdge> directedGraph;

  @Before
  public void createDirectedGraph() {
    directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    IntStream.range(1, 10).forEach(i -> {
      directedGraph.addVertex("v" + i);
    });
    directedGraph.addEdge("v1", "v2");
    directedGraph.addEdge("v2", "v4");
    directedGraph.addEdge("v4", "v3");
    directedGraph.addEdge("v3", "v1");
    directedGraph.addEdge("v5", "v4");
    directedGraph.addEdge("v5", "v6");
    directedGraph.addEdge("v6", "v7");
    directedGraph.addEdge("v7", "v5");
    directedGraph.addEdge("v8", "v5");
    directedGraph.addEdge("v9", "v8");
  }

  @Test
  public void givenDirectedGraph_whenGetStronglyConnectedSubgraphs_thenPathExistsBetweenStronglyconnectedVertices() {
    StrongConnectivityAlgorithm<String, DefaultEdge> scAlg =
        new KosarajuStrongConnectivityInspector<>(directedGraph);
    List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphs =
        scAlg.getStronglyConnectedComponents();
    List<String> stronglyConnectedVertices =
        new ArrayList<>(stronglyConnectedSubgraphs.get(3).vertexSet());

    String randomVertex1 = stronglyConnectedVertices.get(0);
    String randomVertex2 = stronglyConnectedVertices.get(3);
    AllDirectedPaths<String, DefaultEdge> allDirectedPaths = new AllDirectedPaths<>(directedGraph);

    List<GraphPath<String, DefaultEdge>> possiblePathList = allDirectedPaths
        .getAllPaths(randomVertex1, randomVertex2, false, stronglyConnectedVertices.size());
    assertTrue(possiblePathList.size() > 0);
  }

  @Test
  public void givenDirectedGraphWithCycle_whenCheckCycles_thenDetectCycles() {
    CycleDetector<String, DefaultEdge> cycleDetector =
        new CycleDetector<String, DefaultEdge>(directedGraph);
    assertTrue(cycleDetector.detectCycles());
    Set<String> cycleVertices = cycleDetector.findCycles();
    assertTrue(cycleVertices.size() > 0);
  }

  @Test
  public void givenDirectedGraph_whenCreateInstanceDepthFirstIterator_thenGetIterator() {
    DepthFirstIterator<String, DefaultEdge> depthFirstIterator =
        new DepthFirstIterator<>(directedGraph);
    assertNotNull(depthFirstIterator);
  }

  @Test
  public void givenDirectedGraph_whenCreateInstanceBreadthFirstIterator_thenGetIterator() {
    BreadthFirstIterator<String, DefaultEdge> breadthFirstIterator =
        new BreadthFirstIterator<>(directedGraph);
    assertNotNull(breadthFirstIterator);
  }

  @Test
  public void givenDirectedGraph_whenGetDijkstraShortestPath_thenGetNotNullPath() {
    DijkstraShortestPath<String, DefaultEdge> dijkstraShortestPath =
        new DijkstraShortestPath<>(directedGraph);
    List<String> shortestPath = dijkstraShortestPath.getPath("v1", "v4").getVertexList();
    assertNotNull(shortestPath);
  }

  @Test
  public void givenDirectedGraph_whenGetBellmanFordShortestPath_thenGetNotNullPath() {
    BellmanFordShortestPath<String, DefaultEdge> bellmanFordShortestPath =
        new BellmanFordShortestPath<>(directedGraph);
    List<String> shortestPath = bellmanFordShortestPath.getPath("v1", "v4").getVertexList();
    assertNotNull(shortestPath);
  }
}
