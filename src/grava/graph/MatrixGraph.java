package grava.graph;

import grava.edge.WeightedArc;
import grava.exceptions.IllegalDimensionException;
import grava.exceptions.LoopException;
import grava.search.Searchable;
import grava.util.DistanceMatrix;
import static grava.util.SetUtils.setOf;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * A matrix graph is a graph specified by a certain distance matrix. It only
 * implements the searchable interface, so actions like addition or deletion of
 * vertices is not supported. The distance matrix which has to be specified
 * during construction can be non-symmetrical.
 *
 * @param <V>
 *            the type of vertices
 */
public class MatrixGraph<V> implements Searchable<V, WeightedArc<V>> {

	private final List<V> vertices;
	private final DistanceMatrix matrix;

	/**
	 * Constructs a matrix graph specified by the given distance matrix, and the
	 * given list of vertices, which corresponds to the entries in the distance
	 * matrix.
	 * 
	 * @param vertices
	 *            The list of vertices in the graph
	 * @param matrix
	 *            The distance matrix specifying the distance between vertices.
	 *            For instance matrix.value(0,1) indicates the distance between
	 *            vertices.get(0) and vertices.get(1).
	 * @throws IllegalDimensionException
	 *             When the specified distance matrix is non-square.
	 * @throws LoopException
	 *             When the specified distance matrix contains nonzero entries
	 *             on its main diagonal, indicating that the graph contains
	 *             loops.
	 */
	public MatrixGraph(List<V> vertices, DistanceMatrix matrix)
			throws IllegalDimensionException, LoopException {
		if (vertices.size() != matrix.size())
			throw new IllegalDimensionException(vertices.size(), matrix.size());
		for (int i = 0; i < matrix.size(); i++)
			if (matrix.value(i, i) != DistanceMatrix.UNREACHABLE)
				throw new LoopException(vertices.get(i));
		this.vertices = vertices;
		this.matrix = matrix;
	}

	/**
	 * /** Constructs a matrix graph specified by the given distance matrix as a
	 * array of arrays, and the given list of vertices, which corresponds to the
	 * entries in the distance matrix.
	 * 
	 * @param vertices
	 *            The list of vertices in the graph
	 * @param matrix
	 *            The distance matrix as an array of arrays specifying the
	 *            distance between vertices. For instance matrix[0][1] indicates
	 *            the distance between vertices.get(0) and vertices.get(1).
	 * @throws IllegalDimensionException
	 *             When the specified distance matrix is non-square.
	 * @throws LoopException
	 *             When the specified distance matrix contains nonzero entries
	 *             on its main diagonal, indicating that the graph contains
	 *             loops.
	 */
	public MatrixGraph(List<V> vertices, double[][] matrix)
			throws IllegalDimensionException, LoopException {
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

	/**
	 * Returns a graph representing this searchable.
	 */
	public Graph<V, WeightedArc<V>> toGraph() {
		return new MappedGraph<>(new HashSet<>(vertices), setOf(vertices
				.stream().flatMap(v -> edgesOf(v).stream())));
	}
}