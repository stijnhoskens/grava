package grava.maze;

import java.util.Set;

import grava.edge.Edge;
import grava.search.Searchable;

/**
 * A maze consisting of several vertices in a 2D grid. The set of vertices is
 * said to be immutable after creation, meaning no method specified in this
 * interface adds or removes a vertex.
 * 
 * @param <V>
 *            the type of vertices in the maze
 */
public interface Maze<V extends Positioned> extends Searchable<V, Edge<V>> {

	/**
	 * Returns the set of vertices contained in this maze. This set is assumed
	 * to be unmodifiable.
	 * 
	 * @return the set of vertices.
	 */
	Set<V> getVertices();

	/**
	 * Returns the set of all neighbours of the given vertices. These are all
	 * vertices adjacent to v and without a wall in between. This set is assumed
	 * to be unmodifiable.
	 * 
	 * @param v
	 *            the vertex whose neighbours are to be retrieved
	 * @return the set of all neighbours of the given vertex
	 */
	Set<V> neighboursOf(V v);

	/**
	 * Adds a wall between v and the vertex in the given direction. This means
	 * those vertices are no longer reachable from one another. If a wall was
	 * already standing there, or if no vertex is available in the given
	 * direction, this method has no effect.
	 * 
	 * @param v
	 *            the starting vertex
	 * @param direction
	 *            the direction where the wall is going to stand
	 */
	void addWallAt(V v, Direction direction);

	/**
	 * Adds a wall between u and v. This means those vertices are no longer
	 * reachable from one another. If a wall was already standing there, or if
	 * one of the given vertices is not in the maze, this method has no effect.
	 * 
	 * @param u
	 *            the first vertex
	 * @param v
	 *            the second
	 */
	void addWallBetween(V u, V v);

	/**
	 * Returns true iff there is a wall adjacent to v in the given direction.
	 * 
	 * @param v
	 *            the vertex
	 * @param direction
	 *            the direction in which there could be a wall
	 * @return true iff there is a wall
	 */
	boolean hasWallAt(V v, Direction direction);

	/**
	 * Returns true if there is a wall between u and v. In general, returns true
	 * if both vertices are unreachable from one another. So if they are not
	 * adjacent, it will also return true.
	 * 
	 * @param u
	 *            the first vertex
	 * @param v
	 *            the second
	 * @return true iff there is a wall
	 */
	boolean hasWallBetween(V u, V v);

	/**
	 * Removes the wall between v and the vertex in the given direction.
	 * 
	 * Returns true iff a wall was present, and is now removed.
	 * 
	 * @param v
	 *            the starting vertex
	 * @param direction
	 *            the direction where the wall is going to be deleted
	 * @return true iff the deletion is successful
	 */
	boolean removeWallAt(V v, Direction direction);

	/**
	 * Removes the wall between u and v.
	 * 
	 * Returns true iff a wall was present, and is now removed.
	 * 
	 * @param u
	 *            the first vertex
	 * @param v
	 *            the second
	 * @return true iff the deletion is successful
	 */
	boolean removeWallBetween(V u, V v);

	/**
	 * Returns the vertex at position p, or null if there is no such vertex.
	 * 
	 * @param p
	 *            The position of the required vertex
	 * @return The required vertex
	 */
	V vertexAt(Position p);

	/**
	 * Returns the width of the maze. If this maze contains no nodes, zero is
	 * returned.
	 * 
	 * @return the width of the maze.
	 */
	int width();

	/**
	 * Returns the height of the maze. If this maze contains no nodes, zero is
	 * returned.
	 * 
	 * @return the width of the maze.
	 */
	int height();

}
