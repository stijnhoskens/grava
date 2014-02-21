package graph.parser;

import edge.WeightedEdge;
import graph.MappedGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import node.IdNode;
import util.MultiMap;

public class GraphParser {

	private static Map<String, IdNode> nodes;

	/**
	 * Example file:
	 * 
	 * # This is a comment line, the first line gives all nodes, all possible
	 * strings allowed, separated by a ','. The following lines give all the
	 * connections between nodes, the second line shows A has an edge to B with
	 * weight 2, and so on.
	 * 
	 * A,B,C,D,E,F
	 * 
	 * A: B:2,C:5,F:2
	 * 
	 * B: C:2,F:8
	 * 
	 */
	public static MappedGraph<IdNode, WeightedEdge<IdNode>> parse(String path)
			throws IOException, IndexOutOfBoundsException {

		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = reader.readLine();
		while (isComment(line))
			// This line is a comment line.
			line = reader.readLine();
		// The next line contains all nodes.

		nodes = getNodes(line);
		MultiMap<IdNode, WeightedEdge<IdNode>> map = new MultiMap<>(
				nodes.values());
		
		for (line = reader.readLine(); line != null; line = reader.readLine()) {
			if (isComment(line))
				continue;
			int breakPoint = line.indexOf(":");
			String nodeName = line.substring(0, breakPoint).trim();
			String neighbors = line.substring(breakPoint + 1).trim();
			IdNode node = nodes.get(nodeName);
			map.get(node).addAll(getEdges(neighbors));
		}
		
		reader.close();
		return new MappedGraph<IdNode, WeightedEdge<IdNode>>(map);
	}

	private static Map<String, IdNode> getNodes(String string) {
		Map<String, IdNode> map = new HashMap<>();
		for (String token : string.split(","))
			map.put(token, new IdNode(token.trim()));
		return map;
	}

	private static boolean isComment(String line) {
		return line.startsWith("#") || line.trim().equals("");
	}

	private static Set<WeightedEdge<IdNode>> getEdges(String line) {
		Set<WeightedEdge<IdNode>> edges = new HashSet<>();
		for (String token : line.split(","))
			edges.add(getEdge(token));
		return edges;
	}

	private static WeightedEdge<IdNode> getEdge(String token) {
		String[] splitted = token.split(":");
		return new WeightedEdge<IdNode>(nodes.get(splitted[0].trim()),
				Double.parseDouble(splitted[1].trim()));
	}

	public static void main(String args[]) {
		try {
			parse("./parser/graph");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
