package grava.test.ninepuzzle;

public enum Direction {
	UP {
		@Override
		protected Position getNeighbour(Position p) {
			if (p.getY() == 0)
				return null;
			return new Position(p.getX(), p.getY() - 1);
		}
	},

	RIGHT {
		@Override
		protected Position getNeighbour(Position p) {
			if (p.getX() == 2)
				return null;
			return new Position(p.getX() + 1, p.getY());
		}
	},

	DOWN {
		@Override
		protected Position getNeighbour(Position p) {
			if (p.getY() == 2)
				return null;
			return new Position(p.getX(), p.getY() + 1);
		}
	},

	LEFT {
		@Override
		protected Position getNeighbour(Position p) {
			if (p.getX() == 0)
				return null;
			return new Position(p.getX() - 1, p.getY());
		}
	};

	protected abstract Position getNeighbour(Position p);
}
