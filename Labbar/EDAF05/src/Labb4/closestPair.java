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
	private static int stepNbr;
	private double distance;
	
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
		Collections.sort(pointlist, new MyComparator());
	}
	
	private void mkXList() {
		for(int i=0 ; i<pointlist.size(); i++){
			Point p = pointlist.get(i);
			xList.add(p.x);
		}
	}
	
	public double getDistance(){
		return this.distance;
	}
	
	private Pair findClosest(List<Double> xL2, List<Point> pL2){
		
		if (xL2.size() <= 3){
			return bruteForce(xL2,pL2);
		} else {
			List<Double> xL = xL2.subList(0, xL2.size()/2);
			List<Double> xR = xL2.subList(xL2.size()/2, xL2.size());
			List<Point> pL = pL2.subList(0, pL2.size()/2);
			List<Point> pR = pL2.subList(pL2.size()/2, pL2.size());

			double xmid = xL2.get(xL2.size()/2);
			Pair pLeft = findClosest(xL, pL);
			Pair pRight = findClosest(xR, pR);
			double distMin = pRight.distance;
			Pair minPair = pRight;
			if (pLeft.distance < distMin){
				distMin = pLeft.distance;
				minPair = pLeft;
			}
			ArrayList<Point> cPoints = new ArrayList<Point>();
			for(Point p : pL2){
				stepNbr++;
				if(Math.abs(xmid- p.x) < distMin){
					cPoints.add(p);
				}
			}
			Collections.sort(cPoints, new YCordComparator());
			double closest = distMin;
			Pair closestPair = minPair;
			
			for(int i=0; i< cPoints.size()-1; i++) {
				Point p1 = cPoints.get(i);
				for(int j=i+1; j<cPoints.size()-2; j++) {
					stepNbr++;
					Point p2 = cPoints.get(j);
					if((p2.y-p1.y) >= distMin){ break;}
					double distance = p1.distanceToPoint(p2);
					if(distance < closest) {
						closestPair = new Pair(p1, p2, distance);
						closest = distance;
					}
				}
			}
			return closestPair;
		}
		
	}
	
	private Pair bruteForce(List<Double> xL2, List<Point> pL2){
		Pair par = new Pair(new Point(0.0, 0.0), new Point(Double.MAX_VALUE, Double.MAX_VALUE), Double.MAX_VALUE);
		
		for(int i=0; i< xL2.size(); i++){
			Point p = pL2.get(i);
			
			for(int k=i+1; k< xL2.size(); k++){
				stepNbr++;
				Point pp = pL2.get(k);
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
	public class YCordComparator implements Comparator<Point> {
		@Override
		public int compare(Point p1, Point p2) {
			return Double.compare(p1.x, p2.x);
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
	
	public closestPair(String arg) {
		stepNbr=0;
		long start = System.currentTimeMillis();
		readFile(new File("src/Labb4/"+arg+".txt"));
		mkXList();
		Pair p = findClosest(xList,pointlist);
		double distance = p.distance;
		long end = System.currentTimeMillis();
		
		System.out.println("Closest distance = " + p.distance);
		System.out.println("From pair: " + p.p1.id + " -- " + p.p2.id);
		
		System.out.println("\nThe algorithm had approx. " + stepNbr + " steps.\n\tn^2 steps = " + pointlist.size()*pointlist.size() + "\n\tn*logn steps = " + pointlist.size()*Math.log(pointlist.size())+ "\n\tn(logn)^2 steps = " +pointlist.size()*Math.log(pointlist.size())*Math.log(pointlist.size())+"\n\n");
		
	}
	
}
