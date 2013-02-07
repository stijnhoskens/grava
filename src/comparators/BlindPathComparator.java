package comparators;

import graph.Node;
import algorithm.Path;

public class BlindPathComparator<T extends Node> implements PathComparator<T> {

	@Override
	public int compare(Path<T> arg0, Path<T> arg1) {
		return 0;
	}

}
