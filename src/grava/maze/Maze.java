package grava.maze;

import grava.edge.Edge;
import grava.graph.MappedGraph;

public class Maze<V extends Positioned> extends MappedGraph<V, Edge<V>> {

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
	public void addEdge(Edge<V> e) {
		if (e.asPair().getFirst().getPosition()
				.manhattanTo(e.asPair().getSecond().getPosition()) > 1)
			return;
		super.addEdge(e);
	}
}
