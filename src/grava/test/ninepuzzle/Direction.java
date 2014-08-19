package grava.test.ninepuzzle;

enum Direction {
	UP {
		@Override
		public MatrixPosition getNeighbour(MatrixPosition p) {
			if (p.getRow() == 0)
				return null;
			return new MatrixPosition(p.getRow() - 1, p.getColumn());
		}
	},

	RIGHT {
		@Override
		public MatrixPosition getNeighbour(MatrixPosition p) {
			if (p.getColumn() == 2)
				return null;
			return new MatrixPosition(p.getRow(), p.getColumn() + 1);
		}
	},

	DOWN {
		@Override
		public MatrixPosition getNeighbour(MatrixPosition p) {
			if (p.getRow() == 2)
				return null;
			return new MatrixPosition(p.getRow() + 1, p.getColumn());
		}
	},

	LEFT {
		@Override
		public MatrixPosition getNeighbour(MatrixPosition p) {
			if (p.getColumn() == 0)
				return null;
			return new MatrixPosition(p.getRow(), p.getColumn() - 1);
		}
	};

	public abstract MatrixPosition getNeighbour(MatrixPosition p);
}
