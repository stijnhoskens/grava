package grava.test;

import static org.junit.Assert.*;
import grava.maze.Direction;
import grava.maze.MappedMaze;
import grava.maze.MatrixMaze;
import grava.maze.Maze;
import grava.maze.MazeBuilder;
import grava.maze.MazeNode;
import grava.maze.Position;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class MazeTest {

	private Stream<Maze<MazeNode>> mazes;

	@Before
	public void before() {
		MazeBuilder<MazeNode> builder = MazeBuilder.rectangular(10, 20,
				MazeNode::new);
		MatrixMaze<MazeNode> matrix = builder.matrix();
		MappedMaze<MazeNode> mapped = builder.mapped();
		mazes = Stream.of(matrix, mapped);
	}

	@Test
	public void testBuilderCorrectness() {
		testEachMaze(m -> {
			assertEquals(10, m.width());
			assertEquals(20, m.height());
			assertEquals(200, m.getVertices().size());
			assertEquals(2, m.neighboursOf(m.vertexAt(new Position(0, 0)))
					.size());
			assertEquals(3, m.neighboursOf(m.vertexAt(new Position(0, 1)))
					.size());
			assertEquals(4, m.neighboursOf(m.vertexAt(new Position(1, 1)))
					.size());
			Position p = new Position(0, 0);
			assertEquals(p, m.vertexAt(p).getPosition());
		});
	}

	@Test
	public void testWallCheck() {
		testEachMaze(m -> {
			MazeNode node = m.vertexAt(new Position(1, 1));
			Arrays.stream(Direction.values()).forEach(d -> {
				assertFalse(m.hasWallAt(node, d));
			});
			MazeNode node2 = m.vertexAt(new Position(0, 0));
			assertTrue(m.hasWallAt(node2, Direction.DOWN));
			assertTrue(m.hasWallAt(node2, Direction.LEFT));
			assertFalse(m.hasWallAt(node2, Direction.UP));
			assertFalse(m.hasWallAt(node2, Direction.RIGHT));
		});
	}

	@Test
	public void testWallAddition() {
		testEachMaze(m -> {
			MazeNode node = m.vertexAt(new Position(1, 1));
			int nbOfNeighbours = 4;
			assertEquals(nbOfNeighbours, m.neighboursOf(node).size());
			for (Direction d : Direction.values()) {
				assertFalse(m.hasWallAt(node, d));
				m.addWallAt(node, d);
				assertTrue(m.hasWallAt(node, d));
				nbOfNeighbours--;
				assertEquals(nbOfNeighbours, m.neighboursOf(node).size());
			}
			node = m.vertexAt(new Position(2, 2));
			nbOfNeighbours = 4;
			for (Direction d : Direction.values()) {
				MazeNode adjacent = m.vertexAt(node.getPosition().neighbour(d));
				assertFalse(m.hasWallBetween(node, adjacent));
				m.addWallBetween(node, adjacent);
				assertTrue(m.hasWallBetween(node, adjacent));
				nbOfNeighbours--;
				assertEquals(nbOfNeighbours, m.neighboursOf(node).size());
			}
		});
	}

	@Test
	public void testWallRemoval() {
		testEachMaze(m -> {
			MazeNode node = m.vertexAt(new Position(1, 1));
			for (Direction d : Direction.values())
				m.addWallAt(node, d);
			int nbOfNeighbours = 0;
			assertEquals(nbOfNeighbours, m.neighboursOf(node).size());
			for (Direction d : Direction.values()) {
				assertTrue(m.hasWallAt(node, d));
				m.removeWallAt(node, d);
				assertFalse(m.hasWallAt(node, d));
				nbOfNeighbours++;
				assertEquals(nbOfNeighbours, m.neighboursOf(node).size());
			}
			node = m.vertexAt(new Position(2, 2));
			for (Direction d : Direction.values())
				m.addWallAt(node, d);
			nbOfNeighbours = 0;
			assertEquals(nbOfNeighbours, m.neighboursOf(node).size());
			for (Direction d : Direction.values()) {
				MazeNode adjacent = m.vertexAt(node.getPosition().neighbour(d));
				assertTrue(m.hasWallBetween(node, adjacent));
				m.removeWallBetween(node, adjacent);
				assertFalse(m.hasWallBetween(node, adjacent));
				nbOfNeighbours++;
				assertEquals(nbOfNeighbours, m.neighboursOf(node).size());
			}
		});
	}

	@Test
	public void benchmark() {
		System.out.println("BENCHMARK ON A " + BENCH_SIZE + "x" + BENCH_SIZE
				+ " MAZE");
		benchBuild();
		benchChecks();
		benchAddition();
		benchRemoval();
	}

	private static final int BENCH_SIZE = 200;

	private static void benchBuild() {
		System.out.println("Building a maze: ");
		System.out.println(" With all walls");
		Duration duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).withAllWalls()
					.matrix();
		});
		System.out.println("  MatrixMaze: " + duration.toMillis() + "ms");
		duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).withAllWalls()
					.mapped();
		});
		System.out.println("  MappedMazes: " + duration.toMillis() + "ms");
		System.out.println(" Without walls");
		duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).matrix();
		});
		System.out.println("  MatrixMaze: " + duration.toMillis() + "ms");
		duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).mapped();
		});
		System.out.println("  MappedMaze: " + duration.toMillis() + "ms");
	}

	private static void benchChecks() {
		System.out.println("Checking walls:");
		MatrixMaze<MazeNode> matrix = MazeBuilder.square(BENCH_SIZE,
				MazeNode::new).matrix();
		MappedMaze<MazeNode> mapped = MazeBuilder.square(BENCH_SIZE,
				MazeNode::new).mapped();
		Function<Maze<MazeNode>, Duration> f = m -> durationOf(() -> {
			m.getVertices().forEach(v -> {
				Arrays.stream(Direction.values()).forEach(d -> {
					m.hasWallAt(v, d);
				});
			});
		});
		System.out.println(" MatrixMaze: " + f.apply(matrix).toMillis() + "ms");
		System.out.println(" MappedMaze: " + f.apply(mapped).toMillis() + "ms");
	}

	private static void benchAddition() {
		System.out.println("Adding walls:");
		MatrixMaze<MazeNode> matrix = MazeBuilder.square(BENCH_SIZE,
				MazeNode::new).matrix();
		MappedMaze<MazeNode> mapped = MazeBuilder.square(BENCH_SIZE,
				MazeNode::new).mapped();
		Function<Maze<MazeNode>, Duration> f = m -> durationOf(() -> {
			m.getVertices().forEach(v -> {
				Arrays.stream(Direction.values()).forEach(d -> {
					m.addWallAt(v, d);
				});
			});
		});
		System.out.println(" MatrixMaze: " + f.apply(matrix).toMillis() + "ms");
		System.out.println(" MappedMaze: " + f.apply(mapped).toMillis() + "ms");
	}

	private static void benchRemoval() {
		System.out.println("Removing walls:");
		MatrixMaze<MazeNode> matrix = MazeBuilder
				.square(BENCH_SIZE, MazeNode::new).withAllWalls().matrix();
		MappedMaze<MazeNode> mapped = MazeBuilder
				.square(BENCH_SIZE, MazeNode::new).withAllWalls().mapped();
		Function<Maze<MazeNode>, Duration> f = m -> durationOf(() -> {
			m.getVertices().forEach(v -> {
				Arrays.stream(Direction.values()).forEach(d -> {
					m.removeWallAt(v, d);
				});
			});
		});
		System.out.println(" MatrixMaze: " + f.apply(matrix).toMillis() + "ms");
		System.out.println(" MappedMaze: " + f.apply(mapped).toMillis() + "ms");
	}

	private static Duration durationOf(Runnable r) {
		LocalTime start = LocalTime.now();
		r.run();
		return Duration.between(start, LocalTime.now());
	}

	private void testEachMaze(Consumer<Maze<MazeNode>> consumer) {
		mazes.forEach(consumer);
	}
}
