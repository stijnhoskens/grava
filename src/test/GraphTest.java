package test;

import edge.ExplicitEdge;
import graph.ExplicitGraph;
import graph.Graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import node.IdNode;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	private Graph<?,?> graph;

	private final IdNode S = new IdNode("S"), A = new IdNode("A"),
			B = new IdNode("B"), C = new IdNode("C"), D = new IdNode("D"),
			E = new IdNode("E"), F = new IdNode("F"), G = new IdNode("G");
	
	private Set<IdNode> nodes;
	
	private Set<ExplicitEdge<IdNode>> edges = new HashSet<>();

	@Before
	public void setUp() throws Exception { 
		nodes = new HashSet<>(Arrays.asList(S,A,B,C,D,E,F,G));
		edges.add(new ExplicitEdge<IdNode>(S, A, 3));
		edges.add(new ExplicitEdge<IdNode>(S, D, 4));
		edges.add(new ExplicitEdge<IdNode>(A, D, 5));
		edges.add(new ExplicitEdge<IdNode>(A, B, 4));
		edges.add(new ExplicitEdge<IdNode>(D, E, 2));
		edges.add(new ExplicitEdge<IdNode>(B, E, 5));
		edges.add(new ExplicitEdge<IdNode>(B, C, 4));
		edges.add(new ExplicitEdge<IdNode>(E, F, 4));
		edges.add(new ExplicitEdge<IdNode>(F, G, 3));
		graph = new ExplicitGraph<>(nodes, edges);
	}

	@Test
	public void test_GraphConversion() {
		
	}

}
