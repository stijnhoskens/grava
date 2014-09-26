package grava.maze;

import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

public class Position {

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private final int x;

	public int getX() {
		return x;
	}

	private final int y;

	public int getY() {
		return y;
	}

	public int manhattanTo(Position other) {
		return abs(x - other.x) + abs(y - other.y);
	}

	public double euclideanTo(Position other) {
		return sqrt(squareEuclideanTo(other));
	}

	public double squareEuclideanTo(Position other) {
		double xDiff = x - other.x;
		double yDiff = y - other.y;
		return xDiff * xDiff + yDiff * yDiff;
	}

	public double normDistanceTo(Position other, int norm) {
		double inv = 1 / ((double) norm);
		double xTerm = pow(abs(x - other.x), norm);
		double yTerm = pow(abs(y - other.y), norm);
		return pow(xTerm + yTerm, inv);
	}

	public double infinityNormDistanceTo(Position other) {
		return max(abs(x - other.x), abs(y - other.y));
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
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
