import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by david on 11/2/2016.
 */
public class JungHyperGraph implements Hypergraph<Vertex, Edge> {

    private final Graph graph;

    public JungHyperGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * Returns a view of all edges in this graph. In general, this
     * obeys the <code>Collection</code> contract, and therefore makes no guarantees
     * about the ordering of the vertices within the set.
     *
     * @return a <code>Collection</code> view of all edges in this graph
     */
    @Override
    public Collection<Edge> getEdges() {
        return IteratorUtils.toList(graph.edges());
    }

    /**
     * Returns a view of all vertices in this graph. In general, this
     * obeys the <code>Collection</code> contract, and therefore makes no guarantees
     * about the ordering of the vertices within the set.
     *
     * @return a <code>Collection</code> view of all vertices in this graph
     */
    @Override
    public Collection<Vertex> getVertices() {
        return IteratorUtils.toList(graph.vertices());
    }

    /**
     * Returns true if this graph's vertex collection contains <code>vertex</code>.
     * Equivalent to <code>getVertices().contains(vertex)</code>.
     *
     * @param vertex the vertex whose presence is being queried
     * @return true iff this graph contains a vertex <code>vertex</code>
     */
    @Override
    public boolean containsVertex(Vertex vertex) {
        return graph.traversal().V().has("id", (Object) vertex.value("id")).hasNext();
    }

