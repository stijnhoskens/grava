package graph;

/**
 * Use this class if your node contains a position.
 * @author Stijn Hoskens
 * @param <T> The class of your position instance.
 */
public abstract class PositionNode<T extends Position> extends Node {

	private T position;
	
	public T getPosition() {
		return this.position;
	}
}
