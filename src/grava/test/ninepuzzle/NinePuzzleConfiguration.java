package grava.test.ninepuzzle;

import grava.exceptions.IllegalDimensionException;
import grava.search.heuristic.Heuristic;

import java.util.Arrays;
import java.util.stream.IntStream;

public class NinePuzzleConfiguration {

	private static final NinePuzzleConfiguration GOAL_STATE = new NinePuzzleConfiguration(
			new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
	private final int[][] entries;

	public NinePuzzleConfiguration(int[][] entries) {
		if (entries.length != 3)
			throw new IllegalDimensionException(entries.length, 3);
		if (entries[0].length != 3)
			throw new IllegalDimensionException(entries[0].length, 3);
		this.entries = entries;
	}

	public boolean isCorrect() {
		return Arrays.deepEquals(entries, GOAL_STATE.entries);
	}

	public int getEntry(MatrixPosition pos) {
		return entries[pos.getRow()][pos.getColumn()];
	}

	private void setEntry(MatrixPosition pos, int val) {
		entries[pos.getRow()][pos.getColumn()] = val;
	}

	public NinePuzzleConfiguration moveEmptySquare(Direction d) {
		MatrixPosition posOf0 = positionOf(0);
		MatrixPosition neighbour = d.getNeighbour(posOf0);
		if (neighbour == null)
			return null;
		return withTheseSwitched(posOf0, neighbour);
	}

	public static Heuristic<NinePuzzleConfiguration> manhattanHeuristic() {
		return npc -> IntStream
				.range(0, 9)
				.map(x -> npc.positionOf(x).manhattanTo(
						GOAL_STATE.positionOf(x))).sum();
	}

	private MatrixPosition positionOf(int val) {
		MatrixPosition posOf = new MatrixPosition(0, 0);
		while (getEntry(posOf) != val)
			posOf = posOf.next();
		return posOf;
	}

	private NinePuzzleConfiguration withTheseSwitched(MatrixPosition p1,
			MatrixPosition p2) {
		int p1Entry = getEntry(p1);
		int p2Entry = getEntry(p2);
		NinePuzzleConfiguration puzzle = new NinePuzzleConfiguration(
				copyOf(entries));
		puzzle.setEntry(p1, p2Entry);
		puzzle.setEntry(p2, p1Entry);
		return puzzle;
	}

	private int[][] copyOf(int[][] array) {
		int[][] newArray = new int[3][3];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = Arrays.copyOf(array[i], 3);
		return newArray;
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

	@Override
	public String toString() {
		return Arrays.deepToString(entries);
	}
}
