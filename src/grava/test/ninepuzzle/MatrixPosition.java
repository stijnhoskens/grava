package grava.test.ninepuzzle;

class MatrixPosition {

	private final int x, y;

	public MatrixPosition(int row, int column) {
		x = row;
		y = column;
	}

	public int getRow() {
		return x;
	}

	public int getColumn() {
		return y;
	}

	public MatrixPosition next() {
		int newY = (y + 1) % 3;
		int newX = x + (y + 1) / 3;
		if (newX == 3)
			return null;
		return new MatrixPosition(newX, newY);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		MatrixPosition other = (MatrixPosition) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int manhattanTo(MatrixPosition other) {
		return Math.abs(getRow() - other.getRow())
				+ Math.abs(getColumn() - other.getColumn());
	}
}
