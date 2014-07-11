package grava.test;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import grava.edge.Arc;
import grava.edge.Edge;
import grava.graph.Graph;

public abstract class GraphTestMethods {

	private final Graph<Node, Edge<Node>> graph;
	private final Graph<Node, Arc<Node>> digraph;

	private final Node a = new Node("a"), b = new Node("b"), c = new Node("c"),
			d = new Node("d"), e = new Node("e"), f = new Node("f");
	private final Set<Node> nodes = Stream.of(a, b, c, d, e, f).collect(
			Collectors.toSet());
	private final Edge<Node> ab = new Edge<>(a, b), bc = new Edge<>(b, c),
			be = new Edge<>(b, e), ef = new Edge<>(e, f),
			cf = new Edge<>(c, f), cd = new Edge<>(c, d),
			df = new Edge<>(d, f);
	/*
	 * All arcs go from the lowest in lexicographic order to the highest.
	 */
	private final Arc<Node> ab_arc = new Arc<>(a, b), bc_arc = new Arc<>(b, c),
			be_arc = new Arc<>(b, e), ef_arc = new Arc<>(e, f),
			cf_arc = new Arc<>(c, f), cd_arc = new Arc<>(c, d),
			df_arc = new Arc<>(d, f);
	private final Set<Edge<Node>> edges = Stream.of(ab, bc, be, ef, cf, cd, df)
			.collect(Collectors.toSet());
	private final Set<Arc<Node>> arcs = Stream.of(ab_arc, bc_arc, be_arc,
			ef_arc, cf_arc, cd_arc, df_arc).collect(Collectors.toSet());;

	/**
	 * Use this constructor to provide implementations of the graph interfaces
	 * as they will be tested by several test methods in this test case.
	 * 
	 * @param graph
	 *            An empty graph containing no vertices nor edges.
	 * @param digraph
	 *            An empty digraph containing no vertices nor edges.
	 */
	public GraphTestMethods(Graph<Node, Edge<Node>> graph,
			Graph<Node, Arc<Node>> digraph) {
		this.graph = graph;
		this.digraph = digraph;
	}

	@Before
	public void before() {
		nodes.forEach(n -> graph.addVertex(n));
		edges.forEach(e -> graph.addEdge(e));
		nodes.forEach(n -> digraph.addVertex(n));
		arcs.forEach(e -> digraph.addEdge(e));
	}

	@Test
	public void testContainment() {
		assertTrue(graph.getVertices().equals(nodes));
		assertTrue(graph.getEdges().equals(edges));
		assertTrue(digraph.getVertices().equals(nodes));
		assertTrue(digraph.getEdges().equals(arcs));
	}

	@Test
	public void testVertexAdditionDeletion() {

		// Addition
		graph.addVertex(a);
		assertTrue(graph.getVertices().equals(nodes));
		Node g = new Node("g");
		graph.addVertex(g);
		assertFalse(graph.getVertices().equals(nodes));
		assertTrue(graph.getVertices().containsAll(nodes));
		assertEquals(nodes.size() + 1, graph.getVertices().size());
		assertTrue(graph.getVertices().contains(g));
		assertTrue(graph.containsVertex(g));

		// Deletion
		assertTrue(graph.removeVertex(g));
		assertTrue(graph.getVertices().equals(nodes));
		assertFalse(graph.removeVertex(g));

	}

	@Test
	public void testEdgeAdditionDeletion() {

		// Addition
		graph.addEdge(ab);
		assertTrue(graph.getVertices().equals(nodes));
		Edge<Node> ae = new Edge<>(a, e);
		graph.addEdge(ae);
		assertFalse(graph.getEdges().equals(edges));
		assertTrue(graph.getEdges().containsAll(edges));
		assertEquals(edges.size() + 1, graph.getEdges().size());
		assertTrue(graph.getEdges().contains(ae));

		// Deletion method 1
		assertTrue(graph.removeEdge(ae));
		assertTrue(graph.getEdges().equals(edges));
		assertFalse(graph.removeEdge(ae));

		// Deletion method 2
		graph.addEdge(ae);
		assertFalse(graph.getEdges().equals(edges));
		assertTrue(graph.getEdges().containsAll(edges));
		assertEquals(edges.size() + 1, graph.getEdges().size());
		assertTrue(graph.getEdges().contains(ae));
		assertTrue(graph.removeEdgeBetween(a, e));
		assertTrue(graph.getEdges().equals(edges));
		graph.addEdge(ae);
		assertFalse(graph.getEdges().equals(edges));
		assertTrue(graph.removeEdgeBetween(e, a));
		assertTrue(graph.getEdges().equals(edges));

		// Deletion method 2 for directed graphs
		Arc<Node> ae_arc = new Arc<>(a, e);
		digraph.addEdge(ae_arc);
		assertFalse(digraph.getEdges().equals(arcs));
		assertTrue(digraph.getEdges().containsAll(arcs));
		assertEquals(arcs.size() + 1, digraph.getEdges().size());
		assertTrue(digraph.getEdges().contains(ae_arc));
		assertFalse(digraph.removeEdgeBetween(e, a));
		assertFalse(digraph.getEdges().equals(arcs));
		assertTrue(digraph.removeEdgeBetween(a, e));
		assertTrue(digraph.getEdges().equals(arcs));

	}

