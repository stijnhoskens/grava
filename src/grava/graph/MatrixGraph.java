package grava.graph;

import grava.edge.WeightedArc;
import grava.exceptions.IllegalDimensionException;
import grava.exceptions.LoopException;
import grava.search.Searchable;
import grava.util.DistanceMatrix;
import static grava.util.SetUtils.setOf;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class MatrixGraph<V> implements Searchable<V, WeightedArc<V>> {

	private final List<V> vertices;
	private final DistanceMatrix matrix;

	public MatrixGraph(List<V> vertices, DistanceMatrix matrix)
			throws IllegalDimensionException {
		if (vertices.size() != matrix.size())
			throw new IllegalDimensionException(vertices.size(), matrix.size());
		for (int i = 0; i < matrix.size(); i++)
			if (matrix.value(i, i) != DistanceMatrix.UNREACHABLE)
				throw new LoopException(vertices.get(i));
		this.vertices = vertices;
		this.matrix = matrix;
	}

	public MatrixGraph(List<V> vertices, double[][] matrix)
			throws IllegalDimensionException {
		this(vertices, new DistanceMatrix(matrix));
	}

	@Override
	public Set<WeightedArc<V>> edgesOf(V v) {
		int i = vertices.indexOf(v);
		if (i == -1)
			return Collections.emptySet();
		double[] row = matrix.getRow(i);
		return setOf(IntStream.range(0, row.length)
				.filter(j -> row[j] != DistanceMatrix.UNREACHABLE)
				.mapToObj(j -> new WeightedArc<V>(v, vertices.get(j), row[j])));
	}
}