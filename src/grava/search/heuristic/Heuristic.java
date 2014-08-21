package grava.search.heuristic;

import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface Heuristic<V> extends ToDoubleFunction<V> {

}
