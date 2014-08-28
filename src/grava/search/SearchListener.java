package grava.search;

import grava.edge.Link;
import grava.walk.Walk;

public interface SearchListener<V, E extends Link<V>> {

	void walkExplored(Walk<V, E> walk);

}
