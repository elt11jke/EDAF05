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


public WordLadders(){
	map = new HashMap<String, LinkedList<String>>();
	wordList = new ArrayList<String>();
	System.out.println("Skriv in en fil:");
	Scanner scan = new Scanner(System.in);
	String f = scan.next();
	readFile(f);
	writeFile();
}

private void readFile(String f){
	Scanner scan = null;
	try {
		scan= new Scanner(new File("src/lab2/"+f+".txt"));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	while (scan.hasNext()) {
		String text = scan.nextLine();
		//kommentar
		wordList.add(text);
		map.put(text, new LinkedList<String>());
	}
	scan.close();
}

private void writeFile() {
	PrintWriter writer = null;
	try {writer = new PrintWriter(f+"TESTOUT.txt");}
		catch (IOException e) {	e.printStackTrace();}

	writer.println(1);
	writer.println(1);
	writer.println(1);
	writer.println(1);
	writer.println(-1);
	writer.println(-1);
	
	writer.close();
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

public void printToFile(){
	
}

public void distance(){
	
}

public static void main(String[] args) {
	WordLadders w = new WordLadders();
}
}