package grava.maze;

import java.util.Arrays;
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

	/**
	 * Creates a maze consisting of the given vertices, and the given edges.
	 * 
	 * @param vertices
	 *            the set of vertices
	 * @param edges
	 *            the set of edges
	 */
	public MappedMaze(Iterable<V> vertices, Iterable<Edge<V>> edges) {
		super(vertices, edges);
	}

	/**
	 * Creates an open maze consisting of the given set of vertices. This means
	 * all adjacent nodes are connected.
	 * 
	 * @param vertices
	 *            the set of vertices
	 */
	public MappedMaze(Iterable<V> vertices) {
		this(vertices, Collections.emptySet());
		vertices.forEach(v -> Arrays.stream(Direction.values()).forEach(
				d -> removeWall(v, d)));
	}

	/**
	 * Creates an empty maze, consisting of no vertices, and no walls
	 * (evidently).
	 */
	public MappedMaze() {
		this(Collections.emptySet(), Collections.emptySet());
	}

	/**
	 * Creates a maze out of the given graph
	 * 
	 * @param graph
	 *            graph of which the vertices and edges are to be copied.
	 */
	public MappedMaze(Graph<V, Edge<V>> graph) {
		super(graph);
	}

	@Override
	public void addWall(V v, Direction direction) {
		V otherV = neighbour(v, direction);
		if (otherV == null)
			return;
		removeEdgeBetween(v, otherV);
	}

	@Override
	public void addWallBetween(V u, V v) {
		removeEdgeBetween(u, v);
	}

	@Override
	public boolean removeWall(V v, Direction direction) {
		V u = neighbour(v, direction);
		if (u == null)
			return false;
		return removeWallBetween(v, u);
	}

	@Override
	public boolean removeWallBetween(V u, V v) {
		if (areNeighbours(u, v))
			return false;
		if (!v.equals(posToVertex.get(v.getPosition()))
				|| !u.equals(posToVertex.get(u.getPosition())))
			return false;
		if (v.getPosition().manhattanTo(u.getPosition()) != 1)
			return false;
		addEdge(new Edge<>(u, v));
		return true;
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

	@Override
	public V vertexAt(Position p) {
		return posToVertex.get(p);
	}

	@Override
	public int width() {
		return minMaxDifference(Position::getX);
	}

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
