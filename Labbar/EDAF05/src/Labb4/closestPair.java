package Labb4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class closestPair {

	private ArrayList<Point> pointlist = new ArrayList<Point>();
	private ArrayList<Double> xList = new ArrayList<Double>();
	private int recCount;
	
	public void readFile(File file){
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] splitLine = line.replaceAll("\\s+", " ").trim().split(" ");

			if (splitLine.length == 3) {
				try {
					String id = splitLine[0]; 
					double x = Double.parseDouble(splitLine[1]);
					double y = Double.parseDouble(splitLine[2]);
					Point p = new Point(x, y);
					p.id = id;
					pointlist.add(p);
				} catch (Exception e) {
					continue;
				}
			}

		}
		//STARTTEST
		/*for(Point p : pointlist) {
			System.out.print(p.x+" ");
		}
		System.out.println("\n");
		Collections.sort(pointlist, new MyComparator());
		for(Point p : pointlist) {
			System.out.print(p.x+" ");
		}
		System.out.println("\n");*/
		//ENDTEST
	}
	
	private void mkXList() {
		for(int i=0 ; i<pointlist.size(); i++){
			Point p = pointlist.get(i);
			xList.add(p.x);
		}
	}
	
	private Pair findClosest(ArrayList<Double> xS, ArrayList<Point> pS){
		
		if (xS.size() <= 3){
			return bruteForce(xS,pS);
		} else {
			ArrayList<Double> xL = new ArrayList<Double>();
			ArrayList<Double> xR = new ArrayList<Double>();
			ArrayList<Point> pL = new ArrayList<Point>();
			ArrayList<Point> pR = new ArrayList<Point>();
			
			for(int i=0; i< xS.size(); i++){
				if(i <=xS.size()/2){
					xL.add(xS.get(i));
					pL.add(pS.get(i));
				}
				else {
					xR.add(xS.get(i));
					pR.add(pS.get(i));
				}
			}
			double xmid = xS.get(xS.size()/2);
			Pair pLeft = findClosest(xL, pL);
			Pair pRight = findClosest(xR, pR);
			double distMin = pRight.distance;
			Pair minPair = pRight;
			if (pLeft.distance < distMin){
				distMin = pLeft.distance;
				minPair = pLeft;
			}
			ArrayList<Point> cPoints = new ArrayList<Point>();
			for(Point p : pS){
				if(Math.abs(xmid- p.x) < distMin){
					cPoints.add(p);
				}
			}
			System.out.println("Mergelist size " + cPoints.size()); //TODO remove
			double closest = distMin;
			System.out.println("distmin " + distMin); //TODO remove
			Pair closestPair = minPair;
			
			for(int i=0; i< cPoints.size()-1; i++) {
				int k=i+1;

				while(k < cPoints.size() && Math.abs(cPoints.get(k).y-cPoints.get(i).y) < distMin){
					System.out.println("Merge points " + cPoints.get(k).distanceToPoint(cPoints.get(i)));
					if (cPoints.get(k).distanceToPoint(cPoints.get(i)) < closest){
						closest = cPoints.get(k).distanceToPoint(cPoints.get(i));
						closestPair = new Pair(cPoints.get(k), cPoints.get(i), cPoints.get(k).distanceToPoint(cPoints.get(i)));
						System.out.println("Closest distance after merging = " + closestPair.distance); //TODO remove
					}
					k++;
				}
			}
			
			//System.out.println("Recursion count = " + ++recCount);
			System.out.println("Closest distance after recursion = " + closestPair.distance + "\n\n"); //TODO remove
			//System.out.println("From pair: " + closestPair.p1.id + " -- " + closestPair.p2.id);
			
			return closestPair;
		}
		
	}
	
	private Pair bruteForce(ArrayList<Double> xS, ArrayList<Point> pS){
		Pair par = new Pair(new Point(0.0, 0.0), new Point(Double.MAX_VALUE, Double.MAX_VALUE), Double.MAX_VALUE);
		
		for(int i=0; i< xS.size(); i++){
			Point p = pS.get(i);
			
			for(int k=i+1; k< xS.size(); k++){
				Point pp = pS.get(k);
				if(p.distanceToPoint(pp) < par.p1.distanceToPoint(par.p2)) {
					par = new Pair(p,pp, p.distanceToPoint(pp));
				}
			}
			
		}
		return par;
	}

	private class Point {
		public String id;
		public double x;
		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public double distanceToPoint(Point p) {
			return Math.hypot(x - p.x, y - p.y);
		}
		
		public int compareTo(Point p) {
			return Double.compare(x, p.x);
		}

	}
	
	public class MyComparator implements Comparator<Point> {
		@Override
		public int compare(Point p1, Point p2) {
			return p1.compareTo(p2);
		}
		
	}
	
	private class Pair {
		public Point p1;
		public Point p2;
		public double distance;
		
		public Pair(Point p1,Point p2,double distance){
			this.p1 = p1;
			this.p2 = p2;
			this.distance = distance;
		}
	}
	
	public static void main(String[] args) {
		new closestPair().run();
	}
	
	public void run() {
		recCount = 0;
		
		long start = System.currentTimeMillis();
		readFile(new File("src/Labb4/att48-tsp.txt"));
		mkXList();
		Pair p = findClosest(xList,pointlist);
		long end = System.currentTimeMillis();
		
		System.out.println("Closest distance = " + p.distance);
		System.out.println("From pair: " + p.p1.id + " -- " + p.p2.id);
		
		System.out.println("The algorithm took " + (end -start) + " ms. The expected complexity was nlogn, with time "+ pointlist.size()*Math.log(pointlist.size())+ " ms");
		
	}
	
}
