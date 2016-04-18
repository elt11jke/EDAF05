package Labb3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class SpanningUSA {
	private static HashMap<String, LinkedList<Edge>> list = new HashMap<String, LinkedList<Edge>>();
	private static HashMap<String, Boolean> isInMST = new HashMap<String, Boolean>();

	private String[] id;
	private int[] mstSet;
	private LinkedList<Edge> resSet;
	private static int distance;
	private int size;
	private String beginStr;
	
	public void readfile(String filename) {
		File f = new File(filename);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		size = 0;
		while (scan.hasNext()) {
			String line = scan.nextLine();
			if (!line.contains("--")) {
				size++;
				String city;
				line = line.trim();
				if(line.indexOf("\"") != -1){
					city = line.substring(line.indexOf("\"")+1, line.lastIndexOf("\""));
					list.put(city,new LinkedList<Edge>());
				
				} else {
					city = line;
					list.put(city, new LinkedList<Edge>());
				}
				isInMST.put(city, false);
				if(size == 1){
					beginStr = city;
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
				 list.get(target).add(new Edge(target, source, weight));
			
			} 
		}
		scan.close();
	}
	public static void main(String[] args) {
		SpanningUSA su = new SpanningUSA();
		//su.readfile("src/Labb3/tinyEWG-alpha.txt");
		long start = System.currentTimeMillis();
		su.readfile("src/Labb3/USA-highway-miles.txt");
		su.prim();
		long end = System.currentTimeMillis();
		System.out.println(distance);
		System.out.println("Executed in " + (end - start) + " ms");
	}
	
	public void prim(){
		
		mstSet = new int[size];
		id = new String[size];
		resSet = new LinkedList<Edge>();
		distance = 0;
		for(int i = 0; i < mstSet.length ; i++) {mstSet[i]=-1;}
		mstSet[0]=0;
		id[0]=beginStr;
		isInMST.put(beginStr, true);
		int n=0;
		
		
		while(n < (size-1)){
			n=0;
			Edge eTemp = new Edge(null, null, Integer.MAX_VALUE);
			while(mstSet[n] == 0){
				LinkedList<Edge> tempList = list.get(id[n]);
				for(Edge e : tempList){
					if(newConnect(e) && eTemp.compareTo(e) > 0){
						eTemp = e;
					}
				}
				n++;
			}
			System.out.println(n);
			mstSet[n] = 0;
			id[n] = eTemp.target;
			isInMST.put(eTemp.target, true);
			resSet.add(eTemp);
			distance+=eTemp.weight;
		}
		int w = 0;
		for(Edge e : resSet){
			w+=e.weight;
			System.out.println(e.source + " - - " + e.target + " : weight = " + e.weight);
		}
		//System.out.println(resSet.size());
	}
	
	private boolean newConnect(Edge e) {
		String testTarget = e.target;
		if(isInMST.get(testTarget)){
			return false;
		}
		return true;
	}
	

}
