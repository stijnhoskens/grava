package comparators;


import java.util.Comparator;

import node.Node;

import algorithm.Path;

public interface PathComparator<T extends Node> extends Comparator<Path<T>> {

}
