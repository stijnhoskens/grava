package grava.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetUtils {

	public static <T> Set<T> setOf(Stream<T> stream) {
		return stream.collect(Collectors.toSet());
	}

	public static <T> Set<T> unmodifiableSetOf(Stream<T> stream) {
		return Collections.unmodifiableSet(setOf(stream));
	}

	@SafeVarargs
	public static <T> Set<T> setOf(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}

	@SafeVarargs
	public static <T> Set<T> unmodifiableSetOf(T... values) {
		return Collections.unmodifiableSet(setOf(values));
	}

	public static <T> Set<T> flatten(Collection<Set<T>> c) {
		return c.stream().flatMap(Set::stream).collect(Collectors.toSet());
	}

}
