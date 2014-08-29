package grava.search;

import static grava.util.SetUtils.setOf;
import grava.edge.Link;
import grava.walk.Walk;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractSearch<V, E extends Link<V>> implements
		SearchStrategy<V, E> {

	protected Set<Walk<V, E>> newWalks(Searchable<V, E> graph,
			Walk<V, E> walk) {
		return setOf(newWalksStream(graph, walk));
	}

	protected List<Walk<V, E>> newWalksList(Searchable<V, E> graph,
			Walk<V, E> walk) {
		return newWalksStream(graph, walk).collect(Collectors.toList());
	}

	// TODO TRY TO USE THIS INSTEAD OF COLLECTED STREAMS
	protected Stream<Walk<V, E>> newWalksStream(Searchable<V, E> graph,
			Walk<V, E> walk) {
		informListenersOf(walk);
		return graph.edgesOf(walk.endVertex()).stream().map(walk::getExtended);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start, V end) {
		return findPath(graph, start, v -> v.equals(end));
	}

	/**
	 * Returns true if extendedWalk is still a path
	 * 
	 * @param path
	 *            A walk that is already a path
	 * @param extendedWalk
	 *            Path extended with one vertex
	 * @return true iff extendedWalk is still a path.
	 */
	protected boolean isStillPath(Walk<V, E> path, Walk<V, E> extendedWalk) {
		return !path.contains(extendedWalk.endVertex());
	}

	private Set<SearchListener<V, E>> listeners = new HashSet<>();

	public void addListener(SearchListener<V, E> l) {
		listeners.add(l);
	}

	public void removeListener(SearchListener<V, E> l) {
		listeners.remove(l);
	}

	private void informListenersOf(Walk<V, E> walk) {
		listeners.forEach(l -> l.walkExplored(walk));
	}

}
