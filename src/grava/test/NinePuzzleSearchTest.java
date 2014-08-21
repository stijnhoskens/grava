package grava.test;

import static org.junit.Assert.assertTrue;
import grava.edge.Edge;
import grava.search.SearchStrategy;
import grava.search.blind.BreadthFirst;
import grava.search.blind.IterativeDeepening;
import grava.test.ninepuzzle.NinePuzzleConfiguration;
import grava.test.ninepuzzle.NinePuzzleGenerator;
import grava.walk.Walk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Implementation not to be tested here (due to the large subspace, 10! =
 * 3628800 states): depth first, non deterministic (due to its unpredictable
 * character)
 */
public class NinePuzzleSearchTest {

	private static NinePuzzleGenerator graph;
	private static List<Pair> configs = new ArrayList<>();
	private SearchStrategy<NinePuzzleConfiguration, Edge<NinePuzzleConfiguration>> search;

	@BeforeClass
	public static void beforeClass() {
		graph = new NinePuzzleGenerator();
		// The end state is the start state
		configs.add(new Pair("trivial", new NinePuzzleConfiguration(
				new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } })));
		// Termination can be reached in one step
		configs.add(new Pair("easy", new NinePuzzleConfiguration(new int[][] {
				{ 1, 0, 2 }, { 3, 4, 5 }, { 6, 7, 8 } })));
		configs.add(new Pair("random_easy", new NinePuzzleConfiguration(
				new int[][] { { 1, 2, 0 }, { 7, 4, 3 }, { 8, 6, 5 } })));
		configs.add(new Pair("random_hard", new NinePuzzleConfiguration(
				new int[][] { { 1, 8, 3 }, { 2, 0, 5 }, { 7, 4, 6 } })));
	}

	@Test
	public void testBreadthFirst() {
		search = new BreadthFirst<>();
	}

	@Test
	public void testIterativeDeepening() {
		search = new IterativeDeepening<>();
	}

	@After
	public void testSuccessfulSearch() {
		System.out.println(search.getClass().getSimpleName() + ": ");
		configs.forEach(pair -> {
			long start = System.currentTimeMillis();
			Optional<Walk<NinePuzzleConfiguration, Edge<NinePuzzleConfiguration>>> optional = getPath(pair
					.getConfig());
			assertTrue(optional.isPresent());
			// optional.ifPresent(path -> print(path));
			long diff = System.currentTimeMillis() - start;
			System.out.println("- " + pair.getName() + ": " + diff + "ms");
		});
	}

	private Optional<Walk<NinePuzzleConfiguration, Edge<NinePuzzleConfiguration>>> getPath(
			NinePuzzleConfiguration config) {
		return search.findPath(graph, config, puzzle -> puzzle.isCorrect());
	}

	private static class Pair {
		private final String name;
		private final NinePuzzleConfiguration config;

		public Pair(String name, NinePuzzleConfiguration config) {
			this.name = name;
			this.config = config;
		}

		public String getName() {
			return this.name;
		}

		public NinePuzzleConfiguration getConfig() {
			return this.config;
		}
	}

	public static void print(
			Walk<NinePuzzleConfiguration, Edge<NinePuzzleConfiguration>> walk) {
		String print = "";
		for (NinePuzzleConfiguration c : walk.getVertices())
			print += " -> " + c.toString();
		System.out.println(print.substring(4));
	}
}