package com.java.v8.basics.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MyClass implements Interface1, Interface2 {

	@Override
	public void interface2method1() {
		// TODO Auto-generated method stub
	}

	@Override
	public void interface1method1() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		IntStream.range(0, 100).forEach(t -> list.add(Integer.toString(t)));
		//list.stream().forEach(System.out::print);
		list.parallelStream()
			.map(t -> t)
			.forEach(t -> doSomething(t));
	}

	private static void doSomething(String t) {
		System.out.println(t);
	}

}
