package grava.maze;

import grava.edge.Edge;
import grava.search.Searchable;

public interface Maze<V extends Positioned> extends Searchable<V, Edge<V>> {

	boolean addWall(V v, Direction direction);

	boolean addWallBetween(V u, V v);

	boolean removeWall(V v, Direction direction);

	void removeWallBetween(V v, V u);

	V getVertexAt(Position p);

	int width();

	int height();

}
