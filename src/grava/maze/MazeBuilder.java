package grava.maze;

import grava.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MazeBuilder<V extends Positioned> {

	private final Dimensions dimensions;
	private final Set<V> vertices;
	private final boolean withoutWalls;

	private MazeBuilder(Set<V> vertices, Dimensions dimensions,
			boolean withoutWalls) {
		this.vertices = vertices;
		this.dimensions = dimensions;
		this.withoutWalls = withoutWalls;
	}

	private MazeBuilder(Set<V> vertices, Dimensions dimensions) {
		this(vertices, dimensions, true);
	}

	/**
	 * Specifies the resulting maze to have no edges, meaning all vertices are
	 * isolated.
	 * 
	 * @return a builder for a maze without edges
	 */
	public MazeBuilder<V> withAllWalls() {
		return new MazeBuilder<>(vertices, dimensions, false);
	}

	/**
	 * Returns a mapped maze. This maze is open (all neighbouring vertices are
	 * connected), unless otherwise specified.
	 * 
	 * @see withoutEdges
	 * @return a mapped maze
	 */
	public MappedMaze<V> mapped() {
		return withoutWalls ? new MappedMaze<>(vertices) : new MappedMaze<>(
				vertices, Collections.emptySet());
	}

	/**
	 * Returns a matrix maze. This maze is open (all neighbouring vertices are
	 * connected), unless otherwise specified.
	 * 
	 * @see withoutEdges
	 * @return a matrix maze
	 */
	public MatrixMaze<V> matrix() {
		return new MatrixMaze<V>(dimensions.width, dimensions.height, vertices,
				withoutWalls);
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
		return new MazeBuilder<>(vertices, new Dimensions(width, height));
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
