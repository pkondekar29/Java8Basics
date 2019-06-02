package com.java.v8.basics.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

class DriverProgramOld {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("10");
		
		/** Legacy technique to write a comparator */
		Comparator<String> comparator = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
			
		};
		/**
		 * Here we are passing some instance which we have created to another piece of code.
		 * <b>
		 * So, we passed code(logic of some kind) as parameter.
		 * </b>
		 */
		Collections.sort(list, comparator);
		/** Same legacy technique to write an interface */
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("inside run");
			}
		};
		/**
		 * Same goes here.
		 * 
		 */
		Executors.newSingleThreadExecutor().execute(r);
	}
	
}
