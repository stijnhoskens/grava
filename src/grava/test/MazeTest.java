package grava.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grava.maze.Direction;
import grava.maze.MappedMaze;
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
		Maze<MazeNode> matrix = builder.build();
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
			Position p = new Position(1, 1);
			Arrays.stream(Direction.values()).forEach(d -> {
				assertFalse(m.hasWallAt(p, d));
			});
			Position q = new Position(0, 0);
			assertTrue(m.hasWallAt(q, Direction.DOWN));
			assertTrue(m.hasWallAt(q, Direction.LEFT));
			assertFalse(m.hasWallAt(q, Direction.UP));
			assertFalse(m.hasWallAt(q, Direction.RIGHT));
		});
	}

	@Test
	public void testWallAddition() {
		testEachMaze(m -> {
			Position p = new Position(1, 1);
			int nbOfNeighbours = 4;
			assertEquals(nbOfNeighbours, m.neighboursOf(m.vertexAt(p)).size());
			for (Direction d : Direction.values()) {
				assertFalse(m.hasWallAt(p, d));
				m.addWallAt(p, d);
				assertTrue(m.hasWallAt(p, d));
				nbOfNeighbours--;
				assertEquals(nbOfNeighbours, m.neighboursOf(m.vertexAt(p))
						.size());
			}
			p = new Position(2, 2);
			nbOfNeighbours = 4;
			for (Direction d : Direction.values()) {
				Position q = p.neighbour(d);
				assertFalse(m.hasWallBetween(p, q));
				m.addWallBetween(p, q);
				assertTrue(m.hasWallBetween(p, q));
				nbOfNeighbours--;
				assertEquals(nbOfNeighbours, m.neighboursOf(m.vertexAt(p))
						.size());
			}
		});
	}

	@Test
	public void testWallRemoval() {
		testEachMaze(m -> {
			Position p = new Position(1, 1);
			for (Direction d : Direction.values())
				m.addWallAt(p, d);
			int nbOfNeighbours = 0;
			assertEquals(nbOfNeighbours, m.neighboursOf(m.vertexAt(p)).size());
			for (Direction d : Direction.values()) {
				assertTrue(m.hasWallAt(p, d));
				m.removeWallAt(p, d);
				assertFalse(m.hasWallAt(p, d));
				nbOfNeighbours++;
				assertEquals(nbOfNeighbours, m.neighboursOf(m.vertexAt(p))
						.size());
			}
			p = new Position(2, 2);
			for (Direction d : Direction.values())
				m.addWallAt(p, d);
			nbOfNeighbours = 0;
			assertEquals(nbOfNeighbours, m.neighboursOf(m.vertexAt(p)).size());
			for (Direction d : Direction.values()) {
				Position adjacent = p.neighbour(d);
				assertTrue(m.hasWallBetween(p, adjacent));
				m.removeWallBetween(p, adjacent);
				assertFalse(m.hasWallBetween(p, adjacent));
				nbOfNeighbours++;
				assertEquals(nbOfNeighbours, m.neighboursOf(m.vertexAt(p))
						.size());
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
		System.out.println("Building the maze: ");
		System.out.println(" With all walls");
		Duration duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).withAllWalls()
					.build();
		});
		System.out.println("  MatrixMaze: " + duration.toMillis() + "ms");
		duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).withAllWalls()
					.mapped();
		});
		System.out.println("  MappedMazes: " + duration.toMillis() + "ms");
		System.out.println(" Without walls");
		duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).build();
		});
		System.out.println("  MatrixMaze: " + duration.toMillis() + "ms");
		duration = durationOf(() -> {
			MazeBuilder.square(BENCH_SIZE, MazeNode::new).mapped();
		});
		System.out.println("  MappedMaze: " + duration.toMillis() + "ms");
	}

	private static void benchChecks() {
		System.out.println("Checking walls:");
		Maze<MazeNode> matrix = MazeBuilder.square(BENCH_SIZE, MazeNode::new)
				.build();
		MappedMaze<MazeNode> mapped = MazeBuilder.square(BENCH_SIZE,
				MazeNode::new).mapped();
		Function<Maze<MazeNode>, Duration> f = m -> durationOf(() -> {
			m.getVertices().forEach(v -> {
				Arrays.stream(Direction.values()).forEach(d -> {
					m.hasWallAt(v.getPosition(), d);
				});
			});
		});
		System.out.println(" MatrixMaze: " + f.apply(matrix).toMillis() + "ms");
		System.out.println(" MappedMaze: " + f.apply(mapped).toMillis() + "ms");
	}

	private static void benchAddition() {
		System.out.println("Adding walls:");
		Maze<MazeNode> matrix = MazeBuilder.square(BENCH_SIZE, MazeNode::new)
				.build();
		MappedMaze<MazeNode> mapped = MazeBuilder.square(BENCH_SIZE,
				MazeNode::new).mapped();
		Function<Maze<MazeNode>, Duration> f = m -> durationOf(() -> {
			m.getVertices().forEach(v -> {
				Arrays.stream(Direction.values()).forEach(d -> {
					m.addWallAt(v.getPosition(), d);
				});
			});
		});
		System.out.println(" MatrixMaze: " + f.apply(matrix).toMillis() + "ms");
		System.out.println(" MappedMaze: " + f.apply(mapped).toMillis() + "ms");
	}

	private static void benchRemoval() {
		System.out.println("Removing walls:");
		Maze<MazeNode> matrix = MazeBuilder.square(BENCH_SIZE, MazeNode::new)
				.withAllWalls().build();
		MappedMaze<MazeNode> mapped = MazeBuilder
				.square(BENCH_SIZE, MazeNode::new).withAllWalls().mapped();
		Function<Maze<MazeNode>, Duration> f = m -> durationOf(() -> {
			m.getVertices().forEach(v -> {
				Arrays.stream(Direction.values()).forEach(d -> {
					m.removeWallAt(v.getPosition(), d);
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
