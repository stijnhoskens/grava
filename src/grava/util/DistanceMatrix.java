package grava.util;

import grava.exceptions.IllegalDimensionException;

public class DistanceMatrix {

	public static final double UNREACHABLE = 0d;

	private double[][] entries;

	/**
	 * @note The size of entries should be one or more, and it must be square.
	 */
	public DistanceMatrix(double[][] entries) throws IllegalDimensionException {
		if (entries.length < 1 || entries.length != entries[0].length)
			throw new IllegalDimensionException(entries.length,
					entries[0].length);
		this.entries = entries;
	}

	public DistanceMatrix(int size) {
		entries = new double[size][size];
	}

	public double[] getRow(int r) {
		return entries[r];
	}

	public double value(int r, int c) {
		return getRow(r)[c];
	}

	public void setValue(int r, int c, double v) {
		entries[r][c] = v;
	}

	public int size() {
		return entries.length;
	}

}
