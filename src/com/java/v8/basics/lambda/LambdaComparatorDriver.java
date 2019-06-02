package com.java.v8.basics.lambda;

import java.util.function.Function;

import com.java.v8.basics.beans.ExperimentalObject;

public class LambdaComparatorDriver {

	public static void main(String... args) {
		/*Comparator<ExperimentalObject> comparator = (o1, o2) -> {	 We are defining the functional interface compare method other here 
			return o1.getValue() - o2.getValue();		 This is the lambda which will get executed 
		};*/
		
		Function<ExperimentalObject, Integer> f1 = ExperimentalObject::getValue;	/* Defining a function for Experimental object */
		Function<ExperimentalObject, Integer> f2 = o1/* This is the input to the function */ -> o1.getValue() /* and this is the o/p */;
		
		Comparator<ExperimentalObject> cmp = Comparator.comparing(ExperimentalObject::getValue);
		
		/**
		 * Final comparator -> Here we are using lambda expressions and 
		 * also using the comparators to chain each other.
		 * 
		 * Here we are taking in the Function and creating a comparator with fnction and chaining that comparator with some other function 
		 * 
		 */
		Comparator<ExperimentalObject> cmp2 = Comparator.comparing(ExperimentalObject::getValue)
														.thenComparing(ExperimentalObject::getName)
														.thenComparing(ExperimentalObject::getId);
	}

}
