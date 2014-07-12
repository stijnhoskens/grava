package grava.test;

import static org.junit.Assert.assertEquals;
import grava.edge.Edge;
import grava.graph.ExplicitGraph;
import grava.graph.Graph;

import org.junit.Test;

public class ExplicitGraphTest extends GraphTestMethods {

	public ExplicitGraphTest() {
		super(new ExplicitGraph<>(), new ExplicitGraph<>());
	}

	@Test
	public void testConstructionUsingEdges() {
		Graph<Node, Edge<Node>> edgeGraph = new ExplicitGraph<>(edges);
		assertEquals(nodes, edgeGraph.getVertices());
	}

}
