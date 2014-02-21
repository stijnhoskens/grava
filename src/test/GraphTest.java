package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import edge.WeightedEdge;
import graph.Graph;
import graph.GraphExplorer;
import graph.parser.GraphParser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import node.IdNode;

import org.junit.Before;

import util.MultiMap;

public abstract class GraphTest {

	protected Graph<IdNode, WeightedEdge<IdNode>> graph;

	protected final IdNode S = new IdNode("S"), A = new IdNode("A"),
			B = new IdNode("B"), C = new IdNode("C"), D = new IdNode("D"),
			E = new IdNode("E"), F = new IdNode("F"), G = new IdNode("G");

	protected Set<IdNode> nodes;

	@Before
	public void setUp() throws Exception {
		graph = GraphParser.parse("./parser/graph");
		nodes = asSet(S, A, B, C, D, E, F, G);
	}

	@SafeVarargs
	public static <T> Set<T> asSet(T... items) {
		return new HashSet<T>(Arrays.asList(items));
	}

	public void testCorrectness(Graph<IdNode, WeightedEdge<IdNode>> graph) {
		hasCorrectNodes(graph);
		hasCorrectEdges(graph, S,
				asSet(new EdgeTuple(A, 3), new EdgeTuple(D, 4)));
		hasCorrectEdges(graph, A,
				asSet(new EdgeTuple(B, 4), new EdgeTuple(D, 5)));
		hasCorrectEdges(graph, B,
				asSet(new EdgeTuple(C, 4), new EdgeTuple(E, 5)));
		hasCorrectEdges(graph, E, asSet(new EdgeTuple(F, 2)));
		hasCorrectEdges(graph, F, asSet(new EdgeTuple(G, 3)));
	}

	protected void hasCorrectNodes(Graph<IdNode, WeightedEdge<IdNode>> graph) {
		GraphExplorer<IdNode, WeightedEdge<IdNode>> explorer = new GraphExplorer<>(
				graph, S);
		MultiMap<IdNode, WeightedEdge<IdNode>> map = explorer.getNodeMapping();
		assertTrue(nodes.equals(map.keySet()));
	}

	protected void hasCorrectEdges(Graph<IdNode, WeightedEdge<IdNode>> graph,
			IdNode node, Set<EdgeTuple> tuples) {
		Set<WeightedEdge<IdNode>> edges = graph.getEdgesFrom(node);
		assertEquals(tuples.size(), edges.size());
		for (EdgeTuple tuple : tuples)
			assertTrue(containsEdge(edges, tuple));
	}

	protected static boolean containsEdge(Set<WeightedEdge<IdNode>> edges,
			EdgeTuple tuple) {
		for (WeightedEdge<IdNode> edge : edges)
			if (tuple.equals(edge))
				return true;
		return false;
	}

	protected static class EdgeTuple {

		private final IdNode node;
		private final double cost;

		public EdgeTuple(IdNode node, double cost) {
			this.node = node;
			this.cost = cost;
		}

		public boolean equals(WeightedEdge<IdNode> edge) {
			return node.equals(edge.getDestination()) && cost == edge.getCost();
		}
	}

}
