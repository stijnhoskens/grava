package grava.search.heuristic;

import grava.edge.Link;

public class HillClimbing2<V, E extends Link<V>> extends BeamSearch<V, E> {

	public HillClimbing2(Heuristic<V> h) {
		super(h, 1);
	}
}
