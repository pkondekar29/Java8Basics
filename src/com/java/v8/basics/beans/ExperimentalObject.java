package com.java.v8.basics.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExperimentalObject {

	String id;
	String name;
	Integer value;
	
	public ExperimentalObject(String id, String name, Integer value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns the value of field <code>{@link #id}</code>.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Assigns the given value to field <code>{@link #id}</code>.
	 *
	 * @param id
	 *            the id to be set.
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Returns the value of field <code>{@link #name}</code>.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Assigns the given value to field <code>{@link #name}</code>.
	 *
	 * @param name
	 *            the name to be set.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the value of field <code>{@link #value}</code>.
	 *
	 * @return the value
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * Assigns the given value to field <code>{@link #value}</code>.
	 *
	 * @param value
	 *            the value to be set.
	 */
	public void setValue(final Integer value) {
		this.value = value;
	}

	public static List<ExperimentalObject> getList() {
		final List<ExperimentalObject> list = new ArrayList<>(10);
		ExperimentalObject object = new ExperimentalObject("1", "object 1", 0);
		object.setId("1");
		object.setName("object 1");
		object.setValue(0);
		list.add(object);
		for (int i = 0; i < 10; i++) {
			list.add(generateRandomObject());
		}

		return list;
	}

	private static final Random rnd = new Random();

	private static ExperimentalObject generateRandomObject() {
		final ExperimentalObject obj = new ExperimentalObject("1", "object 1", 0);
		obj.setId(getRandomString(8));
		obj.setName(getRandomString(10));
		obj.setValue(rnd.nextInt(100));
		return obj;
	}

	private static String getRandomString(final int n) {
		// chose a Character random from this String
		final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";
		// create StringBuffer size of AlphaNumericString
		final StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length()
					* Math.random());
			// add Character one by one in end of sb
			sb.append(AlphaNumericString
					.charAt(index));
		}
		return sb.toString();

	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof ExperimentalObject)) {
			return false;
		}
		ExperimentalObject e = (ExperimentalObject) obj;
		return e.toString().equals(obj.toString()); 
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		return id + " name: " + name + " value: " + value;
	}

}
