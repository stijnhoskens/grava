package grava.test.ninepuzzle;

import grava.edge.WeightedEdge;
import grava.search.Searchable;
import static grava.util.SetUtils.setOf;

import java.util.Arrays;
import java.util.Set;

public class NinePuzzleGenerator
		implements
		Searchable<NinePuzzleConfiguration, WeightedEdge<NinePuzzleConfiguration>> {

	@Override
	public Set<WeightedEdge<NinePuzzleConfiguration>> edgesOf(
			NinePuzzleConfiguration configuration) {
		return setOf(Arrays.stream(Direction.values())
				.map(d -> configuration.moveEmptySquare(d))
				.filter(c -> c != null)
				.map(c -> new WeightedEdge<>(configuration, c)));
	}
}
