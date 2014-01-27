package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiMap<K, V> extends HashMap<K, Set<V>> {

	private static final long serialVersionUID = 1L;
	
	public MultiMap() {
		
	}
	
	public MultiMap(Map<K,Set<V>> map) {
		super(map);
	}

	public MultiMap(Iterable<K> keys) {
		addKeys(keys);
	}

	public void addKey(K key) {
		put(key, new HashSet<V>());
	}

	public void addKeys(Iterable<K> keys) {
		for (K key : keys)
			addKey(key);
	}

	public void addValue(K key, V value) {
		if (!containsKey(key))
			addKey(key);
		get(key).add(value);
	}

	public void addValues(K key, Set<V> values) {
		if (!containsKey(key))
			addKey(key);
		get(key).addAll(values);
	}
}
