package grava.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import grava.edge.WeightedArc;
import grava.exceptions.IllegalDimensionException;
import grava.exceptions.LoopException;
import grava.graph.MatrixGraph;
import static grava.util.CollectionUtils.setOf;

import org.junit.Before;
import org.junit.Test;

public class MatrixGraphTest {

	private final Node a = new Node("a"), b = new Node("b"), c = new Node("c"),
			d = new Node("d"), e = new Node("e"), f = new Node("f");
	private final List<Node> nodes = Arrays.asList(a, b, c, d, e, f);
	private final double[][] distanceMatrix = { { 0, 0, 2, 0, 1, 0 },
			{ 1, 0, 3, 0, 0, 4 }, { 2, 0, 0, 5, 0, 0 }, { 0, 1, 4, 0, 2, 0 },
			{ 7, 0, 0, 1, 0, 5 }, { 4, 0, 0, 0, 0, 0 } };
	private MatrixGraph<Node> graph;

	@Test
	public void testEdgesOf() {
		assertEquals(
				setOf(new WeightedArc<>(a, c, 2), new WeightedArc<>(a, e, 1)),
				graph.edgesOf(a));
		assertEquals(
				setOf(new WeightedArc<>(b, a, 1), new WeightedArc<>(b, c, 3),
						new WeightedArc<>(b, f, 4)), graph.edgesOf(b));
		assertEquals(
				setOf(new WeightedArc<>(c, a, 2), new WeightedArc<>(c, d, 5)),
				graph.edgesOf(c));
		assertEquals(
				setOf(new WeightedArc<>(d, b, 1), new WeightedArc<>(d, c, 4),
						new WeightedArc<>(d, e, 2)), graph.edgesOf(d));
		assertEquals(
				setOf(new WeightedArc<>(e, a, 7), new WeightedArc<>(e, d, 1),
						new WeightedArc<>(e, f, 5)), graph.edgesOf(e));
		assertEquals(setOf(new WeightedArc<>(f, a, 4)), graph.edgesOf(f));
	}

	@Before
	public void before() {
		graph = new MatrixGraph<>(nodes, distanceMatrix);
	}

	@Test(expected = IllegalDimensionException.class)
	public void testIllegalDimensionException() {
		new MatrixGraph<>(nodes, new double[][] { { 0, 1 }, { 1, 0 } });
	}

	@Test(expected = LoopException.class)
	public void testLoopException() {
		new MatrixGraph<>(nodes, new double[][] { { 1, 0, 2, 0, 1, 0 },
				{ 1, 0, 3, 0, 0, 4 }, { 2, 0, 0, 5, 0, 0 },
				{ 0, 1, 4, 0, 2, 0 }, { 7, 0, 0, 1, 0, 5 },
				{ 4, 0, 0, 0, 0, 0 } });
	}

}
