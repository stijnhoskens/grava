package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import node.Node;
import edge.Edge;

/**
 * Class used to fully reconstruct, this is done using a search across all
 * nodes, these nodes and edges can then be collected. The graph needs to be
 * fully connected if you want all nodes to be found, otherwise only the nodes
 * connected to the start node will be explored.
 * 
 * @author Stijn
 * 
 * @param <T>
 *            Node
 * @param <S>
 *            Edge
 */
public class GraphExplorer<T extends Node, S extends Edge<T>> {

	private final Graph<T, S> graph;
	private final T start;
	private boolean isExplored = false;
	private final List<T> exploredNodes = new ArrayList<>();
	private final Set<S> exploredEdges = new HashSet<>();
	private final Map<T, Set<S>> map = new HashMap<>();

	/**
	 * Starts exploring for all nodes & edges in the graph.
	 * 
	 * @param graph
	 *            The given graph.
	 * @param seed
	 *            The seed node used in the algorithm
	 * @note Only nodes connected to the seed will be explored.
	 */
	public GraphExplorer(Graph<T, S> graph, T seed) {
		this.graph = graph;
		this.start = seed;
		startExploring();
	}

	public Set<T> getNodes() {
		try {
			awaitExplorer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new HashSet<>(exploredNodes);
	}

	public Set<S> getEdges() {
		try {
			awaitExplorer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exploredEdges;
	}

	public Map<T, Set<S>> getNodeMapping() {
		try {
			awaitExplorer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.map;
	}

	private synchronized void awaitExplorer() throws InterruptedException {
		while (!isExplored)
			wait();
	}

	private void startExploring() {
		new Thread(new ExplorationRunner()).start();
	}

	private class ExplorationRunner implements Runnable {

		// Using a set ensures uniqueness of all items in it. This also enforces
		// random polling, which means it's a form of non deterministic search.
		private Set<T> q = new HashSet<>();

		@Override
		public void run() {
			q.add(start);
			// This loop will terminate whenever all nodes of the graph are in
			// the nodes set. That is, of course, when the graph is finite :)
			while (!q.isEmpty()) {
				T node = nextElement();
				if (exploredNodes.contains(node))
					continue;
				Set<T> neighbors = graph.getNeighborsOf(node);
				q.addAll(neighbors);
				Set<S> edges = graph.getEdgesFrom(node);
				exploredNodes.add(node);
				map.put(node, edges);
			}
			isExplored = true;
			GraphExplorer.this.notify();
		}

		private T nextElement() {
			return q.iterator().next();
		}
	}
}
