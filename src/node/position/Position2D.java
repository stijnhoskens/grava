package node.position;

public class Position2D extends Position<Position2D> {

	private final double x;
	private final double y;
	
	public Position2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public double SLDto(Position2D other) {
		double xDiff = this.getX() - ((Position2D) other).getX();
		double yDiff = this.getY() - ((Position2D) other).getY();
		return pythagoras(xDiff, yDiff);
	}

	@Override
	public double manhattan(Position2D other) {
		double xDiff = this.getX() - ((Position2D) other).getX();
		double yDiff = this.getY() - ((Position2D) other).getY();
		return Math.abs(xDiff) + Math.abs(yDiff);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Position2D other = (Position2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}
