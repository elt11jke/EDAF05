package Labb4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
public class closestPair {

	private ArrayList<Point> pointlist = new ArrayList<Point>();
	
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
					pointlist.add(new Point(id, x, y));
				} catch (Exception e) {
					
				}
			}

		}
	}
	

	private class Point implements Comparable<Point>, Comparator<Point> {
		public String id;
		public double x;
		public double y;

		public Point(String id, double x, double y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return id + " " + x + " " + y;
		}

		public double distanceToPoint(Point p) {
			return Math.hypot(x - p.x, y - p.y);
		}

		public int compareTo(Point q) {
			return (int) (q.x - this.x);
		}

		public int compare(Point p, Point q) {
			return (int) (p.y - q.y);
		}

	}
	
	public static void main(String[] args) {
		
	}
	
}
