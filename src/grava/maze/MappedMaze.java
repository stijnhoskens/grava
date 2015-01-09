package grava.maze;

import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntFunction;

import grava.edge.Edge;
import grava.graph.Graph;
import grava.graph.MappedGraph;

public class MappedMaze<V extends Positioned> extends MappedGraph<V, Edge<V>>
		implements Maze<V> {

	private Map<Position, V> posToVertex;

	public MappedMaze(Iterable<V> vertices, Iterable<Edge<V>> edges) {
		super(vertices, edges);
	}

	public MappedMaze(Iterable<V> vertices) {
		this(vertices, Collections.emptySet());
	}

	public MappedMaze() {
		this(Collections.emptySet(), Collections.emptySet());
	}

	public MappedMaze(Graph<V, Edge<V>> graph) {
		super(graph);
	}

	/**
	 * Adds the given edge to the graph. If the edge happens to be already in
	 * the graph, nothing happens. If one of the vertices did not appear in the
	 * vertex set before, it is added. This way, a connected graph can be
	 * constructed by only using this method.
	 * 
	 * In the maze only edges between neighboring vertices can be added, if the
	 * edge connects vertices not adjacent to one another, nothing happens.
	 * 
	 * @param e
	 *            the edge to be added
	 */
	@Override
	public void addEdge(Edge<V> e) {
		if (e.asPair().getFirst().getPosition()
				.manhattanTo(e.asPair().getSecond().getPosition()) == 1)
			super.addEdge(e);
	}

	/**
	 * Adds an edge starting at the given vertex, going in the given direction.
	 * Because the edge is bi-directional, the effect is the same as adding an
	 * edge in the resulting end-vertex (the one to which the first one gets
	 * connected) going to the opposing direction. If there is no such
	 * neighboring vertex, nothing happens.
	 * 
	 * Returns true iff an edge actually is added.
	 * 
	 * @param v
	 *            the starting vertex
	 * @param direction
	 *            the direction the edge is going to
	 * @return true iff the addition was successful
	 */
	public boolean addEdge(V v, Direction direction) {
		V otherV = neighbour(v, direction);
		if (otherV == null)
			return false;
		super.addEdge(new Edge<>(v, otherV));
		return true;
	}

	/**
	 * The same as addEdge(v, direction), but for deletion.
	 * 
	 * @see addEdge
	 * @param v
	 *            the starting vertex
	 * @param direction
	 *            the direction the edge is going to
	 * @return true iff the deletion was successful
	 */
	public boolean removeEdge(V v, Direction direction) {
		V otherV = neighbour(v, direction);
		if (otherV == null)
			return false;
		return removeEdgeBetween(v, otherV);
	}

	/**
	 * The same as removeEdge, but added for increased readability.
	 */
	@Override
	public boolean addWall(V v, Direction direction) {
		return removeEdge(v, direction);
	}

	/**
	 * The same as removeEdgeBetween, but added for increased readability.
	 */
	@Override
	public boolean addWallBetween(V v, V u) {
		return removeEdgeBetween(v, u);
	}

	/**
	 * The same as addEdge, but added for increased readability.
	 */
	@Override
	public boolean removeWall(V v, Direction direction) {
		return addEdge(v, direction);
	}

	/**
	 * The same as addEdgeBetween, but added for increased readability.
	 */
	public void removeWallBetween(V v, V u) {
		addEdge(new Edge<>(v, u));
	}

	private V neighbour(V v, Direction dir) {
		return posToVertex.get(v.getPosition().neighbour(dir));
	}

	/**
	 * Adds the given vertex to the graph. If the position as specified by the
	 * vertex is already occupied, nothing happens.
	 * 
	 * @param v
	 *            the vertex to be added
	 */
	@Override
	public void addVertex(V v) {
		if (posToVertex.containsKey(v.getPosition()))
			return;
		posToVertex.put(v.getPosition(), v);
		super.addVertex(v);
	}

	@Override
	public boolean removeVertex(V v) {
		posToVertex.remove(v.getPosition(), v);
		return super.removeVertex(v);
	}

	/**
	 * Returns the vertex at position p, or null if there is no such vertex.
	 * 
	 * @param p
	 *            The position of the required vertex
	 * @return The required vertex
	 */
	@Override
	public V getVertexAt(Position p) {
		return posToVertex.get(p);
	}

	/**
	 * Returns the width of the maze, being the difference of the maximum and
	 * the minimum x-coordinate of all nodes incremented by one. If this maze
	 * contains no nodes, zero is returned.
	 * 
	 * @return the width of the maze.
	 */
	@Override
	public int width() {
		return minMaxDifference(Position::getX);
	}

	/**
	 * Returns the height of the maze, being the difference of the maximum and
	 * the minimum y-coordinate of all nodes incremented by one. If this maze
	 * contains no nodes, zero is returned.
	 * 
	 * @return the width of the maze.
	 */
	@Override
	public int height() {
		return minMaxDifference(Position::getY);
	}

	private int minMaxDifference(ToIntFunction<Position> mapper) {
		Set<Position> positions = posToVertex.keySet();
		if (positions.isEmpty())
			return 0;
		IntSummaryStatistics stats = positions.stream().mapToInt(mapper)
				.summaryStatistics();
		return stats.getMax() - stats.getMin() + 1;
	}

	@Override
	protected void initDataStructures() {
		posToVertex = new HashMap<>();
		super.initDataStructures();
	}

}
