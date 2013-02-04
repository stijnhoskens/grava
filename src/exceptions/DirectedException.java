package exceptions;

public class DirectedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final double cost1to2;
	private final double cost2to1;
	
	public DirectedException(double cost1to2, double cost2to1) {
		this.cost1to2 = cost1to2;
		this.cost2to1 = cost2to1;
	}
	
	public double[] getCosts() {
		double[] costs = {cost1to2, cost2to1};
		return costs;
	}

}
