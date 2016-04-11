package Labb3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class SpanningUSA {
	private static HashMap<String, LinkedList<Edge>> list = new HashMap<String, LinkedList<Edge>>();
	private String[] id;
	private int[] mstSet;
	private String[] remainSet; 
	private LinkedList<Edge> resSet;
	private int distance;
	private int size;
	private String beginStr;
	
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
	
	public void prim(){
		mstSet = new int[size];
		for(int i : mstSet) mstSet[i]=-1;
		mstSet[0]=0;
		id[0]=beginStr;
		int n=0;
		Edge eTemp = new Edge(null, null, Integer.MAX_VALUE);
		
		while(n < (size-1)){
			n=0;
			while(mstSet[n] == 0){
				LinkedList<Edge> tempList = list.get(id[n]);
				for(Edge e : tempList){
					if(newConnect(e) && eTemp.compareTo(e) > 0){
						eTemp = e;
					}
				}
				n++;
			}
			mstSet[n] = 0;
			id[n] = eTemp.target;
			resSet.add(eTemp);
			distance+=eTemp.weight;
		}
	}
	
	private boolean newConnect(Edge e) {
		String testTarget = e.target;
		int k = 0;
		while(mstSet[k] == 0){
			if(id[k].equals(testTarget)){
				return false;
			}
		}
		return true;
	}
	

}
