import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by dheryanto on 12/02/2016.
 */
public class JungGraph extends JungHyperGraph implements Graph<Vertex, Edge> {

    public JungGraph(org.apache.tinkerpop.gremlin.structure.Graph graph) {
        super(graph);
    }

    /**
     * Returns <code>true</code> if <code>v1</code> is a predecessor of <code>v2</code> in this graph.
     * Equivalent to <code>v1.getPredecessors().contains(v2)</code>.
     *
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return <code>true</code> if <code>v1</code> is a predecessor of <code>v2</code>, and false otherwise.
     */
    @Override
    public boolean isPredecessor(Vertex v1, Vertex v2) {
        return getPredecessors(v1).contains(v2);
    }

    /**
     * Returns <code>true</code> if <code>v1</code> is a successor of <code>v2</code> in this graph.
     * Equivalent to <code>v1.getSuccessors().contains(v2)</code>.
     *
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return <code>true</code> if <code>v1</code> is a successor of <code>v2</code>, and false otherwise.
     */
    @Override
    public boolean isSuccessor(Vertex v1, Vertex v2) {
        return getSuccessors(v1).contains(v2);
    }

    /**
     * Returns the number of predecessors that <code>vertex</code> has in this graph.
     * Equivalent to <code>vertex.getPredecessors().size()</code>.
     *
     * @param vertex the vertex whose predecessor count is to be returned
     * @return the number of predecessors that <code>vertex</code> has in this graph
     */
    @Override
    public int getPredecessorCount(Vertex vertex) {
        return getPredecessors(vertex).size();
    }

    /**
     * Returns the number of successors that <code>vertex</code> has in this graph.
     * Equivalent to <code>vertex.getSuccessors().size()</code>.
     *
     * @param vertex the vertex whose successor count is to be returned
     * @return the number of successors that <code>vertex</code> has in this graph
     */
    @Override
    public int getSuccessorCount(Vertex vertex) {
        return getSuccessors(vertex).size();
    }

    /**
     * Returns <code>true</code> if <code>vertex</code> is the source of <code>edge</code>.
     * Equivalent to <code>getSource(edge).equals(vertex)</code>.
     *
     * @param vertex the vertex to be queried
     * @param edge   the edge to be queried
     * @return <code>true</code> iff <code>vertex</code> is the source of <code>edge</code>
     */
    @Override
    public boolean isSource(Vertex vertex, Edge edge) {
        return getSource(edge).equals(vertex);
    }

    /**
     * Returns <code>true</code> if <code>vertex</code> is the destination of <code>edge</code>.
     * Equivalent to <code>getDest(edge).equals(vertex)</code>.
     *
     * @param vertex the vertex to be queried
     * @param edge   the edge to be queried
     * @return <code>true</code> iff <code>vertex</code> is the destination of <code>edge</code>
     */
    @Override
    public boolean isDest(Vertex vertex, Edge edge) {
        return getDest(edge).equals(vertex);
    }

    /**
     * Adds edge <code>e</code> to this graph such that it connects
     * vertex <code>v1</code> to <code>v2</code>.
     * Equivalent to <code>addEdge(e, new Pair<V>(v1, v2))</code>.
     * If this graph does not contain <code>v1</code>, <code>v2</code>,
     * or both, implementations may choose to either silently add
     * the vertices to the graph or throw an <code>IllegalArgumentException</code>.
     * If this graph assigns edge types to its edges, the edge type of
     * <code>e</code> will be the default for this graph.
     * See <code>Hypergraph.addEdge()</code> for a listing of possible reasons
     * for failure.
     *
     * @param edge the edge to be added
     * @param v1   the first vertex to be connected
     * @param v2   the second vertex to be connected
     * @return <code>true</code> if the add is successful, <code>false</code> otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(Object, Object, Object, EdgeType)
     */
    @Override
    public boolean addEdge(Edge edge, Vertex v1, Vertex v2) {
        String msg = "Assume graph is immutable";
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Adds edge <code>e</code> to this graph such that it connects
     * vertex <code>v1</code> to <code>v2</code>.
     * Equivalent to <code>addEdge(e, new Pair<V>(v1, v2))</code>.
     * If this graph does not contain <code>v1</code>, <code>v2</code>,
     * or both, implementations may choose to either silently add
     * the vertices to the graph or throw an <code>IllegalArgumentException</code>.
     * If <code>edgeType</code> is not legal for this graph, this method will
     * throw <code>IllegalArgumentException</code>.
     * See <code>Hypergraph.addEdge()</code> for a listing of possible reasons
     * for failure.
     *
     * @param edge     the edge to be added
     * @param v1       the first vertex to be connected
     * @param v2       the second vertex to be connected
     * @param edgeType the type to be assigned to the edge
     * @return <code>true</code> if the add is successful, <code>false</code> otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(Object, Object, Object)
     */
    @Override
    public boolean addEdge(Edge edge, Vertex v1, Vertex v2, EdgeType edgeType) {
        String msg = "Assume graph is immutable";
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Returns the endpoints of <code>edge</code> as a <code>Pair<V></code>.
     *
     * @param edge the edge whose endpoints are to be returned
     * @return the endpoints (incident vertices) of <code>edge</code>
     */
    @Override
    public Pair<Vertex> getEndpoints(Edge edge) {
        return new Pair<>(getSource(edge), getDest(edge));
    }

    /**
     * Returns the vertex at the other end of <code>edge</code> from <code>vertex</code>.
     * (That is, returns the vertex incident to <code>edge</code> which is not <code>vertex</code>.)
     *
     * @param vertex the vertex to be queried
     * @param edge   the edge to be queried
     * @return the vertex at the other end of <code>edge</code> from <code>vertex</code>
     */
    @Override
    public Vertex getOpposite(Vertex vertex, Edge edge) {
        Vertex source = getSource(edge);
        Vertex dest = getDest(edge);
        return source.equals(vertex) ? dest : source;
    }
}
