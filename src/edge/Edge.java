package edge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import node.Node;

/**
 * This class is used for the most basic edge, in only one direction. This can
 * be the case when a certain street is one direction. You can also use this if
 * you insist on making two edges for a two way edge. It can come in handy in a
 * LinkedGraph, where only the other neighbor is needed, so a bidirectional
 * relation is unnecessary. Use the BiDirectionalEdge if you need the edge to
 * contain both linking nodes.
 * 
 * @author Stijn
 * 
 * @param <T>
 *            The node which the edge connects.
 */
public class Edge<T extends Node> {

	private final T destination;

	public Edge(T destination) {
		this.destination = destination;
	}

	public T getDestination() {
		return destination;
	}

	private static class DestinationSet<T extends Node, S extends Edge<T>>
			extends
				HashSet<T> {

		private static final long serialVersionUID = 1L;

		private final Collection<S> edges;

		public DestinationSet(Collection<S> edges) {
			this.edges = edges;
		}

		@Override
		public Iterator<T> iterator() {
			final Iterator<S> edgeIterator = edges.iterator();
			return new Iterator<T>() {

				@Override
				public boolean hasNext() {
					return edgeIterator.hasNext();
				}

				@Override
				public T next() {
					return edgeIterator.next().getDestination();
				}

				@Override
				public void remove() {
					edgeIterator.remove();
				}
			};
		}
	}

	/**
	 * Small optimization :)
	 */
	public static <T extends Node, S extends Edge<T>> Set<T> convert(
			Collection<S> edges) {
		return new DestinationSet<T, S>(edges);
	}

}
