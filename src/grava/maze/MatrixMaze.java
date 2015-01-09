package grava.maze;

import grava.edge.Edge;
import grava.util.CollectionUtils;

import java.util.Arrays;
import java.util.Set;

public class MatrixMaze<V extends Positioned> implements Maze<V> {

	private final V[][] data;
	private final boolean[][] horWalls;
	private final boolean[][] verWalls;

	@SuppressWarnings("unchecked")
	public MatrixMaze(int width, int height, Iterable<V> vertices) {
		data = (V[][]) new Object[width][height];
		vertices.forEach(v -> {
			Position p = v.getPosition();
			data[p.getX()][p.getY()] = v;
		});
		horWalls = new boolean[width][height - 1];
		Arrays.stream(horWalls).forEach(r -> Arrays.fill(r, false));
		verWalls = new boolean[width - 1][height];
		Arrays.stream(verWalls).forEach(r -> Arrays.fill(r, false));
	}

	@Override
	public Set<Edge<V>> edgesOf(V v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<V> getVertices() {
		return CollectionUtils.unmodifiableSetOf(Arrays.stream(data).flatMap(
				Arrays::stream));
	}

	@Override
	public void addWall(V v, Direction direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addWallBetween(V u, V v) {
		Direction direction = Direction.between(u.getPosition(),
				v.getPosition());
		if (direction != null)
			addWall(u, direction);
	}

	@Override
	public boolean removeWall(V v, Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeWallBetween(V u, V v) {
		Direction direction = Direction.between(u.getPosition(),
				v.getPosition());
		if (direction != null)
			return removeWall(u, direction);
		return false;
	}

	@Override
	public V vertexAt(Position p) {
		int x = p.getX(), y = p.getY();
		if (x >= width() || y >= height())
			return null;
		return data[x][y];
	}

	@Override
	public int width() {
		return data.length;
	}

	@Override
	public int height() {
		if (width() == 0)
			return 0;
		return data[0].length;
	}

}
