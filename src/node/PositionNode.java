package node;

import node.position.Position;

/**
 * Use this class if your node contains a position.
 * @author Stijn Hoskens
 * @param <T> The class of your position instance.
 */
public abstract class PositionNode<T extends Position<T>> implements Node {

	private T position;
	
	public T getPosition() {
		return this.position;
	}
}
