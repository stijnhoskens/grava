package grava.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {

	public static <T> Set<T> setOf(Stream<T> stream) {
		return stream.collect(Collectors.toSet());
	}

	public static <T> Set<T> unmodifiableSetOf(Stream<T> stream) {
		return Collections.unmodifiableSet(setOf(stream));
	}

	@SafeVarargs
	public static <T> Set<T> setOf(T... values) {
		return setOf(Stream.of(values));
	}

	@SafeVarargs
	public static <T> Set<T> unmodifiableSetOf(T... values) {
		return unmodifiableSetOf(Stream.of(values));
	}

	public static <T> Set<T> flatten(Collection<Set<T>> c) {
		return flatten(c.stream());
	}

	public static <T> Set<T> flatten(Stream<Set<T>> stream) {
		return flattenedStream(stream).collect(Collectors.toSet());
	}

	public static <T> Stream<T> flattenedStream(Stream<Set<T>> stream) {
		return stream.flatMap(Set::stream);
	}

	public static <T> Set<T> copyOf(Collection<T> c) {
		return new HashSet<>(c);
	}

	public static <T> List<T> listOf(Stream<T> stream) {
		return stream.collect(Collectors.toList());
	}

}
