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
				line = line.trim();
				if(line.indexOf("\"") != -1){
					list.put(line.substring(line.indexOf("\"")+1, line.lastIndexOf("\"")),new LinkedList<Edge>());
				
				} else {
					list.put(line, new LinkedList<Edge>());
				}
			}
			if (line.contains("--")) {
				String source;
				String target;
				int weight;
				String[] splitCities = line.split("--");
				splitCities[0] = splitCities[0].trim();
				
				if(splitCities[0].indexOf("\"") != -1){
					source = splitCities[0].substring(splitCities[0].indexOf("\"")+1, splitCities[0].lastIndexOf("\""));
					System.out.println(source);
				} else {
					source = splitCities[0];
				}
				
				String[] splitDist = splitCities[1].split("\\[");
				splitDist[0] = splitDist[0].trim();				

				if(splitDist[0].indexOf("\"") != -1){
					target = splitDist[0].substring(splitDist[0].indexOf("\"")+1, splitDist[0].lastIndexOf("\""));
					
				} else {
					target = splitDist[0];
				}
				
				weight = Integer.parseInt(splitDist[1].substring(0, splitDist[1].length()-1));
				
				 list.get(source).add(new Edge(source, target, weight));
			
			} 
		}
		scan.close();
	}
	public static void main(String[] args) {

		readfile("src/Labb3/USA-highway-miles.txt");
	}
	

}
