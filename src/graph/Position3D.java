package graph;

public class Position3D extends Position {
	
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
	
	private double z;

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public boolean equals(Position other) {
		if(other instanceof Position3D)
			return this.getX() == ((Position3D) other).getX() &&
				this.getY() == ((Position3D) other).getY() &&
				this.getZ() == ((Position3D) other).getZ();
		else return false;
	}

	@Override
	public double SLDto(Position other) {
		if(other instanceof Position3D) {
			double xDiff = this.getX() - ((Position3D) other).getX();
			double yDiff = this.getY() - ((Position3D) other).getY();
			double zDiff = this.getZ() - ((Position3D) other).getZ();
			return pythagoras(xDiff, yDiff, zDiff);
		}
		else return 0d;
	}

	@Override
	public double manhattan(Position other) {
		if(other instanceof Position3D) {
			double xDiff = this.getX() - ((Position3D) other).getX();
			double yDiff = this.getY() - ((Position3D) other).getY();
			double zDiff = this.getZ() - ((Position3D) other).getZ();
			return Math.abs(xDiff) + Math.abs(yDiff) + Math.abs(zDiff);
		}
		else return 0d;
	}

}
