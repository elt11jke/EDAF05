package Labb2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class WordLadders {
private HashMap<String, LinkedList<String>> map;
private ArrayList<String> wordList;
private String f;
PrintWriter writer;


public WordLadders(String f){
	/*map = new HashMap<String, LinkedList<String>>();
	wordList = new ArrayList<String>();
	System.out.println("Skriv in en fil:");
	Scanner scan = new Scanner(System.in);*/
	this.f = f;
	wordList = new ArrayList<String>();
	map = new HashMap<String, LinkedList<String>>();
	readFile(f);
	AdjacentList();
	BFS();
}

private void readFile(String f){
	Scanner scan = null;
	try {
		scan= new Scanner(new File("src/Labb2/words-"+f+".txt"));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	while (scan.hasNext()) {
		String text = scan.nextLine();
		wordList.add(text);
		map.put(text, new LinkedList<String>());
	}
	scan.close();
}


public void AdjacentList(){
	for (int i = 0; i < wordList.size(); i++){
		char second = wordList.get(i).charAt(1);
		char third = wordList.get(i).charAt(2);
		char fourth = wordList.get(i).charAt(3);
		char fifth = wordList.get(i).charAt(4);
		
		for(int j = 0; j < wordList.size(); j++){
			if(i != j){
				char[] c = wordList.get(j).toCharArray();
				ArrayList<Character> cList = new ArrayList<Character>();
				for(char ch :c){
					cList.add(ch);
				}
				if(cList.contains(second)){
					cList.remove(cList.indexOf(second)); 
					if(cList.contains(third)){
						cList.remove(cList.indexOf(third));	
						if(cList.contains(fourth)){
							cList.remove(cList.indexOf(fourth));	
							if (cList.contains(fifth)) {
								map.get(wordList.get(i)).addLast(wordList.get(j));
								}
							}
						}	
					}
				}
			}
		}
	}

public void BFS(){
	Scanner scan = null;
	try {
		writer = new PrintWriter("src/Labb2/words-"+f+"-TESTOUT.txt");
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		scan= new Scanner(new File("src/Labb2/words-"+f+"-in.txt"));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	while(scan.hasNext()){
		//System.out.println("start compare");
		String line = scan.nextLine();
		Scanner pair = new Scanner(line);
		String start = pair.next();
		String end = pair.next();
		Boolean found = false;
		if(start.equals(end)){
			found=true;
		}
		int layerCounter = 0;
		int elementId = wordList.indexOf(start);
		Boolean[] discovered = new Boolean[Integer.parseInt(f)];
		for(int i=0; i< discovered.length; i++){
			if(elementId == i){
				discovered[i]=true;
			} else {
				discovered[i]=false;
			}
		}
		LinkedList<Integer>[] layers = (LinkedList<Integer>[]) new LinkedList[wordList.size()];
		layers[0] = new LinkedList<Integer>();
		layers[0].add(elementId);
		
		while(!found && layers[layerCounter].size() > 0){
			//System.out.println("enter" + layerCounter);
			layers[layerCounter+1] = new LinkedList<Integer>();
			search:
			for(int node : layers[layerCounter]){
				//System.out.println("forenter");
				LinkedList<String> list = map.get(wordList.get(node));

				for(String neighbor : list){
					//System.out.println("neighbor"+neighbor);
					int neighborId = wordList.indexOf(neighbor);
					if(!discovered[neighborId]){
						/*if(layers[layerCounter+1] == null){
							layers[layerCounter+1] = new LinkedList<Integer>();
						}*/
						layers[layerCounter+1].add(neighborId);
						discovered[neighborId]=true;
						if(wordList.get(neighborId).equals(end)){
							found = true;
							break search;
						}
					}
				}				
			}
			layerCounter++;
		}
		if(found){
			writer.println(layerCounter);
		} else {
			writer.println("-1");
		}
		layerCounter = 0;
		found = false;

	}
	writer.close();
}

/*public Boolean compareOut(){
	Scanner rOut = new Scanner("src/lab2/words-"+f+"-out.txt");
	Scanner out = 
}*/

/*public static void main(String[] args) {
	WordLadders w = new WordLadders();
}*/
}