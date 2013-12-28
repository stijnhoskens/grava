package node.position;

public abstract class Position<T extends Position<T>> {
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object other);
	
	public abstract double SLDto(T other);
	
	public abstract double manhattan(T other);
	
	protected static double pythagoras(double... diffs) {
		double sum = 0;
		for(double diff : diffs)
			sum += diff*diff;
		return Math.sqrt(sum);
	}

}
