package grava.util;

import java.util.Arrays;

public class DistanceMatrix {

	private double[][] entries;

	/**
	 * @note The size of entries should be one or more, and it must be square.
	 */
	public DistanceMatrix(double[][] entries) {
		if (entries.length < 1 || entries.length != entries[0].length)
			throw new IllegalArgumentException("Not a square matrix");
		this.entries = entries;
	}

	public DistanceMatrix(int size) {
		entries = new double[size][size];
	}

	public double[] getRow(int r) {
		return entries[r];
	}

	public double getValue(int r, int c) {
		return getRow(r)[c];
	}

	public void setValue(int r, int c, double v) {
		entries[r][c] = v;
	}

	public int getSize() {
		return entries.length;
	}

	public void setSize(int size) {
		double[][] newEntries = new double[size][size];
		int minSize = Math.min(size, getSize());
		for (int i = 0; i < minSize; i++)
			newEntries[i] = Arrays.copyOf(getRow(i), size);
		entries = newEntries;
	}

	public void incrementSize() {
		setSize(getSize() + 1);
	}

}
