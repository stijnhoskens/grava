package edge;

import node.Node;
import exceptions.DirectedException;

/**
 * Use this class whenever your edges need to contain both nodes.
 * 
 * @see Edge
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public class BiDirectionalEdge<T extends Node, S extends Edge<T>> {

	private final S edge1;

	private final S edge2;

	public BiDirectionalEdge(S edge1, S edge2) {
		this.edge1 = edge1;
		this.edge2 = edge2;
	}

	public BiDirectionalEdge<T, S> switchNodes() {
		return new BiDirectionalEdge<>(edge2, edge1);
	}

	public T getNode1() {
		return edge2.getNode2();
	}
	
	public S getEdge1() {
		return this.edge1;
	}

	public T getNode2() {
		return edge1.getNode2();
	}
	
	public S getEdge2() {
		return this.edge2;
	}

	public boolean isDirected() {
		return getCost2to1() != getCost1to2();
	}

	public double getCost() throws DirectedException {
		if (isDirected())
			throw new DirectedException(getCost2to1(), getCost1to2());
		return getCost1to2();
	}

	public double getCost2to1() {
		return edge2.getCost();
	}

	public double getCost1to2() {
		return edge1.getCost();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edge1 == null) ? 0 : edge1.hashCode());
		result = prime * result + ((edge2 == null) ? 0 : edge2.hashCode());
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
		BiDirectionalEdge<?, ?> other = (BiDirectionalEdge<?, ?>) obj;
		if (edge1 == null) {
			if (other.edge1 != null)
				return false;
		} else if (!edge1.equals(other.edge1))
			return false;
		if (edge2 == null) {
			if (other.edge2 != null)
				return false;
		} else if (!edge2.equals(other.edge2))
			return false;
		return true;
	}
}
