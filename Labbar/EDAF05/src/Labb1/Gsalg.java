package Labb1;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class Gsalg {

	private int n;
	private String[] people; //name and number of a man
	private LinkedList<Integer> singleMen; //list of single men
	private LinkedList<Integer>[] manPref; //array of the men's preferences in stacks
	private int[][] womanPref; //2d array of women pref
	private int[] engaged; //returns the man the woman i is engaged to, otherwise null
	private File inFile;

	public static void main(String[] args) {
		new Gsalg().run(args);
	}

	public void run(String[] args){
		if (args.length > 0) {
			inFile = new File(args[0]);
		}
		readFile();

		for(int i=1; i<= 2*n; i++) {
			if(i%2 == 1) {
				singleMen.add(i);
			} else {
				engaged[i] = -1;
			}
		}
		galeshapley();
		writeFile();
	}

	private void readFile() {
		Scanner sc = null;
		int numPer;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		try {sc = new Scanner(inFile);}
		catch (IOException e) { e.printStackTrace();}
		try {
			String str;

			while (sc.hasNext()) {
				str = sc.nextLine();
				while(str.startsWith("#")){
					str = sc.nextLine();
				}
				if(str.startsWith("n")){
					n = Integer.parseInt(str.substring(2));
					people = new String[2*n+1];
					singleMen = new LinkedList<Integer>();
					manPref = (LinkedList<Integer>[]) new LinkedList[2*n];
					for(int i=0; i<2*n; i++) {manPref[i] = new LinkedList<Integer>();}
					womanPref = new int[2*n+1][2*n+1];
					engaged = new int[2*n+1];
					for(int i=1; i<= 2*n; i++){
						numPer = sc.nextInt();
						String namePer = sc.next();
						people[numPer] = namePer;
					}
				} else if(str.length() != 0) {
					String[] split = str.split(":");
					numPer = Integer.parseInt(split[0]);
					Scanner nSC = new Scanner(split[1]);
					for(int i=1; i <= n ; i++) {
						int rank = nSC.nextInt();
						if(numPer%2 == 1){
							manPref[numPer].add(rank);
						} else {
							womanPref[numPer][rank] = i;
						}
					}
				}		
			}
		} finally {
			sc.close();
		}
	}

	private void writeFile() {
		for (int i=2; i<=2*n; i++) {
			if(i%2 == 0){
				System.out.println(i + "  " + engaged[i]);
			}
		}
		PrintWriter writer = null;
		try {writer = new PrintWriter(inFile+"out.txt");}
 		catch (IOException e) {	e.printStackTrace();}

		for (int i=2; i <= 2*n; i ++) {
			if (i%2 == 0)
				writer.println(people[i] + " -- " + people[engaged[i]]);
		}
		writer.close();
	}
	private void galeshapley(){
		while(singleMen.size()!=0){
			int man = singleMen.getFirst();
			int woman = manPref[man].remove(0);
			if (engaged[woman] < 0) {
				engaged[woman] = man;
				singleMen.remove(0);
			} else if (engaged[woman] > 0 && (womanPref[woman][engaged[woman]] > womanPref[woman][man])) {
				singleMen.remove(0);
				singleMen.add(engaged[woman]);
				engaged[woman] = man;
			}
		}
	}
}