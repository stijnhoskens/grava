package grava.search;

import static grava.util.SetUtils.setOf;
import grava.edge.Link;
import grava.walk.Walk;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractSearch<V, E extends Link<V>> implements
		SearchStrategy<V, E> {

	protected static <V, E extends Link<V>> Set<Walk<V, E>> getNewWalks(
			Searchable<V, E> graph, Walk<V, E> walk) {
		return setOf(graph.edgesOf(walk.endVertex()).stream()
				.map(walk::getExtended));
	}

	protected static <V, E extends Link<V>> List<Walk<V, E>> getNewWalksList(
			Searchable<V, E> graph, Walk<V, E> walk) {
		return graph.edgesOf(walk.endVertex()).stream().map(walk::getExtended)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start, V end) {
		return findPath(graph, start, v -> v.equals(end));
	}

	/**
	 * Returns true if extendedWalk is still a path
	 * 
	 * @param path
	 *            A walk that is also a path
	 * @param extendedWalk
	 *            Path extended with one vertex
	 * @return true iff extendedWalk is still a path.
	 */
	protected boolean isStillPath(Walk<V, E> path, Walk<V, E> extendedWalk) {
		return !path.contains(extendedWalk.endVertex());
	}

}
