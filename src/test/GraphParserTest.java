package test;

import static org.junit.Assert.assertTrue;
import edge.WeightedEdge;
import graph.GraphExplorer;

import java.util.Set;

import node.IdNode;

import org.junit.Test;

import util.MultiMap;

public class GraphParserTest extends GraphTest {

	@Test
	public void test_graphParsing() {
		GraphExplorer<IdNode, WeightedEdge<IdNode>> explorer = new GraphExplorer<>(
				graph, S);
		MultiMap<IdNode, WeightedEdge<IdNode>> map = explorer.getNodeMapping();
		assertTrue(nodes.equals(map.keySet()));
		
		assertTrue(map.count(S) == 2);
		Set<WeightedEdge<IdNode>> edgesOfS = map.get(S);
		assertTrue(containsEdge(edgesOfS, A, 3));
		assertTrue(containsEdge(edgesOfS, D, 4));
		
		assertTrue(map.count(A) == 2);
		Set<WeightedEdge<IdNode>> edgesOfA = map.get(A);
		assertTrue(containsEdge(edgesOfA, B, 4));
		assertTrue(containsEdge(edgesOfA, D, 5));
		
		assertTrue(map.count(B) == 2);
		Set<WeightedEdge<IdNode>> edgesOfB = map.get(B);
		assertTrue(containsEdge(edgesOfB, C, 4));
		assertTrue(containsEdge(edgesOfB, E, 5));
		
		assertTrue(map.count(E) == 1);
		Set<WeightedEdge<IdNode>> edgesOfE = map.get(E);
		assertTrue(containsEdge(edgesOfE, F, 2));
		
		assertTrue(map.count(F) == 1);
		Set<WeightedEdge<IdNode>> edgesOfF = map.get(F);
		assertTrue(containsEdge(edgesOfF, G, 3));
	}

	public static boolean containsEdge(Set<WeightedEdge<IdNode>> edges,
			IdNode node, double cost) {
		for (WeightedEdge<IdNode> edge : edges)
			if (edge.getDestination().equals(node) && edge.getCost() == cost)
				return true;
		return false;
	}

}
