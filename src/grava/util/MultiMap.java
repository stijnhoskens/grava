package grava.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MultiMap<K, V> extends HashMap<K, Set<V>> {

	private static final long serialVersionUID = 1L;

	public boolean addKey(K key) {
		if (containsKey(key))
			return false;
		put(key, new HashSet<>());
		return true;
	}

	public boolean addValue(K key, V value) {
		addKey(key);
		return get(key).add(value);
	}

	public void addValues(K key, Collection<V> values) {
		addKey(key);
		get(key).addAll(values);
	}

	public int count(K key) {
		if (!containsKey(key))
			return 0;
		return get(key).size();
	}

	public boolean containsValue(K key, V value) {
		if (!containsKey(key))
			return false;
		return get(key).contains(value);
	}

	public boolean removeValue(K key, V value) {
		if (!containsKey(key))
			return false;
		return get(key).remove(value);
	}

	@Override
	public Set<V> get(Object key) {
		if (!containsKey(key))
			return Collections.emptySet();
		return super.get(key);
	}

}
