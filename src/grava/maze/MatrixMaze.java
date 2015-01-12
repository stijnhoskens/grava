package grava.maze;

import grava.edge.Edge;
import grava.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class MatrixMaze<V extends Positioned> extends AbstractMaze<V> {

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
		if (!v.equals(vertexAt(v.getPosition())))
			return Collections.emptySet();
		Position p = v.getPosition();
		return CollectionUtils.setOf(Arrays.stream(Direction.values())
				.filter(d -> !hasWallAt(p, d)).map(v.getPosition()::neighbour)
				.map(this::vertexAt));
	}

	@Override
	public boolean hasWallAt(Position p, Direction dir) {
		if (exceedsDimensions(p.neighbour(dir)))
			return true;
		int x = p.getX(), y = p.getY();
		return dir.isHorizontal() ? verWalls[dir.increment() ? x : x - 1][y]
				: horWalls[x][dir.increment() ? y : y - 1];
	}

	@Override
	public boolean hasWallBetween(Position p, Position q) {
		Direction dir = Direction.between(p, q);
		if (dir == null)
			return true;
		return hasWallAt(p, dir);
	}

	@Override
	public Set<V> getVertices() {
		return CollectionUtils.unmodifiableSetOf(Arrays.stream(data).flatMap(
				Arrays::stream));
	}

	@Override
	public void addWallAt(Position p, Direction direction) {
		if (exceedsDimensions(p) || exceedsDimensions(p.neighbour(direction)))
			return;
		setWall(p, direction, true);
	}

	private void setWall(Position pos, Direction dir, boolean val) {
		int x = pos.getX(), y = pos.getY();
		if (dir.isHorizontal())
			setValue(verWalls, dir.increment() ? x : x - 1, y, val, pos, dir);
		else
			setValue(horWalls, x, dir.increment() ? y : y - 1, val, pos, dir);
	}

	private void setValue(boolean[][] walls, int x, int y, boolean val,
			Position p, Direction d) {
		boolean oldValue = walls[x][y];
		if (val && !oldValue) {
			informMazeListeners(l -> l.wallAdded(p, d));
			walls[x][y] = val;
		} else if (!val && oldValue) {
			informMazeListeners(l -> l.wallRemoved(p, d));
			walls[x][y] = val;
		}
	}

	@Override
	public void addWallBetween(Position p, Position q) {
		Direction direction = Direction.between(p, q);
		if (direction != null)
			addWallAt(p, direction);
	}

	@Override
	public boolean removeWallAt(Position p, Direction direction) {
		if (exceedsDimensions(p) || exceedsDimensions(p.neighbour(direction)))
			return false;
		if (!hasWallAt(p, direction))
			return false;
		setWall(p, direction, false);
		return true;
	}

	@Override
	public boolean removeWallBetween(Position p, Position q) {
		Direction direction = Direction.between(p, q);
		if (direction != null)
			return removeWallAt(p, direction);
		return false;
	}

	@Override
	public V vertexAt(Position p) {
		if (exceedsDimensions(p))
			return null;
		return data[p.getX()][p.getY()];
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
