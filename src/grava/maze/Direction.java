package grava.maze;

public enum Direction {

	UP, DOWN, LEFT, RIGHT;

	Position neighbourTo(Position pos) {
		if (isHorizontal())
			return incrementX(pos, increment() ? 1 : -1);
		return incrementY(pos, increment() ? 1 : -1);
	}

	boolean increment() {
		return equals(UP) || equals(RIGHT);
	}

	private static Position incrementX(Position pos, int amount) {
		return new Position(pos.getX() + amount, pos.getY());
	}

	private static Position incrementY(Position pos, int amount) {
		return new Position(pos.getX(), pos.getY() + amount);
	}

	boolean isHorizontal() {
		return equals(LEFT) || equals(RIGHT);
	}

	public static Direction between(Position p1, Position p2) {
		for (Direction d : values())
			if (d.neighbourTo(p1).equals(p2))
				return d;
		return null;
	}
}
