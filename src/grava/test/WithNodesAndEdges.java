package grava.test;

import static grava.util.CollectionUtils.setOf;
import grava.edge.Arc;
import grava.edge.Edge;

import java.util.Set;

public abstract class WithNodesAndEdges {

	protected final Node a = new Node("a"), b = new Node("b"),
			c = new Node("c"), d = new Node("d"), e = new Node("e"),
			f = new Node("f");
	protected final Set<Node> nodes = setOf(a, b, c, d, e, f);
	protected final Edge<Node> ab = new Edge<>(a, b), bc = new Edge<>(b, c),
			be = new Edge<>(b, e), ef = new Edge<>(e, f),
			cf = new Edge<>(c, f), cd = new Edge<>(c, d),
			df = new Edge<>(d, f);
	protected final Set<Edge<Node>> edges = setOf(ab, bc, be, ef, cf, cd, df);
	/*
	 * All arcs go from the lowest in lexicographic order to the highest.
	 */
	protected final Arc<Node> ab_arc = new Arc<>(a, b),
			bc_arc = new Arc<>(b, c), be_arc = new Arc<>(b, e),
			ef_arc = new Arc<>(e, f), cf_arc = new Arc<>(c, f),
			cd_arc = new Arc<>(c, d), df_arc = new Arc<>(d, f);
	protected final Set<Arc<Node>> arcs = setOf(ab_arc, bc_arc, be_arc, ef_arc,
			cf_arc, cd_arc, df_arc);

}
