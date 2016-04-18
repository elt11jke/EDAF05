package Labb3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MinimalSpanningTree {
	private static HashMap<String, PriorityQueue<Edge>> list = new HashMap<String, PriorityQueue<Edge>>();
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
					list.put(city,new PriorityQueue<Edge>());
				
				} else {
					city = line;
					list.put(city,new PriorityQueue<Edge>());
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
				
				 list.get(source).offer(new Edge(source, target, weight));
				 list.get(target).offer(new Edge(target, source, weight));
			
			} 
		}
		scan.close();
	}
	public static void main(String[] args) {
		MinimalSpanningTree su = new MinimalSpanningTree();
		//su.readfile("src/Labb3/tinyEWG-alpha.txt");
		su.readfile("src/Labb3/USA-highway-miles.txt");
		su.prim();
		System.out.println(distance);
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
		
		
		while(n < (size-1)){ //Loop until all nodes are in the MST
			int connectId = 0;
			n=0;
			Edge eTemp = new Edge(null, null, Integer.MAX_VALUE);
			Edge e = new Edge(null, null, Integer.MAX_VALUE);
			while(mstSet[n] == 0){ // Loop through all nodes of the MST
				while(list.get(id[n]).size()>0) {
					e = list.get(id[n]).peek();
					if (newConnect(e)){
						break;
					} else { list.get(id[n]).poll(); }
				}
				if(e.compareTo(eTemp) == -1){
					eTemp = e;
					connectId = n;
				}	
				n++;
			}
			list.get(id[connectId]).poll();
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
