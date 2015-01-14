package grava.maze.generator;

import grava.maze.Direction;
import grava.maze.Maze;
import grava.maze.MazeBuilder;
import grava.maze.Position;
import grava.maze.Positioned;
import grava.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class RecursiveBacktracker<V extends Positioned> implements
		MazeGenerator<V> {

	private final Stack<Position> stack = new Stack<>();
	private final Set<Position> visited = new HashSet<>();

	private Maze<V> maze;

	@Override
	public Maze<V> apply(MazeBuilder<V> builder) {
		maze = builder.withAllWalls().build();
		Position seed = new Position(0, 0);
		stack.push(seed);
		visited.add(seed);
		Random r = new Random();
		while (!stack.isEmpty()) {
			Position p = stack.pop();
			List<Position> nextPositions = nextPossibilities(p);
			if (nextPositions.isEmpty())
				continue;
			Position next = nextPositions.get(r.nextInt(nextPositions.size()));
			stack.push(p);
			stack.push(next);
			visited.add(next);
			maze.removeWallBetween(p, next);
		}
		return maze;
	}

	private List<Position> nextPossibilities(Position p) {
		return CollectionUtils.listOf(Arrays.stream(Direction.values())
				.map(p::neighbour)
				.filter(pos -> !isVisited(pos) && maze.contains(pos)));
	}

	private boolean isVisited(Position p) {
		return visited.contains(p);
	}

}
