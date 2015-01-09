package grava.maze;

public enum Direction {

	UP {
		@Override
		Position neighbourTo(Position pos) {
			return incrementY(pos, 1);
		}

		@Override
		public Direction opposite() {
			return DOWN;
		}
	},

	DOWN {
		@Override
		Position neighbourTo(Position pos) {
			return incrementY(pos, -1);
		}

		@Override
		public Direction opposite() {
			return UP;
		}
	},

	LEFT {
		@Override
		Position neighbourTo(Position pos) {
			return incrementX(pos, -1);
		}

		@Override
		public Direction opposite() {
			return RIGHT;
		}
	},

	RIGHT {
		@Override
		Position neighbourTo(Position pos) {
			return incrementX(pos, 1);
		}

		@Override
		public Direction opposite() {
			return LEFT;
		}
	};

	abstract Position neighbourTo(Position pos);
	
	public abstract Direction opposite();

	private static Position incrementX(Position pos, int amount) {
		return new Position(pos.getX() + amount, pos.getY());
	}

	private static Position incrementY(Position pos, int amount) {
		return new Position(pos.getX(), pos.getY() + amount);
	}

}
