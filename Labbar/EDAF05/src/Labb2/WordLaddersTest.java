package Labb2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class WordLaddersTest {
	
	public static void main(String[] args) {
	
	new WordLaddersTest().run();
		
	}

	private void run(){
		WordLadders w10 = new WordLadders("10");
		WordLadders w250 = new WordLadders("250");
		WordLadders w5757 = new WordLadders("5757");
		String[] nbrs = new String[]{"10","250","5757"};
		for(String str : nbrs){
			if(compare(str)){
				System.out.println("sant");
			}else{
				System.out.println("falsk");
			}			
		}

	}
	
	public boolean compare(String nbr){
		Scanner Out = null;
		try {
			Out = new Scanner(new File("src/Labb2/words-"+nbr+"-TESTOUT.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner Real = null;
		try {
			Real = new Scanner(new File("src/Labb2/words-"+nbr+"-out.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(Out.hasNext()&& Real.hasNext()){
			String out = Out.nextLine();
			String real = Real.nextLine();
			System.out.print(out);
			System.out.println(real);
			if(!out.equals(real)){
				return false;
			}
		}
		return true;
		
	   
	}
}
