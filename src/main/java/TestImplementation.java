import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;

import java.util.List;

/**
 * Created by dheryanto on 12/02/2016.
 */
public class TestImplementation {
    public static void main(String[] args) {
        Graph graph = TinkerFactory.createModern();
        JungHyperGraph jungHyperGraph = new JungHyperGraph(graph);
        PageRank<Vertex, Edge> pageRank = new PageRank<>(jungHyperGraph, 0.15d);
        pageRank.evaluate();

        System.out.println("============ PAGE RANK ============");
        for (Vertex vertex : IteratorUtils.asIterable(graph.vertices())) {
            System.out.println("PageRank of " + vertex + ": " + pageRank.getVertexScore(vertex));
        }

        System.out.println("============  SHORTEST PATH ============");
        JungGraph jungGraph = new JungGraph(graph);
        DijkstraShortestPath<Vertex, Edge> dijkstra = new DijkstraShortestPath<Vertex, Edge>(jungGraph);
        List<Vertex> vertexList = IteratorUtils.toList(graph.vertices());
        for (int i = 0; i < vertexList.size(); i++) {
            for (int j = i + 1; j < vertexList.size(); j++) {
                List<Edge> path = dijkstra.getPath(vertexList.get(i), vertexList.get(j));
                if (path.size() > 0) {
                    System.out.print(String.format("[%d --- %d]  \t", i, j));
                    for (Edge e : path) {
                        System.out.print(e);
                        System.out.print(" ");
                    }
                    System.out.println();
                }
            }
        }
    }
}
