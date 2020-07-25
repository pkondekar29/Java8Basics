package com.java.iostreams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String json = "{\"processed\": [\"1370958\", \"98741\"]}";
		File file = new File(Driver.class.getClassLoader().getResource("output.txt").getFile());
		System.out.print(json.getBytes());
		try(OutputStream os = new FileOutputStream(file)){
			os.write(json.getBytes("UTF8"));
		}
	}

}
