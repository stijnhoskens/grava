package node;

import node.position.Position;

/**
 * Use this class if your node contains a position. The assumption is made that
 * only one node can exist at one position.
 * 
 * @author Stijn Hoskens
 * @param <T>
 *            The class of your position instance.
 */
public interface PositionNode<T extends Position<T>> extends ValueNode {

	public T getPosition();
}
