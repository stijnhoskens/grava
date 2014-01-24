package graph.neighbor;

import node.Node;
import edge.Edge;
import graph.Graph;
import graph.GraphExplorer;
import graph.MappedGraph;

/**
 * This graph is basic in the sense that it will build up the full graph when
 * only neighbors can be requested. It will encapsulate neighbors in an Edge of
 * type S.
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public abstract class BasicGraph<T extends Node, S extends Edge<T>>
		implements
			Graph<T, S> {

	protected final NeighborProvider<T> neighborProvider;

	/**
	 * @param provider
	 *            Provides all neighbors while searching for them. Supply a
	 *            graph as an argument and this graph will act as a shallow
	 *            facade for the underlying graph. This approach is not
	 *            recommended as it creates unnecessary overhead.
	 */
	public BasicGraph(NeighborProvider<T> provider) {
		this.neighborProvider = provider;
	}
	
	public BasicGraph(GraphExplorer<T, S> explorer) {
		this.neighborProvider = new MappedGraph<>(explorer);
	}
}
