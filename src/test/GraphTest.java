package test;

import static org.junit.Assert.*;
import edge.WeightedEdge;
import graph.Graph;
import graph.GraphExplorer;
import graph.parser.GraphParser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import node.IdNode;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	private Graph<IdNode, WeightedEdge<IdNode>> graph;

	private final IdNode S = new IdNode("S"), A = new IdNode("A"),
			B = new IdNode("B"), C = new IdNode("C"), D = new IdNode("D"),
			E = new IdNode("E"), F = new IdNode("F"), G = new IdNode("G");

	private Set<IdNode> nodes;

	@Before
	public void setUp() throws Exception {
		nodes = new HashSet<IdNode>(Arrays.asList(S, A, B, C, D, E, F, G));
		graph = GraphParser.parse("./parser/graph");
	}

	@Test
	public void test_graphParsing() {
		GraphExplorer<IdNode, WeightedEdge<IdNode>> explorer = new GraphExplorer<>(
				graph, S);
		Map<IdNode, Set<WeightedEdge<IdNode>>> map = explorer.getNodeMapping();
		assertTrue(nodes.equals(map.keySet()));
	}

}
