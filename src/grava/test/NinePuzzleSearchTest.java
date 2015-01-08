package grava.test;

import static org.junit.Assert.assertTrue;
import grava.edge.WeightedEdge;
import grava.search.SearchStrategy;
import grava.search.blind.BreadthFirst;
import grava.search.heuristic.GreedySearch;
import grava.search.heuristic.Heuristic;
import grava.search.heuristic.HillClimbing1;
import grava.search.optimal.AStar;
import grava.search.optimal.IDAStar;
import grava.test.ninepuzzle.NinePuzzleConfiguration;
import grava.test.ninepuzzle.NinePuzzleGenerator;
import grava.util.Pair;
import grava.walk.Walk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class NinePuzzleSearchTest {

	private static NinePuzzleGenerator graph;
	private static List<Pair<String, NinePuzzleConfiguration>> configs = new ArrayList<>();
	private SearchStrategy<NinePuzzleConfiguration, WeightedEdge<NinePuzzleConfiguration>> search;

	@BeforeClass
	public static void beforeClass() {
		graph = new NinePuzzleGenerator();
		// The end state is the start state
		configs.add(new Pair<>("trivial", new NinePuzzleConfiguration(
				new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } })));
		// Termination can be reached in one step
		configs.add(new Pair<>("easy", new NinePuzzleConfiguration(new int[][] {
				{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } })));
		configs.add(new Pair<>("medium", new NinePuzzleConfiguration(
				new int[][] { { 1, 2, 0 }, { 7, 4, 3 }, { 8, 6, 5 } })));
		configs.add(new Pair<>("hard", new NinePuzzleConfiguration(new int[][] {
				{ 1, 8, 3 }, { 2, 0, 5 }, { 7, 4, 6 } })));
	}

	@Test
	public void testBreadthFirst() {
		search = new BreadthFirst<>();
	}

	@Test
	public void testGreedySearch() {
		search = new GreedySearch<>(manhattan());
	}

	@Test
	public void testHillClimbing1() {
		search = new HillClimbing1<>(manhattan());
	}

	@Test
	public void testAStar() {
		search = new AStar<>(manhattan(), true);
	}

	@Test
	public void testIDAStar() {
		search = new IDAStar<>(manhattan());
	}

	@After
	public void testSuccessfulSearch() {
		System.out.println(search.getClass().getSimpleName() + ": ");
		configs.forEach(pair -> {
			long start = System.currentTimeMillis();
			Optional<Walk<NinePuzzleConfiguration, WeightedEdge<NinePuzzleConfiguration>>> optional = getPath(pair
					.getSecond());
			assertTrue(optional.isPresent());
			// optional.ifPresent(path -> print(path));
			long diff = System.currentTimeMillis() - start;
			System.out.println("- " + pair.getFirst() + ": " + diff + "ms");
		});
	}

	private static Heuristic<NinePuzzleConfiguration> manhattan() {
		return NinePuzzleConfiguration.manhattanHeuristic();
	}

	private Optional<Walk<NinePuzzleConfiguration, WeightedEdge<NinePuzzleConfiguration>>> getPath(
			NinePuzzleConfiguration config) {
		return search.findPath(graph, config,
				NinePuzzleConfiguration::isCorrect);
	}

	public static void print(
			Walk<NinePuzzleConfiguration, WeightedEdge<NinePuzzleConfiguration>> walk) {
		String print = "";
		for (NinePuzzleConfiguration c : walk.getVertices())
			print += " -> " + c.toString();
		System.out.println(print.substring(4));
	}
}
