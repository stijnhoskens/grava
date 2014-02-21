package test;

import edge.WeightedEdge;
import graph.Graph;
import graph.parser.GraphParser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import node.IdNode;

import org.junit.Before;

public abstract class GraphTest {

	protected Graph<IdNode, WeightedEdge<IdNode>> graph;

	protected final IdNode S = new IdNode("S"), A = new IdNode("A"),
			B = new IdNode("B"), C = new IdNode("C"), D = new IdNode("D"),
			E = new IdNode("E"), F = new IdNode("F"), G = new IdNode("G");

	protected Set<IdNode> nodes;

	@Before
	public void setUp() throws Exception {
		nodes = asSet(S, A, B, C, D, E, F, G);
		graph = GraphParser.parse("./parser/graph");
	}

	public static Set<IdNode> asSet(IdNode... items) {
		return new HashSet<IdNode>(Arrays.asList(items));
	}

}
