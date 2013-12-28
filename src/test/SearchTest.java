package test;

import static org.junit.Assert.assertEquals;
import graph.ExplicitGraph;

import node.IdNode;

import org.junit.BeforeClass;
import org.junit.Test;

import algorithm.BiDirectional;
import algorithm.BreadthFirst;
import algorithm.DepthFirst;
import algorithm.IterativeDeepening;
import algorithm.NonDeterministic;
import algorithm.Path;
import algorithm.Search;

public class SearchTest {
	
	private static final ExplicitGraph<IdNode> graph = new ExplicitGraph<IdNode>();
	private static Search<IdNode> search;
	
	private static IdNode S, A, B, C, D, E, F, G;
	
	private static Path<IdNode> foundPath;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		S = new IdNode("S");
		A = new IdNode("A");
		B = new IdNode("B");
		C = new IdNode("C");
		D = new IdNode("D");
		E = new IdNode("E");
		F = new IdNode("F");
		G = new IdNode("G");
		graph.addNode(S);
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		graph.addNode(D);
		graph.addNode(E);
		graph.addNode(F);
		graph.addNode(G);
		graph.addEdge(S, A, 3d);
		graph.addEdge(S, D, 4d);
		graph.addEdge(A, D, 5d);
		graph.addEdge(A, B, 4d);
		graph.addEdge(B, C, 4d);
		graph.addEdge(D, E, 2d);
		graph.addEdge(B, E, 5d);
		graph.addEdge(E, F, 4d);
		graph.addEdge(F, G, 3d);
	}

	@Test
	public void test_Blind() {
		search = new DepthFirst<IdNode>(graph, S, G);
		search.performSearch();
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search = new BreadthFirst<IdNode>(graph, S, G);
		search.performSearch();
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search = new NonDeterministic<IdNode>(graph, S, G);
		search.performSearch();
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search = new IterativeDeepening<IdNode>(graph, S, G);
		search.performSearch();
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
		search = new BiDirectional<IdNode>(graph, S, G);
		search.performSearch();
		foundPath = search.getPath();
		assertEquals(G, foundPath.getEndpoint());
	}

}
