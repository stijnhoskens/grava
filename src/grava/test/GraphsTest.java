package grava.test;

import static grava.util.CollectionUtils.setOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grava.edge.Arc;
import grava.edge.Edge;
import grava.graph.ExplicitGraph;
import grava.graph.Graph;
import grava.graph.Graphs;

import org.junit.Test;

public class GraphsTest extends WithNodesAndEdges {

	private final Graph<Node, Edge<Node>> empty_graph = new ExplicitGraph<>();
	private final Graph<Node, Edge<Node>> total_graph = new ExplicitGraph<>(
			nodes, edges);
	private final Graph<Node, Edge<Node>> ab_graph = new ExplicitGraph<>(setOf(
			a, b), setOf(ab));
	private final Graph<Node, Edge<Node>> cdf_graph = new ExplicitGraph<>(
			setOf(c, d, f), setOf(cd, df, cf));
	private final Graph<Node, Arc<Node>> ab_arc_graph = new ExplicitGraph<>(
			setOf(a, b), setOf(ab_arc));

	@Test
	public void testNullGraph() {
		assertTrue(Graphs.isNullGraph(empty_graph));
		assertFalse(Graphs.isNullGraph(total_graph));
	}

	@Test
	public void testCompleteness() {
		assertFalse(Graphs.isComplete(total_graph));
		assertTrue(Graphs.isComplete(ab_graph));
		assertTrue(Graphs.isComplete(cdf_graph));
		assertFalse(Graphs.isComplete(ab_arc_graph));
	}

	@Test
	public void testEquals() {
		assertFalse(ab_graph
				.equals(new ExplicitGraph<>(setOf(a, b), setOf(ab))));
		assertTrue(Graphs.equals(ab_graph, new ExplicitGraph<>(setOf(a, b),
				setOf(ab))));
		assertFalse(Graphs.equals(ab_graph, cdf_graph));
	}

	@Test
	public void testSubGraphs() {

		assertTrue(Graphs.isSubgraphOf(ab_graph, total_graph));
		assertFalse(Graphs.isSubgraphOf(ab_graph, cdf_graph));

		assertTrue(Graphs.equals(ab_graph,
				Graphs.subgraphInducedByVertices(setOf(a, b), total_graph)));
		assertFalse(Graphs.equals(ab_graph,
				Graphs.subgraphInducedByVertices(setOf(c, f, a), total_graph)));

		assertTrue(Graphs.equals(ab_graph,
				Graphs.subgraphInducedByEdges(setOf(ab), total_graph)));
		assertFalse(Graphs.equals(ab_graph,
				Graphs.subgraphInducedByEdges(setOf(be, ef), total_graph)));
	}

	// TODO test is(Weakly)Connected
}
