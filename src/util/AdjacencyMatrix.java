package util;

import java.util.Arrays;

/**
 * Matrix to represent the adjacency matrix, as specified in
 * http://en.wikipedia.org/wiki/Adjacency_matrix. The entry in row i and column
 * j corresponds to the cost from node i to node j. If two nodes i & j are
 * unreachable from each other, get(i,j) returns Double.POSITIVE_INFINITY.
 * 
 * @author Stijn
 * 
 */
public class AdjacencyMatrix {

	private final double[][] matrix;

	/**
	 * @pre Entries for unreachable edges should have Double.POSITIVE_INFITY as
	 *      its value.
	 * @pre matrix has to be square.
	 */
	public AdjacencyMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public AdjacencyMatrix(int size) {
		this.matrix = new double[size][size];
		for (double[] row : matrix)
			Arrays.fill(row, Double.POSITIVE_INFINITY);
	}
	public double get(int row, int column) {
		return matrix[row][column];
	}

	public void set(int row, int column, double value) {
		matrix[row][column] = value;
	}

	public int size() {
		return matrix.length;
	}

	public double[] getRow(int index) {
		return matrix[index];
	}

}
