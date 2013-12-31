package util;


/**
 * Matrix to represent the adjacency matrix, as specified in
 * http://en.wikipedia.org/wiki/Adjacency_matrix. The entry in row i and column
 * j corresponds to the cost from node i to node j.
 * 
 * @author Stijn
 * 
 */
public class AdjacencyMatrix {

	private final double[][] matrix;

	/**
	 * @pre matrix has to be square.
	 */
	public AdjacencyMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public AdjacencyMatrix(int size) {
		this.matrix = new double[size][size];
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
