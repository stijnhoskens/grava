package grava.util;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetUtils {

	@SafeVarargs
	public static <T> Set<T> setOf(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}

	@SafeVarargs
	public static <T> Set<T> unmodifiableSetOf(T... values) {
		return Collections.unmodifiableSet(Stream.of(values).collect(
				Collectors.toSet()));
	}

}
