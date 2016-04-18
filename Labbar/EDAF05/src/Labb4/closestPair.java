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
					continue;
				}
			}

		}
	}
	
	

	private class Point {
		public String id;
		public double x;
		public double y;

		public Point(String id, double x, double y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		public double distanceToPoint(Point p) {
			return Math.hypot(x - p.x, y - p.y);
		}

	}
	
	public static void main(String[] args) {
		
	}
	
}
