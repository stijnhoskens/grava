package node;

import graph.DynamicGraph;

/**
 * As this interface is used in the DynamicGraph implementation, it needs to
 * override hashCode and equals.
 * 
 * @see DynamicGraph
 * 
 * @author Stijn
 * 
 */
public interface ValueNode extends Node {

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object other);

}
