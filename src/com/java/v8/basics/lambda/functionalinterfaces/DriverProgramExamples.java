package com.java.v8.basics.lambda.functionalinterfaces;

public class DriverProgramExamples {
	
	public static void main(String[] args) {
		
		Predicate<String> p1 = s -> s.length() < 10;
		System.out.println(p1.test("Hello"));
		
		Predicate<String> p2 = s -> s.length() > 5;
		
		Predicate<String> p3 = p1.and(p2);	/* This is chaining of both predicates. And it means that all the string with length greater than 10 and less than 5 */
		System.out.println(p3.test("Hello"));
		System.out.println(p3.test("dasasjfklajf"));
	}
}
