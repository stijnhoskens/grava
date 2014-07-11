package grava.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import grava.edge.Edge;
import grava.graph.Graph;
import grava.graph.MappedGraph;

public class MappedGraphTest extends GraphTestMethods {

	public MappedGraphTest() {
		super(new MappedGraph<>(), new MappedGraph<>());
	}

	@Test
	public void testConstructionUsingEdges() {
		Graph<Node, Edge<Node>> edgeGraph = new MappedGraph<>(edges);
		assertEquals(nodes, edgeGraph.getVertices());
	}

}
