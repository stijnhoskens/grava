package grava.test;

import static org.junit.Assert.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import grava.edge.Arc;
import grava.edge.Edge;
import grava.graph.Graph;

public abstract class GraphTestMethods {

	protected final Graph<Node, Edge<Node>> graph;
	protected final Graph<Node, Arc<Node>> digraph;

	protected final Node a = new Node("a"), b = new Node("b"),
			c = new Node("c"), d = new Node("d"), e = new Node("e"),
			f = new Node("f");
	protected final Set<Node> nodes = Stream.of(a, b, c, d, e, f).collect(
			Collectors.toSet());
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
	protected final Set<Edge<Node>> edges = Stream.of(ab, bc, be, ef, cf, cd,
			df).collect(Collectors.toSet());
	protected final Set<Arc<Node>> arcs = Stream.of(ab_arc, bc_arc, be_arc,
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
		assertTrue(graph.containsVertex(g));

		// Deletion
		assertTrue(graph.removeVertex(g));
		assertEquals(nodes, graph.getVertices());
		assertFalse(graph.removeVertex(g));

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

		Set<Edge<Node>> edgesOfA = graph.edgesOf(a);
		assertTrue(edgesOfA.remove(ab));
		assertEquals(0, edgesOfA.size());
		Set<Edge<Node>> edgesOfB = graph.edgesOf(b);
		assertTrue(edgesOfB.remove(ab));
		assertTrue(edgesOfB.remove(bc));
		assertTrue(edgesOfB.remove(be));
		assertEquals(0, edgesOfB.size());
		Set<Edge<Node>> edgesOfC = graph.edgesOf(c);
		assertTrue(edgesOfC.remove(bc));
		assertTrue(edgesOfC.remove(cd));
		assertTrue(edgesOfC.remove(cf));
		assertEquals(0, edgesOfC.size());
		Set<Edge<Node>> edgesOfD = graph.edgesOf(d);
		assertTrue(edgesOfD.remove(cd));
		assertTrue(edgesOfD.remove(df));
		assertEquals(0, edgesOfD.size());
		Set<Edge<Node>> edgesOfE = graph.edgesOf(e);
		assertTrue(edgesOfE.remove(be));
		assertTrue(edgesOfE.remove(ef));
		assertEquals(0, edgesOfE.size());
		Set<Edge<Node>> edgesOfF = graph.edgesOf(f);
		assertTrue(edgesOfF.remove(cf));
		assertTrue(edgesOfF.remove(df));
		assertTrue(edgesOfF.remove(ef));
		assertEquals(0, edgesOfF.size());

		// Directed
		Set<Arc<Node>> arcsOfA = digraph.edgesOf(a);
		assertTrue(arcsOfA.remove(ab_arc));
		assertEquals(0, arcsOfA.size());
		Set<Arc<Node>> arcsOfB = digraph.edgesOf(b);
		assertTrue(arcsOfB.remove(bc_arc));
		assertTrue(arcsOfB.remove(be_arc));
		assertEquals(0, arcsOfB.size());
		Set<Arc<Node>> arcsOfC = digraph.edgesOf(c);
		assertTrue(arcsOfC.remove(cd_arc));
		assertTrue(arcsOfC.remove(cf_arc));
		assertEquals(0, arcsOfC.size());
		Set<Arc<Node>> arcsOfD = digraph.edgesOf(d);
		assertTrue(arcsOfD.remove(df_arc));
		assertEquals(0, arcsOfD.size());
		Set<Arc<Node>> arcsOfE = digraph.edgesOf(e);
		assertTrue(arcsOfE.remove(ef_arc));
		assertEquals(0, arcsOfE.size());
		Set<Arc<Node>> arcsOfF = digraph.edgesOf(f);
		assertEquals(0, arcsOfF.size());

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
			}
			else
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
			}
			else
				assertFalse(optional.isPresent());
		}));
		
	}
}
