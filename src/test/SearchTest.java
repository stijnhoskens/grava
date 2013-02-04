package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import algorithm.Path;
import algorithm.Search;
import algorithm.SearchMethod;

import graph.Graph;
import graph.Node;

public class SearchTest {
	
	private static final Graph<Node> graph = new Graph<Node>();
	private static Search<Node> search;
	
	private static Node S, A, B, C, D, E, F, G;
	
	private static Path<Node> foundPath;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		S = new Node("S");
		A = new Node("A");
		B = new Node("B");
		C = new Node("C");
		D = new Node("D");
		E = new Node("E");
		F = new Node("F");
		G = new Node("G");
		graph.addNode(S);
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		graph.addNode(D);
		graph.addNode(E);
		graph.addNode(F);
		graph.addNode(G);
		graph.addConnection(S, A, 3d);
		graph.addConnection(S, D, 4d);
		graph.addConnection(A, D, 5d);
		graph.addConnection(A, B, 4d);
		graph.addConnection(B, C, 4d);
		graph.addConnection(D, E, 2d);
		graph.addConnection(B, E, 5d);
		graph.addConnection(E, F, 4d);
		graph.addConnection(F, G, 3d);
		search = new Search<Node>(graph, S, G);
	}

	@Test
	public void test_Blind() {
		search.performSearch(SearchMethod.depthFirst);
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search.performSearch(SearchMethod.breadthFirst);
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search.performSearch(SearchMethod.nonDeterministic);
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search.performSearch(SearchMethod.iterativeDeepening);
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search.performSearch(SearchMethod.biDirectional);
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
	}

}
