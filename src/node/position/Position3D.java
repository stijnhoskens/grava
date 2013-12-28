package node.position;

public class Position3D extends Position<Position3D> {

	private final Position2D pos2D;
	private double z;

	public Position3D(double x, double y, double z) {
		pos2D = new Position2D(x, y);
		this.z = z;
	}

	public double getX() {
		return pos2D.getX();
	}

	public double getY() {
		return pos2D.getY();
	}

	public double getZ() {
		return z;
	}

	@Override
	public double SLDto(Position3D other) {
		double SLD2D = this.pos2D.SLDto(other.pos2D);
		double zDiff = this.getZ() - ((Position3D) other).getZ();
		return pythagoras(SLD2D, zDiff);
	}

	@Override
	public double manhattan(Position3D other) {
		double manhattan2D = this.pos2D.manhattan(other.pos2D);
		double zDiff = this.getZ() - ((Position3D) other).getZ();
		return manhattan2D + Math.abs(zDiff);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos2D == null) ? 0 : pos2D.hashCode());
		long temp;
		temp = Double.doubleToLongBits(z);
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
		Position3D other = (Position3D) obj;
		if (pos2D == null) {
			if (other.pos2D != null)
				return false;
		} else if (!pos2D.equals(other.pos2D))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

}
