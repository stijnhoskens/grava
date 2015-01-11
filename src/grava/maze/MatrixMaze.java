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
	MatrixMaze(int width, int height, Iterable<V> vertices, boolean withoutWalls) {
		data = (V[][]) new Positioned[width][height];
		vertices.forEach(v -> {
			Position p = v.getPosition();
			data[p.getX()][p.getY()] = v;
		});
		horWalls = new boolean[width][height - 1];
		Arrays.stream(horWalls).forEach(r -> Arrays.fill(r, !withoutWalls));
		verWalls = new boolean[width - 1][height];
		Arrays.stream(verWalls).forEach(r -> Arrays.fill(r, !withoutWalls));
	}

	public MatrixMaze(int width, int height, Iterable<V> vertices) {
		this(width, height, vertices, true);
	}

	@Override
	public Set<Edge<V>> edgesOf(V v) {
		return CollectionUtils.setOf(neighboursOf(v).stream().map(
				u -> new Edge<>(v, u)));
	}

	@Override
	public Set<V> neighboursOf(V v) {
		return CollectionUtils.setOf(Arrays.stream(Direction.values())
				.filter(d -> !hasWallAt(v, d)).map(v.getPosition()::neighbour)
				.map(this::vertexAt));
	}

	@Override
	public boolean hasWallAt(V v, Direction dir) {
		Position pos = v.getPosition();
		if (exceedsDimensions(pos.neighbour(dir)))
			return true;
		int x = pos.getX(), y = pos.getY();
		if (dir.isHorizontal())
			return verWalls[dir.increment() ? x : x - 1][y];
		else
			return horWalls[x][dir.increment() ? y : y - 1];
	}

	@Override
	public boolean hasWallBetween(V u, V v) {
		Direction dir = Direction.between(u.getPosition(), v.getPosition());
		if (dir == null)
			return true;
		return hasWallAt(u, dir);
	}

	@Override
	public Set<V> getVertices() {
		return CollectionUtils.unmodifiableSetOf(Arrays.stream(data).flatMap(
				Arrays::stream));
	}

	@Override
	public void addWallAt(V v, Direction direction) {
		Position pOfV = v.getPosition();
		if (exceedsDimensions(pOfV)
				|| exceedsDimensions(pOfV.neighbour(direction)))
			return;
		setWall(pOfV, direction, true);
	}

	private void setWall(Position pos, Direction dir, boolean val) {
		int x = pos.getX(), y = pos.getY();
		if (dir.isHorizontal())
			verWalls[dir.increment() ? x : x - 1][y] = val;
		else
			horWalls[x][dir.increment() ? y : y - 1] = val;
	}

	@Override
	public void addWallBetween(V u, V v) {
		Direction direction = Direction.between(u.getPosition(),
				v.getPosition());
		if (direction != null)
			addWallAt(u, direction);
	}

	@Override
	public boolean removeWallAt(V v, Direction direction) {
		Position pOfV = v.getPosition();
		if (exceedsDimensions(pOfV)
				|| exceedsDimensions(pOfV.neighbour(direction)))
			return false;
		if (!hasWallAt(v, direction))
			return false;
		setWall(pOfV, direction, false);
		return true;

	}

	@Override
	public boolean removeWallBetween(V u, V v) {
		Direction direction = Direction.between(u.getPosition(),
				v.getPosition());
		if (direction != null)
			return removeWallAt(u, direction);
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

	private boolean exceedsDimensions(Position p) {
		return exceedsWidth(p) || exceedsHeight(p);
	}

	private boolean exceedsWidth(Position p) {
		return p.getX() < 0 || p.getX() >= width();
	}

	private boolean exceedsHeight(Position p) {
		return p.getY() < 0 || p.getY() >= height();
	}

}