	@Test
	public void testNeighbourship() {

		assertTrue(graph.areNeighbours(a, b));
		assertTrue(graph.areNeighbours(b, c));
		assertTrue(graph.areNeighbours(b, e));
		assertTrue(graph.areNeighbours(e, f));
		assertTrue(graph.areNeighbours(c, f));
		assertTrue(graph.areNeighbours(c, d));
		assertTrue(graph.areNeighbours(d, f));

		assertTrue(graph.areNeighbours(b, a));
		assertTrue(graph.areNeighbours(c, b));
		assertTrue(graph.areNeighbours(e, b));
		assertTrue(graph.areNeighbours(f, e));
		assertTrue(graph.areNeighbours(f, c));
		assertTrue(graph.areNeighbours(d, c));
		assertTrue(graph.areNeighbours(f, d));

		assertFalse(graph.areNeighbours(a, a));
		int nbOfTotalNeighbourships = 0;
		for (Node n1 : nodes)
			for (Node n2 : nodes)
				if (graph.areNeighbours(n1, n2))
					nbOfTotalNeighbourships++;
		assertEquals(2 * edges.size(), nbOfTotalNeighbourships);

		// Directed Graph
		assertTrue(digraph.areNeighbours(a, b));
		assertTrue(digraph.areNeighbours(b, c));
		assertTrue(digraph.areNeighbours(b, e));
		assertTrue(digraph.areNeighbours(e, f));
		assertTrue(digraph.areNeighbours(c, f));
		assertTrue(digraph.areNeighbours(c, d));
		assertTrue(digraph.areNeighbours(d, f));

		assertFalse(digraph.areNeighbours(b, a));
		assertFalse(digraph.areNeighbours(c, b));
		assertFalse(digraph.areNeighbours(e, b));
		assertFalse(digraph.areNeighbours(f, e));
		assertFalse(digraph.areNeighbours(f, c));
		assertFalse(digraph.areNeighbours(d, c));
		assertFalse(digraph.areNeighbours(f, d));

		assertFalse(digraph.areNeighbours(a, a));

		int nbOfTotalDirectedNeighbourships = 0;
		for (Node n1 : nodes)
			for (Node n2 : nodes)
				if (digraph.areNeighbours(n1, n2))
					nbOfTotalDirectedNeighbourships++;
		assertEquals(arcs.size(), nbOfTotalDirectedNeighbourships);
	}

	@Test
	public void testNeighbourhood() {
		
		Set<Node> neighboursOfA = graph.neighboursOf(a);
		assertTrue(neighboursOfA.remove(b));
		assertEquals(0, neighboursOfA.size());
		Set<Node> neighboursOfB = graph.neighboursOf(b);
		assertTrue(neighboursOfB.remove(a));
		assertTrue(neighboursOfB.remove(c));
		assertTrue(neighboursOfB.remove(e));
		assertEquals(0, neighboursOfB.size());
		Set<Node> neighboursOfC = graph.neighboursOf(c);
		assertTrue(neighboursOfC.remove(b));
		assertTrue(neighboursOfC.remove(d));
		assertTrue(neighboursOfC.remove(f));
		assertEquals(0, neighboursOfC.size());
		Set<Node> neighboursOfD = graph.neighboursOf(d);
		assertTrue(neighboursOfD.remove(c));
		assertTrue(neighboursOfD.remove(f));
		assertEquals(0, neighboursOfD.size());
		Set<Node> neighboursOfE = graph.neighboursOf(e);
		assertTrue(neighboursOfE.remove(b));
		assertTrue(neighboursOfE.remove(f));
		assertEquals(0, neighboursOfE.size());
		Set<Node> neighboursOfF = graph.neighboursOf(f);
		assertTrue(neighboursOfF.remove(c));
		assertTrue(neighboursOfF.remove(d));
		assertTrue(neighboursOfF.remove(e));
		assertEquals(0, neighboursOfF.size());
		
		// Directed
		Set<Node> directedNeighboursOfA = digraph.neighboursOf(a);
		assertTrue(directedNeighboursOfA.remove(b));
		assertEquals(0, directedNeighboursOfA.size());
		Set<Node> directedNeighboursOfB = digraph.neighboursOf(b);
		assertTrue(directedNeighboursOfB.remove(c));
		assertTrue(directedNeighboursOfB.remove(e));
		assertEquals(0, directedNeighboursOfB.size());
		Set<Node> directedNeighboursOfC = digraph.neighboursOf(c);
		assertTrue(directedNeighboursOfC.remove(d));
		assertTrue(directedNeighboursOfC.remove(f));
		assertEquals(0, directedNeighboursOfC.size());
		Set<Node> directedNeighboursOfD = digraph.neighboursOf(d);
		assertTrue(directedNeighboursOfD.remove(f));
		assertEquals(0, directedNeighboursOfD.size());
		Set<Node> directedNeighboursOfE = digraph.neighboursOf(e);
		assertTrue(directedNeighboursOfE.remove(f));
		assertEquals(0, directedNeighboursOfE.size());
		Set<Node> directedNeighboursOfF = digraph.neighboursOf(f);
		assertEquals(0, directedNeighboursOfF.size());
	}
	
	@Test
	public void testEdgesOf() {
		
	}
}
