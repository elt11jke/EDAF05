package Labb3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class SpanningUSA {
	static HashMap<String, LinkedList<Edge>> list = new HashMap<String, LinkedList<Edge>>();

	
	public static void readfile(String filename) {
		File f = new File(filename);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (scan.hasNext()) {
			String line = scan.nextLine();
			if (!line.contains("--")) {
				list.put(line.trim(), new LinkedList<Edge>());
			}
			if (line.contains("--")) {
				String[] splitCities = line.split("--");
				splitCities[0] = splitCities[0].trim();

			} 
		}
		scan.close();
	}
	public static void main(String[] args) {

		readfile("src/Labb3/USA-highway-miles.txt");
	}
	

}
