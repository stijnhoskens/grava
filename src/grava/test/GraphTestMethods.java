package grava.test;

import static grava.util.SetUtils.setOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import grava.edge.Arc;
import grava.edge.Edge;
import grava.graph.Graph;

import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public abstract class GraphTestMethods {

	protected final Graph<Node, Edge<Node>> graph;
	protected final Graph<Node, Arc<Node>> digraph;

	protected final Node a = new Node("a"), b = new Node("b"),
			c = new Node("c"), d = new Node("d"), e = new Node("e"),
			f = new Node("f");
	protected final Set<Node> nodes = setOf(Stream.of(a, b, c, d, e, f));
	protected final Edge<Node> ab = new Edge<>(a, b), bc = new Edge<>(b, c),
			be = new Edge<>(b, e), ef = new Edge<>(e, f),
			cf = new Edge<>(c, f), cd = new Edge<>(c, d),
			df = new Edge<>(d, f);
	/*
	 * All arcs go from the lowest in lexicographic order to the highest.
	 */
	protected final Arc<Node> ab_arc = new Arc<>(a, b),
			bc_arc = new Arc<>(b, c), be_arc = new Arc<>(b, e),
			ef_arc = new Arc<>(e, f), cf_arc = new Arc<>(c, f),
			cd_arc = new Arc<>(c, d), df_arc = new Arc<>(d, f);
	protected final Set<Edge<Node>> edges = setOf(Stream.of(ab, bc, be, ef, cf,
			cd, df));
	protected final Set<Arc<Node>> arcs = setOf(Stream.of(ab_arc, bc_arc,
			be_arc, ef_arc, cf_arc, cd_arc, df_arc));

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
		assertEquals(nodes, graph.getVertices());
		assertEquals(edges, graph.getEdges());
		assertEquals(nodes, digraph.getVertices());
		assertEquals(arcs, digraph.getEdges());
	}

	@Test
	public void testVertexAdditionDeletion() {

		// Addition
		graph.addVertex(a);
		assertEquals(nodes, graph.getVertices());
		Node g = new Node("g");
		graph.addVertex(g);
		assertFalse(graph.getVertices().equals(nodes));
		assertTrue(graph.getVertices().containsAll(nodes));
		assertEquals(nodes.size() + 1, graph.getVertices().size());
		assertTrue(graph.getVertices().contains(g));
		assertTrue(graph.getVertices().contains(g));

		// Deletion
		assertTrue(graph.removeVertex(g));
		assertEquals(nodes, graph.getVertices());
		assertFalse(graph.removeVertex(g));

		// Deletion of a connected vertex
		assertTrue(graph.removeVertex(a));
		assertFalse(graph.areNeighbours(b, a));
	}

	@Test
	public void testEdgeAdditionDeletion() {

		// Addition
		graph.addEdge(ab);
		assertEquals(nodes, graph.getVertices());
		Edge<Node> ae = new Edge<>(a, e);
		graph.addEdge(ae);
		assertNotEquals(edges, graph.getEdges());
		assertTrue(graph.getEdges().containsAll(edges));
		assertEquals(edges.size() + 1, graph.getEdges().size());
		assertTrue(graph.getEdges().contains(ae));

		// Deletion method 1
		assertTrue(graph.removeEdge(ae));
		assertEquals(edges, graph.getEdges());
		assertFalse(graph.removeEdge(ae));

		// Deletion method 2
		graph.addEdge(ae);
		assertNotEquals(edges, graph.getEdges());
		assertTrue(graph.getEdges().containsAll(edges));
		assertEquals(edges.size() + 1, graph.getEdges().size());
		assertTrue(graph.getEdges().contains(ae));
		assertTrue(graph.removeEdgeBetween(a, e));
		assertEquals(edges, graph.getEdges());
		graph.addEdge(ae);
		assertNotEquals(edges, graph.getEdges());
		assertTrue(graph.removeEdgeBetween(e, a));
		assertEquals(edges, graph.getEdges());

		// Deletion method 2 for directed graphs
		Arc<Node> ae_arc = new Arc<>(a, e);
		digraph.addEdge(ae_arc);
		assertNotEquals(arcs, digraph.getEdges());
		assertTrue(digraph.getEdges().containsAll(arcs));
		assertEquals(arcs.size() + 1, digraph.getEdges().size());
		assertTrue(digraph.getEdges().contains(ae_arc));
		assertFalse(digraph.removeEdgeBetween(e, a));
		assertNotEquals(arcs, digraph.getEdges());
		assertTrue(digraph.removeEdgeBetween(a, e));
		assertEquals(arcs, digraph.getEdges());

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

		assertEquals(setOf(b), graph.neighboursOf(a));
		assertEquals(setOf(a, c, e), graph.neighboursOf(b));
		assertEquals(setOf(b, d, f), graph.neighboursOf(c));
		assertEquals(setOf(c, f), graph.neighboursOf(d));
		assertEquals(setOf(b, f), graph.neighboursOf(e));
		assertEquals(setOf(c, d, e), graph.neighboursOf(f));

		// Directed
		assertEquals(setOf(b), digraph.neighboursOf(a));
		assertEquals(setOf(c, e), digraph.neighboursOf(b));
		assertEquals(setOf(d, f), digraph.neighboursOf(c));
		assertEquals(setOf(f), digraph.neighboursOf(d));
		assertEquals(setOf(f), digraph.neighboursOf(e));
		assertEquals(setOf(), digraph.neighboursOf(f));
	}

	@Test
	public void testEdgesOf() {

		assertEquals(setOf(ab), graph.edgesOf(a));
		assertEquals(setOf(ab, bc, be), graph.edgesOf(b));
		assertEquals(setOf(bc, cd, cf), graph.edgesOf(c));
		assertEquals(setOf(cd, df), graph.edgesOf(d));
		assertEquals(setOf(be, ef), graph.edgesOf(e));
		assertEquals(setOf(cf, df, ef), graph.edgesOf(f));

		// Directed
		assertEquals(setOf(ab_arc), digraph.edgesOf(a));
		assertEquals(setOf(bc_arc, be_arc), digraph.edgesOf(b));
		assertEquals(setOf(cd_arc, cf_arc), digraph.edgesOf(c));
		assertEquals(setOf(df_arc), digraph.edgesOf(d));
		assertEquals(setOf(ef_arc), digraph.edgesOf(e));
		assertEquals(setOf(), digraph.edgesOf(f));

	}

	@Test
	public void testEdgeBetween() {

		nodes.forEach(n1 -> nodes.forEach(n2 -> {
			Optional<Edge<Node>> optional = graph.edgeBetween(n1, n2);
			if (graph.areNeighbours(n1, n2)) {
				assertTrue(optional.isPresent());
				Edge<Node> edge = optional.get();
				assertTrue(edges.contains(edge));
				assertTrue(edge.contains(n1));
				assertTrue(edge.contains(n2));
			} else
				assertFalse(optional.isPresent());
		}));

		nodes.forEach(n1 -> nodes.forEach(n2 -> {
			Optional<Arc<Node>> optional = digraph.edgeBetween(n1, n2);
			if (digraph.areNeighbours(n1, n2)) {
				assertTrue(optional.isPresent());
				Arc<Node> arc = optional.get();
				assertTrue(arcs.contains(arc));
				assertEquals(n1, arc.getTail());
				assertEquals(n2, arc.getHead());
			} else
				assertFalse(optional.isPresent());
		}));

	}

	@Test
	public void benchmarkEdgesOf() {
		IntStream.rangeClosed(1, 10000).forEach(i -> {
			nodes.stream().forEach(n -> {
				graph.edgesOf(n);
			});
		});
	}

	@Test
	public void benchmarkExplicitSetAccess() {
		IntStream.rangeClosed(1, 1000).forEach(i -> {
			nodes.stream().forEach(n -> {
				graph.getVertices();
				graph.getEdges();
			});
		});
	}
}
