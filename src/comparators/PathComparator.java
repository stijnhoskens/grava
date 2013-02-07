package comparators;

import graph.Node;

import java.util.Comparator;

import algorithm.Path;

public interface PathComparator<T extends Node> extends Comparator<Path<T>> {

}
