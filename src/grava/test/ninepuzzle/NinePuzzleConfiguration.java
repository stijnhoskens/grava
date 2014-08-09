package grava.test.ninepuzzle;

import java.util.Arrays;

import grava.exceptions.IllegalDimensionException;

public class NinePuzzleConfiguration {

	private final int[][] entries;

	public NinePuzzleConfiguration(int[][] entries) {
		if (entries.length != 3)
			throw new IllegalDimensionException(entries.length, 3);
		if (entries[0].length != 3)
			throw new IllegalDimensionException(entries[0].length, 3);
		this.entries = entries;
	}

	public boolean isCorrect() {
		return Arrays.deepEquals(entries, new int[][] { { 0, 1, 2 },
				{ 3, 4, 5 }, { 6, 7, 8 } });
	}

	public int getEntry(int row, int col) {
		return entries[row][col];
	}

	public int getEntry(Position pos) {
		return getEntry(pos.getY(), pos.getY());
	}

	public NinePuzzleConfiguration moveEmptySquare(Direction d) {
		Position posOf0 = null;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (getEntry(i, j) == 0)
					posOf0 = new Position(j, i);
		Position neighbour = d.getNeighbour(posOf0);
		if (neighbour == null)
			return null;
		int otherEntry = getEntry(neighbour);
		int[][] otherEntries = Arrays.copyOf(entries, 3);
		otherEntries[neighbour.getY()][neighbour.getX()] = 0;
		otherEntries[posOf0.getY()][posOf0.getX()] = otherEntry;
		return new NinePuzzleConfiguration(otherEntries);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(entries);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NinePuzzleConfiguration other = (NinePuzzleConfiguration) obj;
		if (!Arrays.deepEquals(entries, other.entries))
			return false;
		return true;
	}

}
