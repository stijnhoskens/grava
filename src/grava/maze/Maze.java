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
	 * Adds a wall between p and the position in the given direction. This means
	 * the vertices at those positions are no longer reachable from one another.
	 * If a wall was already standing there, or if no position is available in
	 * the given direction, this method has no effect.
	 * 
	 * @param p
	 *            the starting position
	 * @param direction
	 *            the direction where the wall is going to stand
	 */
	void addWallAt(Position p, Direction direction);

	/**
	 * Adds a wall between p and q. This means the vertices at those positions
	 * are no longer reachable from one another. If a wall was already standing
	 * there, or if one of the given positions is not in the maze, this method
	 * has no effect.
	 * 
	 * @param p
	 *            the first position
	 * @param q
	 *            the second
	 */
	void addWallBetween(Position p, Position q);

	/**
	 * Returns true iff there is a wall adjacent to p in the given direction.
	 * 
	 * @param p
	 *            the position adjacent to the supposed wall
	 * @param direction
	 *            the direction in which there could be a wall
	 * @return true iff there is a wall
	 */
	boolean hasWallAt(Position p, Direction direction);

	/**
	 * Returns true if there is a wall between p and q. In general, returns true
	 * if the vertices at both positions are unreachable from one another. So if
	 * they are not adjacent, it will also return true.
	 * 
	 * @param p
	 *            the first position
	 * @param q
	 *            the second
	 * @return true iff there is a wall
	 */
	boolean hasWallBetween(Position p, Position q);

	/**
	 * Removes the wall between p and the position in the given direction.
	 * Returns true iff a wall was present, and is now removed.
	 * 
	 * @param p
	 *            the starting position
	 * @param direction
	 *            the direction where the wall is going to be deleted
	 * @return true iff the deletion is successful
	 */
	boolean removeWallAt(Position p, Direction direction);

	/**
	 * Removes the wall between p and q. Returns true iff a wall was present,
	 * and is now removed.
	 * 
	 * @param p
	 *            the first position
	 * @param q
	 *            the second
	 * @return true iff the deletion is successful
	 */
	boolean removeWallBetween(Position p, Position q);

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

	/**
	 * Adds the specified maze listener to the maze. These listeners can be used
	 * to monitor wall additions and removals. Typical use is a graphical
	 * interface.
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	void addListener(MazeListener listener);

	/**
	 * Removes the specified maze listener.
	 * 
	 * @param listener
	 *            the listener to be removed
	 */
	void removeListener(MazeListener listener);

}
