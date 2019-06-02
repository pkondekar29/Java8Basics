package com.java.v8.basics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.java.v8.basics.beans.ExperimentalObject;

public class JavaBasics8 {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<ExperimentalObject> objects = ExperimentalObject.getList();
		ExperimentalObject object = new ExperimentalObject("1", "ad", 10);
		object.setId("1");
		object.setName("object 1");
		object.setValue(0);
		//objects.forEach(element -> find(object, element));
		
		SimpleDateFormat sdf = new SimpleDateFormat("");
		
		System.out.println(sdf.format(new Date()));
	}

	private static void find(ExperimentalObject ex, ExperimentalObject element) {
		if(ex.equals(element)) {
			System.out.println("Found");
		}
	}
}
