package grava.maze;

import grava.edge.Edge;
import grava.graph.MappedGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

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
				d -> removeWallAt(v, d)));
	}

	/**
	 * Creates an open maze (where all adjacent nodes are connected) of size
	 * width x height.
	 * 
	 * @param width
	 *            the width of the maze
	 * @param height
	 *            the height
	 * @param mapper
	 *            mapper used to create a node from a given position
	 */
	public MappedMaze(int width, int height,
			Function<? super Position, ? extends V> mapper) {
		if (width == 0 || height == 0)
			return;
		V[] previous = connectRow(0, width, mapper);
		for (int y = 1; y < height; y++) {
			V[] actual = connectRow(y, width, mapper);
			for (int x = 0; x < width; x++)
				addEdge(new Edge<>(previous[x], actual[x]));
			previous = actual;
		}
	}

	private V[] connectRow(int y, int width,
			Function<? super Position, ? extends V> mapper) {
		@SuppressWarnings("unchecked")
		V[] result = (V[]) new Positioned[width];
		V previous = mapper.apply(new Position(0, y));
		result[0] = previous;
		for (int x = 1; x < width; x++) {
			V actual = mapper.apply(new Position(x, y));
			addEdge(new Edge<>(previous, actual));
			result[x] = actual;
			previous = actual;
		}
		return result;
	}

	@Override
	public void addWallAt(V v, Direction direction) {
		V otherV = neighbour(v, direction);
		if (otherV == null)
			return;
		if (removeEdgeBetween(v, otherV))
			informMazeListeners(l -> l.wallAdded(v.getPosition(), direction));
	}

	@Override
	public void addWallBetween(V u, V v) {
		Position pOfU = u.getPosition(), pOfV = v.getPosition();
		if (removeEdgeBetween(u, v))
			informMazeListeners(l -> l.wallAdded(v.getPosition(),
					Direction.between(pOfU, pOfV)));
	}

	@Override
	public boolean hasWallAt(V v, Direction direction) {
		V u = neighbour(v, direction);
		if (u == null)
			return true;
		return !areNeighbours(v, u);
	}

	@Override
	public boolean hasWallBetween(V u, V v) {
		return !areNeighbours(u, v);
	}

	@Override
	public boolean removeWallAt(V v, Direction direction) {
		return removeWallBetween(v, neighbour(v, direction), direction);
	}

	@Override
	public boolean removeWallBetween(V u, V v) {
		return removeWallBetween(u, v,
				Direction.between(u.getPosition(), v.getPosition()));
	}

	private boolean removeWallBetween(V u, V v, Direction dir) {
		if (v == null)
			return false;
		if (areNeighbours(u, v))
			return false;
		if (!v.equals(posToVertex.get(v.getPosition()))
				|| !u.equals(posToVertex.get(u.getPosition())))
			return false;
		if (v.getPosition().manhattanTo(u.getPosition()) != 1)
			return false;
		addEdge(new Edge<>(u, v));
		informMazeListeners(l -> l.wallRemoved(u.getPosition(), dir));
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

	private final Set<MazeListener> listeners = new HashSet<>();

	@Override
	public void addListener(MazeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListener(MazeListener l) {
		listeners.remove(l);
	}

	protected void informMazeListeners(Consumer<MazeListener> action) {
		listeners.forEach(action);
	}

}
