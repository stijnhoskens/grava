package grava.search;

import static grava.util.SetUtils.setOf;
import grava.edge.Link;
import grava.walk.Walk;

import java.util.Set;

public abstract class AbstractSearch<V, E extends Link<V>> implements
		SearchStrategy<V, E> {

	protected static <V, E extends Link<V>> Set<Walk<V, E>> getNewWalks(
			Searchable<V, E> graph, Walk<V, E> walk) {
		return setOf(graph.edgesOf(walk.endVertex()).stream()
				.map(walk::getExtended));
	}
}
