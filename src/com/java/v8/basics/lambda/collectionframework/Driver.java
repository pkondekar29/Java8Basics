package com.java.v8.basics.lambda.collectionframework;

import java.util.HashMap;
import java.util.Map;

/**
 *	Collections framework addition -
 *	1. forEach(Consumer<>) in Iterable.	
 *		A consumer is something which takes in some object and does not return anything and just consumes it.
 *	2. removeIf(Predicate<>) 
 *		A predicate returns a boolean
 *	3. replaceAll(UnaryOperator<E> ) on List.
 *		Unary takes in same object and returns it
 *	4. sort on List
 *		takes in Comparator
 *	5. forEach(BiConsumer<K, V> ) in Map
 * 
 *
 */
public class Driver {

	private static final String EMPTY_VALUE = "";

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		
		map.forEach((s1, s2) -> {
			System.out.println(s1 + " value: " + map.get(s1));
		});
		
		// new method
		map.getOrDefault("s2", EMPTY_VALUE);
		
		map.putIfAbsent("s2", "sdadas");
		
		map.replace("s2", "new val");
		
		map.replace("s2", "new val", "latest val");
		
		map.replaceAll((s1, s2) -> s1.concat(s2));	// BiFunction which concatenates the strings
		
		map.remove("s1", "old");
		
		map.forEach((s1, s2) -> System.out.println(s2));
		
		// These are the compute api which can be used very easily to change the mapping of the given map
		// The mapping Functions are BiFunctions<K, V, V> which take in the key-value pair and return some mapped value
//		map.compute();
//		map.computeIfAbsent(key, mappingFunction)
//		map.computeIfPresent(key, remappingFunction);

		Map<String, Map<String, String>> mapofMap = new HashMap<>();
		mapofMap.computeIfAbsent("one", key -> new HashMap<String, String>()).put("two", "asd");
		
		// This looks the same as compute but the remappingFunction<V , V, V> takes in the values - old values and new values instead of key-value pairs
		//map.merge(key, value, remappingFunction);
	}
	
}
