package Labb4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ClosePairTest {
	private double[] res;
	private String[] inputFile;
	
	public static void main(String[] args) {
		new ClosePairTest().run();		
	}

	private void run(){
		  res = new double[95];
		  inputFile = new String[95];
		  getFile();
		  if(testAlg()) {
			  System.out.println("All test were successfull");
		  } else {
			  System.out.println("The algorithm is flawed");
		  }
	}
	
	public boolean testAlg(){
		  for(int i=0; i<95; i++){
			  closestPair cp = new closestPair(inputFile[i]);
			  if(Math.round(res[i]*100.0)/100.0 != Math.round(res[i]*100.0)/100.0){
				  return false;
			  }
		  }
		  return true;
	}
	
	public void getFile(){
		Scanner sc = null;
		String str;
		int pos = 0;
		try {
			sc = new Scanner(new File("src/Labb4/closest-pair-out.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		while(sc.hasNext()){
			str = sc.nextLine();
			str = str.substring(8);
			String[] split = str.split(":");
			StringBuilder sb = new StringBuilder(split[0]);
			sb.setCharAt(sb.length()-4, '-');
			inputFile[pos] = sb.toString();
			Scanner sc2 = new Scanner(split[1]);
			sc2.nextInt();
			res[pos] = Double.parseDouble(sc2.next());
			
			pos++;
		}
	}
}
