package grava.graph;

import grava.edge.Link;

public interface GraphListener<V, E extends Link<V>> {

	void vertexAdded(V v);

	void vertexRemoved(V v);

	void edgeAdded(E e);

	void edgeRemoved(E e);

}