    /**
     * Returns true if this graph's edge collection contains <code>edge</code>.
     * Equivalent to <code>getEdges().contains(edge)</code>.
     *
     * @param edge the edge whose presence is being queried
     * @return true iff this graph contains an edge <code>edge</code>
     */
    @Override
    public boolean containsEdge(Edge edge) {
        return graph.traversal().E().has("id", (Object) edge.value("id")).hasNext();
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    @Override
    public int getEdgeCount() {
        return CollectionUtils.size(graph.edges());
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    @Override
    public int getVertexCount() {
        return CollectionUtils.size(graph.vertices());
    }

    /**
     * Returns the collection of vertices which are connected to <code>vertex</code>
     * via any edges in this graph.
     * If <code>vertex</code> is connected to itself with a self-loop, then
     * it will be included in the collection returned.
     *
     * @param vertex the vertex whose neighbors are to be returned
     * @return the collection of vertices which are connected to <code>vertex</code>,
     * or <code>null</code> if <code>vertex</code> is not present
     */
    @Override
    public Collection<Vertex> getNeighbors(Vertex vertex) {
        return new HashSet<Vertex>(IteratorUtils.toList(vertex.vertices(Direction.BOTH)));
    }

    /**
     * Returns the collection of edges in this graph which are connected to <code>vertex</code>.
     *
     * @param vertex the vertex whose incident edges are to be returned
     * @return the collection of edges which are connected to <code>vertex</code>,
     * or <code>null</code> if <code>vertex</code> is not present
     */
    @Override
    public Collection<Edge> getIncidentEdges(Vertex vertex) {
        return IteratorUtils.toList(vertex.edges(Direction.BOTH));
    }

    /**
     * Returns the collection of vertices in this graph which are connected to <code>edge</code>.
     * Note that for some graph types there are guarantees about the size of this collection
     * (i.e., some graphs contain edges that have exactly two endpoints, which may or may
     * not be distinct).  Implementations for those graph types may provide alternate methods
     * that provide more convenient access to the vertices.
     *
     * @param edge the edge whose incident vertices are to be returned
     * @return the collection of vertices which are connected to <code>edge</code>,
     * or <code>null</code> if <code>edge</code> is not present
     */
    @Override
    public Collection<Vertex> getIncidentVertices(Edge edge) {
        return IteratorUtils.toList(edge.vertices(Direction.BOTH));
    }

    /**
     * Returns an edge that connects this vertex to <code>v</code>.
     * If this edge is not uniquely
     * defined (that is, if the graph contains more than one edge connecting
     * <code>v1</code> to <code>v2</code>), any of these edges
     * may be returned.  <code>findEdgeSet(v1, v2)</code> may be
     * used to return all such edges.
     * Returns null if either of the following is true:
     * <ul>
     * <li/><code>v2</code> is not connected to <code>v1</code>
     * <li/>either <code>v1</code> or <code>v2</code> are not present in this graph
     * </ul>
     * <p><b>Note</b>: for purposes of this method, <code>v1</code> is only considered to be connected to
     * <code>v2</code> via a given <i>directed</i> edge <code>e</code> if
     * <code>v1 == e.getSource() && v2 == e.getDest()</code> evaluates to <code>true</code>.
     * (<code>v1</code> and <code>v2</code> are connected by an undirected edge <code>u</code> if
     * <code>u</code> is incident to both <code>v1</code> and <code>v2</code>.)
     *
     * @param v1
     * @param v2
     * @return an edge that connects <code>v1</code> to <code>v2</code>,
     * or <code>null</code> if no such edge exists (or either vertex is not present)
     * @see Hypergraph#findEdgeSet(Object, Object)
     */
    @Override
    public Edge findEdge(Vertex v1, Vertex v2) {
        for (Edge edge : IteratorUtils.asIterable(v1.edges(Direction.OUT))) {
            if (edge.inVertex().equals(v2)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Returns all edges that connects this vertex to <code>v</code>.
     * If this edge is not uniquely
     * defined (that is, if the graph contains more than one edge connecting
     * <code>v1</code> to <code>v2</code>), any of these edges
     * may be returned.  <code>findEdgeSet(v1, v2)</code> may be
     * used to return all such edges.
     * Returns null if <code>v2</code> is not connected to <code>v1</code>.
     * <br/>Returns an empty collection if either <code>v1</code> or <code>v2</code> are not present in this graph.
     * <p>
     * <p><b>Note</b>: for purposes of this method, <code>v1</code> is only considered to be connected to
     * <code>v2</code> via a given <i>directed</i> edge <code>d</code> if
     * <code>v1 == d.getSource() && v2 == d.getDest()</code> evaluates to <code>true</code>.
     * (<code>v1</code> and <code>v2</code> are connected by an undirected edge <code>u</code> if
     * <code>u</code> is incident to both <code>v1</code> and <code>v2</code>.)
     *
     * @param v1
     * @param v2
     * @return a collection containing all edges that connect <code>v1</code> to <code>v2</code>,
     * or <code>null</code> if either vertex is not present
     * @see Hypergraph#findEdge(Object, Object)
     */
    @Override
    public Collection<Edge> findEdgeSet(Vertex v1, Vertex v2) {
        Set<Edge> edgeSet = new HashSet<>();
        for (Edge edge : IteratorUtils.asIterable(v1.edges(Direction.OUT))) {
            if (edge.inVertex().equals(v2)) {
                edgeSet.add(edge);
            }
        }
        return edgeSet;
    }

    /**
     * Adds <code>vertex</code> to this graph.
     * Fails if <code>vertex</code> is null or already in the graph.
     *
     * @param vertex the vertex to add
     * @return <code>true</code> if the add is successful, and <code>false</code> otherwise
     * @throws IllegalArgumentException if <code>vertex</code> is <code>null</code>
     */
    @Override
    public boolean addVertex(Vertex vertex) {
        String msg = "Because graph db we're using: Titan, doesn't support adding custom id";
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Adds <code>edge</code> to this graph.
     * Fails under the following circumstances:
     * <ul>
     * <li/><code>edge</code> is already an element of the graph
     * <li/>either <code>edge</code> or <code>vertices</code> is <code>null</code>
     * <li/><code>vertices</code> has the wrong number of vertices for the graph type
     * <li/><code>vertices</code> are already connected by another edge in this graph,
     * and this graph does not accept parallel edges
     * </ul>
     *
     * @param edge
     * @param vertices
     * @return <code>true</code> if the add is successful, and <code>false</code> otherwise
     * @throws IllegalArgumentException if <code>edge</code> or <code>vertices</code> is null,
     *                                  or if a different vertex set in this graph is already connected by <code>edge</code>,
     *                                  or if <code>vertices</code> are not a legal vertex set for <code>edge</code>
     */
    @Override
    public boolean addEdge(Edge edge, Collection<? extends Vertex> vertices) {
        return false;
    }

    /**
     * Adds <code>edge</code> to this graph with type <code>edge_type</code>.
     * Fails under the following circumstances:
     * <ul>
     * <li/><code>edge</code> is already an element of the graph
     * <li/>either <code>edge</code> or <code>vertices</code> is <code>null</code>
     * <li/><code>vertices</code> has the wrong number of vertices for the graph type
     * <li/><code>vertices</code> are already connected by another edge in this graph,
     * and this graph does not accept parallel edges
     * <li/><code>edge_type</code> is not legal for this graph
     * </ul>
     *
     * @param edge
     * @param vertices
     * @param edge_type
     * @return <code>true</code> if the add is successful, and <code>false</code> otherwise
     * @throws IllegalArgumentException if <code>edge</code> or <code>vertices</code> is null,
     *                                  or if a different vertex set in this graph is already connected by <code>edge</code>,
     *                                  or if <code>vertices</code> are not a legal vertex set for <code>edge</code>
     */
    @Override
    public boolean addEdge(Edge edge, Collection<? extends Vertex> vertices, EdgeType edge_type) {
        String msg = "Because graph db we're using: Titan, doesn't support adding custom id";
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Removes <code>vertex</code> from this graph.
     * As a side effect, removes any edges <code>e</code> incident to <code>vertex</code> if the
     * removal of <code>vertex</code> would cause <code>e</code> to be incident to an illegal
     * number of vertices.  (Thus, for example, incident hyperedges are not removed, but
     * incident edges--which must be connected to a vertex at both endpoints--are removed.)
     * <p>
     * <p>Fails under the following circumstances:
     * <ul>
     * <li/><code>vertex</code> is not an element of this graph
     * <li/><code>vertex</code> is <code>null</code>
     * </ul>
     *
     * @param vertex the vertex to remove
     * @return <code>true</code> if the removal is successful, <code>false</code> otherwise
     */
    @Override
    public boolean removeVertex(Vertex vertex) {
        String msg = "Assume graph is immutable";
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Removes <code>edge</code> from this graph.
     * Fails if <code>edge</code> is null, or is otherwise not an element of this graph.
     *
     * @param edge the edge to remove
     * @return <code>true</code> if the removal is successful, <code>false</code> otherwise
     */
    @Override
    public boolean removeEdge(Edge edge) {
        String msg = "Assume graph is immutable";
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Returns <code>true</code> if <code>v1</code> and <code>v2</code> share an incident edge.
     * Equivalent to <code>getNeighbors(v1).contains(v2)</code>.
     *
     * @param v1 the first vertex to test
     * @param v2 the second vertex to test
     * @return <code>true</code> if <code>v1</code> and <code>v2</code> share an incident edge
     */
    @Override
    public boolean isNeighbor(Vertex v1, Vertex v2) {
        return getNeighbors(v1).contains(v2);
    }

    /**
     * Returns <code>true</code> if <code>vertex</code> and <code>edge</code>
     * are incident to each other.
     * Equivalent to <code>getIncidentEdges(vertex).contains(edge)</code> and to
     * <code>getIncidentVertices(edge).contains(vertex)</code>.
     *
     * @param vertex
     * @param edge
     * @return <code>true</code> if <code>vertex</code> and <code>edge</code>
     * are incident to each other
     */
    @Override
    public boolean isIncident(Vertex vertex, Edge edge) {
        return getIncidentEdges(vertex).contains(edge);
    }

    /**
     * Returns the number of edges incident to <code>vertex</code>.
     * Special cases of interest:
     * <ul>
     * <li/> Incident self-loops are counted once.
     * <li> If there is only one edge that connects this vertex to
     * each of its neighbors (and vice versa), then the value returned
     * will also be equal to the number of neighbors that this vertex has
     * (that is, the output of <code>getNeighborCount</code>).
     * <li> If the graph is directed, then the value returned will be
     * the sum of this vertex's indegree (the number of edges whose
     * destination is this vertex) and its outdegree (the number
     * of edges whose source is this vertex), minus the number of
     * incident self-loops (to avoid double-counting).
     * </ul>
     * <p>Equivalent to <code>getIncidentEdges(vertex).size()</code>.
     *
     * @param vertex the vertex whose degree is to be returned
     * @return the degree of this node
     * @see Hypergraph#getNeighborCount(Object)
     */
    @Override
    public int degree(Vertex vertex) {
        return getIncidentEdges(vertex).size();
    }

    /**
     * Returns the number of vertices that are adjacent to <code>vertex</code>
     * (that is, the number of vertices that are incident to edges in <code>vertex</code>'s
     * incident edge set).
     * <p>
     * <p>Equivalent to <code>getNeighbors(vertex).size()</code>.
     *
     * @param vertex the vertex whose neighbor count is to be returned
     * @return the number of neighboring vertices
     */
    @Override
    public int getNeighborCount(Vertex vertex) {
        return getNeighbors(vertex).size();
    }

    /**
     * Returns the number of vertices that are incident to <code>edge</code>.
     * For hyperedges, this can be any nonnegative integer; for edges this
     * must be 2 (or 1 if self-loops are permitted).
     * <p>
     * <p>Equivalent to <code>getIncidentVertices(edge).size()</code>.
     *
     * @param edge the edge whose incident vertex count is to be returned
     * @return the number of vertices that are incident to <code>edge</code>.
     */
    @Override
    public int getIncidentCount(Edge edge) {
        return getIncidentVertices(edge).size();
    }

    /**
     * Returns the edge type of <code>edge</code> in this graph.
     *
     * @param edge
     * @return the <code>EdgeType</code> of <code>edge</code>, or <code>null</code> if <code>edge</code> has no defined type
     */
    @Override
    public EdgeType getEdgeType(Edge edge) {
        return getDefaultEdgeType();
    }

    /**
     * Returns the default edge type for this graph.
     *
     * @return the default edge type for this graph
     */
    @Override
    public EdgeType getDefaultEdgeType() {
        return EdgeType.DIRECTED;
    }

    /**
     * Returns the collection of edges in this graph which are of type <code>edge_type</code>.
     *
     * @param edge_type the type of edges to be returned
     * @return the collection of edges which are of type <code>edge_type</code>, or
     * <code>null</code> if the graph does not accept edges of this type
     * @see EdgeType
     */
    @Override
    public Collection<Edge> getEdges(EdgeType edge_type) {
        return IteratorUtils.toList(graph.edges());
    }

    /**
     * Returns the number of edges of type <code>edge_type</code> in this graph.
     *
     * @param edge_type the type of edge for which the count is to be returned
     * @return the number of edges of type <code>edge_type</code> in this graph
     */
    @Override
    public int getEdgeCount(EdgeType edge_type) {
        return getEdges(edge_type).size();
    }

    /**
     * Returns a <code>Collection</code> view of the incoming edges incident to <code>vertex</code>
     * in this graph.
     *
     * @param vertex the vertex whose incoming edges are to be returned
     * @return a <code>Collection</code> view of the incoming edges incident
     * to <code>vertex</code> in this graph
     */
    @Override
    public Collection<Edge> getInEdges(Vertex vertex) {
        return IteratorUtils.toList(vertex.edges(Direction.IN));
    }

    /**
     * Returns a <code>Collection</code> view of the outgoing edges incident to <code>vertex</code>
     * in this graph.
     *
     * @param vertex the vertex whose outgoing edges are to be returned
     * @return a <code>Collection</code> view of the outgoing edges incident
     * to <code>vertex</code> in this graph
     */
    @Override
    public Collection<Edge> getOutEdges(Vertex vertex) {
        return IteratorUtils.toList(vertex.edges(Direction.OUT));
    }

    /**
     * Returns the number of incoming edges incident to <code>vertex</code>.
     * Equivalent to <code>getInEdges(vertex).size()</code>.
     *
     * @param vertex the vertex whose indegree is to be calculated
     * @return the number of incoming edges incident to <code>vertex</code>
     */
    @Override
    public int inDegree(Vertex vertex) {
        return getInEdges(vertex).size();
    }

    /**
     * Returns the number of outgoing edges incident to <code>vertex</code>.
     * Equivalent to <code>getOutEdges(vertex).size()</code>.
     *
     * @param vertex the vertex whose outdegree is to be calculated
     * @return the number of outgoing edges incident to <code>vertex</code>
     */
    @Override
    public int outDegree(Vertex vertex) {
        return getOutEdges(vertex).size();
    }

    /**
     * If <code>directed_edge</code> is a directed edge in this graph, returns the source;
     * otherwise returns <code>null</code>.
     * The source of a directed edge <code>d</code> is defined to be the vertex for which
     * <code>d</code> is an outgoing edge.
     * <code>directed_edge</code> is guaranteed to be a directed edge if
     * its <code>EdgeType</code> is <code>DIRECTED</code>.
     *
     * @param directed_edge
     * @return the source of <code>directed_edge</code> if it is a directed edge in this graph, or <code>null</code> otherwise
     */
    @Override
    public Vertex getSource(Edge directed_edge) {
        return directed_edge.outVertex();
    }

    /**
     * If <code>directed_edge</code> is a directed edge in this graph, returns the destination;
     * otherwise returns <code>null</code>.
     * The destination of a directed edge <code>d</code> is defined to be the vertex
     * incident to <code>d</code> for which
     * <code>d</code> is an incoming edge.
     * <code>directed_edge</code> is guaranteed to be a directed edge if
     * its <code>EdgeType</code> is <code>DIRECTED</code>.
     *
     * @param directed_edge
     * @return the destination of <code>directed_edge</code> if it is a directed edge in this graph, or <code>null</code> otherwise
     */
    @Override
    public Vertex getDest(Edge directed_edge) {
        return directed_edge.inVertex();
    }

    /**
     * Returns a <code>Collection</code> view of the predecessors of <code>vertex</code>
     * in this graph.  A predecessor of <code>vertex</code> is defined as a vertex <code>v</code>
     * which is connected to
     * <code>vertex</code> by an edge <code>e</code>, where <code>e</code> is an outgoing edge of
     * <code>v</code> and an incoming edge of <code>vertex</code>.
     *
     * @param vertex the vertex whose predecessors are to be returned
     * @return a <code>Collection</code> view of the predecessors of
     * <code>vertex</code> in this graph
     */
    @Override
    public Collection<Vertex> getPredecessors(Vertex vertex) {
        return IteratorUtils.toList(vertex.vertices(Direction.IN));
    }

    /**
     * Returns a <code>Collection</code> view of the successors of <code>vertex</code>
     * in this graph.  A successor of <code>vertex</code> is defined as a vertex <code>v</code>
     * which is connected to
     * <code>vertex</code> by an edge <code>e</code>, where <code>e</code> is an incoming edge of
     * <code>v</code> and an outgoing edge of <code>vertex</code>.
     *
     * @param vertex the vertex whose predecessors are to be returned
     * @return a <code>Collection</code> view of the successors of
     * <code>vertex</code> in this graph
     */
    @Override
    public Collection<Vertex> getSuccessors(Vertex vertex) {
        return IteratorUtils.toList(vertex.vertices(Direction.OUT));
    }
}
