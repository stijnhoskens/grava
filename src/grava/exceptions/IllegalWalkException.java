package grava.exceptions;

import grava.edge.Link;
import grava.walk.Walk;

public class IllegalWalkException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Walk<?, ?> walk;
	private final Link<?> link;
	private final Object o;

	public IllegalWalkException(Walk<?, ?> walk, Link<?> link, Object o) {
		this.walk = walk;
		this.link = link;
		this.o = o;
	}

	public IllegalWalkException(Walk<?, ?> walk, Link<?> link) {
		this.walk = walk;
		this.link = link;
		this.o = null;
	}

	public Walk<?, ?> getWalk() {
		return walk;
	}

	public Link<?> getLink() {
		return link;
	}

	public Object getO() {
		return o != null ? o : link.asSet().stream()
				.filter(u -> !u.equals(walk.endVertex())).findAny().get();
	}

}
