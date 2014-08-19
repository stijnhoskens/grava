package grava.test;

import static org.junit.Assert.assertTrue;
import grava.edge.Edge;
import grava.search.SearchStrategy;
import grava.search.blind.DepthFirst;
import grava.test.ninepuzzle.NinePuzzleConfiguration;
import grava.test.ninepuzzle.NinePuzzleGenerator;

import org.junit.BeforeClass;
import org.junit.Test;

public class NinePuzzleSearchTest {

	private static NinePuzzleGenerator graph;
	private static NinePuzzleConfiguration trivialStart, easyStart;
	private static SearchStrategy<NinePuzzleConfiguration, Edge<NinePuzzleConfiguration>> search;

	@BeforeClass
	public static void beforeClass() {
		graph = new NinePuzzleGenerator();
		trivialStart = new NinePuzzleConfiguration(new int[][] { { 0, 1, 2 },
				{ 3, 4, 5 }, { 6, 7, 8 } });
		easyStart = new NinePuzzleConfiguration(new int[][] { { 1, 0, 2 },
				{ 3, 4, 5 }, { 6, 7, 8 } });
	}

	@Test
	public void testSearchMethods() {
		search = new DepthFirst<>();
		assertTrue(search.findPath(graph, trivialStart,
				puzzle -> puzzle.isCorrect()).isPresent());
		assertTrue(search.findPath(graph, easyStart,
				puzzle -> puzzle.isCorrect()).isPresent());

	}
}
