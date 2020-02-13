package com.java.v8.basics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.java.v8.basics.beans.ExperimentalObject;

public class JavaBasics8 {
	
	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		/*List<ExperimentalObject> objects = ExperimentalObject.getList();
		ExperimentalObject object = new ExperimentalObject("1", "ad", 10);
		object.setId("1");
		object.setName("object 1");
		object.setValue(0);
		//objects.forEach(element -> find(object, element));
		
		SimpleDateFormat sdf = new SimpleDateFormat("");
		
		System.out.println(sdf.format(new Date()));
	*/	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		String startDate = sdf.parse("14-06-2019").toString();
		System.out.println(startDate);
	}

	private static void find(ExperimentalObject ex, ExperimentalObject element) {
		if(ex.equals(element)) {
			System.out.println("Found");
		}
	}
}
