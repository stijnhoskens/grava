package graph;

public class Position2D extends Position {

	private double x;
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	private double y;

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double SLDto(Position other) {
		if(other instanceof Position2D) {
			double xDiff = this.getX() - ((Position2D) other).getX();
			double yDiff = this.getY() - ((Position2D) other).getY();
			return pythagoras(xDiff, yDiff);
		}
		else return 0d;
	}

	public double manhattan(Position other) {
		if(other instanceof Position2D) {
			double xDiff = this.getX() - ((Position2D) other).getX();
			double yDiff = this.getY() - ((Position2D) other).getY();
			return Math.abs(xDiff) + Math.abs(yDiff);
		}
		else return 0d;
	}

	@Override
	public boolean equals(Position other) {
		if(other instanceof Position2D)
			return x==((Position2D) other).getX() && y==((Position2D) other).getY();
		else return false;
	}

}
