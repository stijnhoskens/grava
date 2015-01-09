package grava.maze;

import grava.util.CollectionUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MazeBuilder<V extends Positioned> {

	private final Set<V> vertices;

	private MazeBuilder(Set<V> vertices) {
		this.vertices = vertices;
	}

	public MappedMaze<V> withoutEdges() {
		return new MappedMaze<>(vertices);
	}

	public MappedMaze<V> withoutWalls() {
		MappedMaze<V> maze = new MappedMaze<>(vertices);
		vertices.forEach(v -> Arrays.stream(Direction.values()).forEach(
				d -> maze.removeWall(v, d)));
		return maze;
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
		return new MazeBuilder<>(vertices);
	}

	public static <T extends Positioned> MazeBuilder<T> square(int size,
			Function<Position, T> mapper) {
		return rectangular(size, size, mapper);
	}

	public static void main(String[] args) {
		MappedMaze<MazeNode> maze = MazeBuilder.square(1000, MazeNode::new)
				.withoutWalls();
		System.out.println(maze.getEdges().size());
	}

}
