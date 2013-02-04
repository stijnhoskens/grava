package graph;

public abstract class Position {
	
	public abstract boolean equals(Position other);
	
	public abstract double SLDto(Position other);
	
	public abstract double manhattan(Position other);
	
	protected double pythagoras(double... diffs) {
		double sum = 0;
		for(double diff : diffs)
			sum += diff*diff;
		return Math.sqrt(sum);
	}

}
