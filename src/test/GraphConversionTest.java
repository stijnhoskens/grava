package test;

import edge.WeightedEdge;
import graph.AdjacencyGraph;
import graph.ExplicitGraph;
import node.IdNode;

import org.junit.Test;

public class GraphConversionTest extends GraphTest {

	@Test
	public void test_conversion() {
		ExplicitGraph<IdNode, WeightedEdge<IdNode>> explicit = new ExplicitGraph<>(
				graph, S);
		testCorrectness(explicit);
		AdjacencyGraph<IdNode> adjacency = new AdjacencyGraph<>(graph, S);
		testCorrectness(adjacency);
	}

}
