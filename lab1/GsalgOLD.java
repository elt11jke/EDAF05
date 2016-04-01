import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class Gsalg {

	/*
	1. Read
	2. Sort men and women accord. to name
	3. Insert into linked list
	4. Make preference lists
	5. Start algorithm
	*/

	private int n;
	private String[] people; //name and number of a man
	private Stack singleMen; //list of single men
	private Stack[] manPref; //array of the men's preferences in stacks
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
		people = new String[1000];
		singleMen = new Stack();
		manPref = new Stack[1000];
		for(int i=0; i< 1000;i++){manPref[i] = new Stack();}
		womanPref = new int[1000][1000];
		engaged = new int[1000];
		readFile();
		galeshapley();
		writeFile();
	}

	private void readFile() {
		BufferedReader br = null;
		try {br = new BufferedReader(new FileReader(inFile));}
		catch (IOException e) { e.printStackTrace();}  
		try {
			String line = br.readLine();

			while(line != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(line);
				char fc = sb.charAt(0);
				if(fc != '#'){
					if(fc == 'n') {n= Integer.parseInt(sb.substring(2));}
					else {
						int fi = Character.getNumericValue(fc);
						if (fi%2 == 0 && sb.charAt(1) != ':') {people[fi] = sb.substring(2);}
						else if (fi%2 == 1 && sb.charAt(1) != ':') {people[fi] = sb.substring(2);}
						else if (fi%2 == 0 && sb.charAt(1) == ':') {insertPref(sb, fi);}
						else if (fi%2 == 1 && sb.charAt(1) == ':') {insertPref(sb, fi);}
					}
				}
				line = br.readLine();
				if(line!=null && line.length() == 0) {line = br.readLine();}
				System.out.println(line);
			}
		} catch (IOException e) {
        e.printStackTrace(); 
		} finally {
			try {br.close();}
			catch (IOException e) {e.printStackTrace(); }
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

	private void insertPref(StringBuilder sb, int fi) {
		int pos = 3;
		if (fi%2 == 1) {
			singleMen.push(fi);
		}
		
		for (int i=1; i<=n; i++){
			if(fi%2 == 0){
				womanPref[fi][Character.getNumericValue(sb.charAt(pos))]=i;
				engaged[fi] = -1;
			} else {
				manPref[fi].push(Character.getNumericValue(sb.charAt(pos)));
				
			}
		pos+=2;
		}
	}

	private void galeshapley(){

		System.out.println("Women pref");
		System.out.println(womanPref[6][1]);
		System.out.println(womanPref[6][3]);
		System.out.println(womanPref[6][5]);

		
		for(int m =1; m<=2*n; m++){
			if (m%2 == 1) {
				Stack newStack = new Stack();
				while(!manPref[m].empty()){
					newStack.push(manPref[m].pop());
				}
				manPref[m]=newStack;				
			}
			
		}

		while(singleMen.size()!=0){
			/*System.out.println(singleMen.peek());
			char test = (char)singleMen.peek();
			int man = Character.getNumericValue(test);*/
			int man = (int)singleMen.peek();


			System.out.println("Man = "+man);
			int woman = (int)manPref[man].pop();
			System.out.println("Woman = "+woman);

			if (engaged[woman] < 0) {
				engaged[woman] = man;
				System.out.println(singleMen.pop());
				System.out.println("engaged = yes");
			} else if (engaged[woman] > 0 && (womanPref[woman][engaged[woman]] > womanPref[woman][man])) {
				singleMen.pop();
				singleMen.push(engaged[woman]);
				engaged[woman] = man;
				System.out.println("engaged = yes");
			} else {
				System.out.println("engaged = no");
			}
		}
	}

}