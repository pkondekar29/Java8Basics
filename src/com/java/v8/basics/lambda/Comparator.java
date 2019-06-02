package com.java.v8.basics.lambda;

import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {

	Integer compare(T t1, T t2);

	// Level 1
	/*static Comparator<ExperimentalObject> comparing(Function<ExperimentalObject, Integer> f) {
		return (o1, o2) -> f.apply(o1) - f.apply(o2);	 So this is calling the lambda using Function interface 
	}*/
	
	// Level 2
	/*static Comparator<ExperimentalObject> comparing(Function<ExperimentalObject, Comparable> f) {
		return (o1, o2) -> f.apply(o1).compareTo(f.apply(o2));  // So this is returning the comparator<T> functional interface 
	}*/
	
	// Level 3
	public static <U> Comparator<U> comparing(Function<U, Comparable> f) {
		return (o1, o2) -> f.apply(o1).compareTo(f.apply(o2)); 
	}
	
	/**
	 * We are trying to chain the comparator here with the new comparator cmp.
	 * If result is 0, i.e, they are equal then use the new comparator cmp.
	 * 
	 * @param cmp
	 * @return
	 */
	public default Comparator<T> thenComparing(Comparator<T> cmp){
		return (o1, o2) -> compare(o1, o2) == 0 ? cmp.compare(o1, o2) : compare(o1, o2);
	}
	
	public default Comparator<T> thenComparing(Function<T, Comparable> f){
		Comparator<T> comparator = comparing(f);			/* So here we created the comparator from the function */
		return thenComparing(comparator);			/* And then use that comparator to chain the existing comparator */
	}
	
}
