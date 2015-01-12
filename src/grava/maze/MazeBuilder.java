package grava.maze;

import grava.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MazeBuilder<V extends Positioned> {

	private final Dimensions dimensions;
	private final Set<V> vertices;
	private final Function<? super Position, ? extends V> mapper;
	private final boolean withoutWalls;
	private final Set<MazeListener> ls = new HashSet<>();

	private MazeBuilder(Set<V> vertices, Dimensions dimensions,
			Function<? super Position, ? extends V> mapper, boolean withoutWalls) {
		this.vertices = vertices;
		this.dimensions = dimensions;
		this.mapper = mapper;
		this.withoutWalls = withoutWalls;
	}

	private MazeBuilder(Set<V> vertices, Dimensions dimensions,
			Function<? super Position, ? extends V> mapper) {
		this(vertices, dimensions, mapper, true);
	}

	/**
	 * Specifies the resulting maze to have no edges, meaning all vertices are
	 * isolated.
	 * 
	 * @return a builder for a maze without edges
	 */
	public MazeBuilder<V> withAllWalls() {
		return new MazeBuilder<>(vertices, dimensions, mapper, false);
	}

	/**
	 * Ensures the given listeners are included in the returned maze. This
	 * ensures the mazeCreation method of MazeListener is invoked correctly.
	 * 
	 * @param listeners
	 *            the listeners to be included
	 * @return a MazeBuilder where the given listeners are included
	 */
	public MazeBuilder<V> withListeners(MazeListener... listeners) {
		MazeBuilder<V> builder = new MazeBuilder<>(vertices, dimensions,
				mapper, withoutWalls);
		builder.addListeners(listeners);
		builder.addListeners(ls);
		return builder;
	}

	private void addListeners(MazeListener... listeners) {
		Arrays.stream(listeners).forEach(ls::add);
	}

	private void addListeners(Collection<MazeListener> listeners) {
		listeners.forEach(ls::add);
	}

	/**
	 * Returns a mapped maze. This maze is open (all neighbouring vertices are
	 * connected), unless otherwise specified.
	 * 
	 * @see withoutEdges
	 * @return a mapped maze
	 */
	public MappedMaze<V> mapped() {
		MappedMaze<V> maze = withoutWalls ? new MappedMaze<>(dimensions.width,
				dimensions.height, mapper) : new MappedMaze<>(vertices,
				Collections.emptySet());
		ls.forEach(maze::addListener);
		ls.forEach(l -> l.mazeCreated(dimensions.width, dimensions.height,
				withoutWalls));
		return maze;
	}

	/**
	 * Returns a matrix maze. This maze is open (all neighbouring vertices are
	 * connected), unless otherwise specified.
	 * 
	 * @see withoutEdges
	 * @return a matrix maze
	 */
	public MatrixMaze<V> matrix() {
		MatrixMaze<V> maze = new MatrixMaze<V>(dimensions.width,
				dimensions.height, vertices, withoutWalls);
		ls.forEach(maze::addListener);
		ls.forEach(l -> l.mazeCreated(dimensions.width, dimensions.height,
				withoutWalls));
		return maze;
	}

	private static class Dimensions {
		final int width;
		final int height;

		Dimensions(int w, int h) {
			width = w;
			height = h;
		}
	}

	/**
	 * Returns a builder from which a rectangular maze can be built. This maze
	 * will have the specified width and height. Coordinates of the vertices
	 * will be (0,0) and up.
	 * 
	 * @param <T>
	 * 
	 * @param width
	 *            the width of the maze to be built
	 * @param height
	 *            the height
	 * @param mapper
	 *            creates a vertex from a given position.
	 * @return a builder from which the rectangular maze can be built.
	 */
	public static <T extends Positioned> MazeBuilder<T> rectangular(int width,
			int height, Function<? super Position, ? extends T> mapper) {
		Stream<Position> stream = IntStream
				.range(0, width)
				.boxed()
				.flatMap(
						x -> IntStream.range(0, height).boxed()
								.map(y -> new Position(x, y)));
		Set<T> vertices = CollectionUtils.setOf(stream.map(mapper));
		return new MazeBuilder<>(vertices, new Dimensions(width, height),
				mapper);
	}

	/**
	 * Returns a builder from which a square maze can be built. This maze will
	 * have the specified size as width and height. Coordinates of the vertices
	 * will be (0,0) and up.
	 * 
	 * @param <T>
	 * 
	 * @param size
	 *            the width and height of the maze to be built
	 * @param mapper
	 *            creates a vertex from a given position.
	 * @return a builder from which the square maze can be built.
	 */
	public static <T extends Positioned> MazeBuilder<T> square(int size,
			Function<? super Position, ? extends T> mapper) {
		return rectangular(size, size, mapper);
	}

}
