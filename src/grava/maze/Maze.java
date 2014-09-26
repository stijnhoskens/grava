package grava.maze;

import java.util.HashMap;
import java.util.Map;

import grava.edge.Edge;
import grava.graph.MappedGraph;

public class Maze<V extends Positioned> extends MappedGraph<V, Edge<V>> {

	private Map<Position, V> posToVertex = new HashMap<>();

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
				.manhattanTo(e.asPair().getSecond().getPosition()) > 1)
			return;
		super.addEdge(e);
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
	public V getVertexAt(Position p) {
		return posToVertex.get(p);
	}

}
