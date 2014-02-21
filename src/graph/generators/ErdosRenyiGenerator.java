package graph.generators;

import java.util.Random;

import edge.Edge;
import graph.MappedGraph;
import node.NumberedNode;

public class ErdosRenyiGenerator {

	private NumberedNode[] nodes;

	private Random r = new Random();

	/**
	 * @param n
	 *            The number of nodes in the graph.
	 */
	public ErdosRenyiGenerator(int n) {
		generateNodes(n);
	}

	public MappedGraph<NumberedNode, Edge<NumberedNode>> generateWithP(double p) {
		assert p <= 1;
		MappedGraph<NumberedNode, Edge<NumberedNode>> graph = new MappedGraph<>();

		// makes sure all nodes are included.
		for (NumberedNode node : nodes)
			graph.addNode(node);

		for (int i = 0; i < getN(); i++) {
			NumberedNode node1 = nodes[i];
			for (int j = i + 1; j < getN(); j++) {
				if (r.nextDouble() > p)
					continue;
				NumberedNode node2 = nodes[j];
				graph.putEdge(node1, new Edge<>(node2));
				graph.putEdge(node2, new Edge<>(node1));
			}
		}

		return graph;
	}

	public NumberedNode[] getNodes() {
		return nodes.clone();
	}

	public int getN() {
		return nodes.length;
	}

	private void generateNodes(int n) {
		nodes = new NumberedNode[n];
		for (int i = 0; i < n; i++)
			nodes[i] = new NumberedNode(i);
	}

	public static void main(String[] args) {
		int n = 5;
		double p = 0.2;

		ErdosRenyiGenerator gen = new ErdosRenyiGenerator(n);
		MappedGraph<?, ?> graph = gen.generateWithP(p);
		
		// int totalPossibleEdges = (n * (n - 1)) / 2;
		// int edgesSize = graph.getEdges().size();
		// System.out.println(edgesSize);
		// int expected = (int) (p * totalPossibleEdges);
		// System.out.println(expected);

		System.out.println(graph.getEdges());

	}
}
