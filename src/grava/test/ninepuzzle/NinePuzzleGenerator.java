package grava.test.ninepuzzle;

import java.util.Arrays;
import java.util.Set;

import grava.edge.Edge;
import grava.search.Searchable;
import grava.util.SetUtils;

public class NinePuzzleGenerator implements
		Searchable<NinePuzzleConfiguration, Edge<NinePuzzleConfiguration>> {

	@Override
	public Set<Edge<NinePuzzleConfiguration>> edgesOf(
			NinePuzzleConfiguration configuration) {
		return SetUtils.setOf(Arrays.stream(Direction.values())
				.map(d -> configuration.moveEmptySquare(d))
				.filter(c -> c != null).map(c -> new Edge<>(configuration, c)));
	}
}
