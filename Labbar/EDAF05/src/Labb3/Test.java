package Labb3;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Test {
	
	EdgeComparator comp = new EdgeComparator();
	PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
	PriorityQueue<Integer> pqINT = new PriorityQueue<Integer>();
	
	public static void main(String[] args){
		Test te = new Test();
	}
	
	public Test() {
		Edge e1 = new Edge("aaaa1", "aaaa2", 50);
		Edge e2 = new Edge("bbbb1", "bbbb2", 32);
		Edge e3 = new Edge("cccc1", "cccc2", 9);
		Edge e4 = new Edge("dsfs", "adsa", 192);
		
		//pq.offer(e4);
		//pq.offer(e3);
		//pq.offer(e2);
		//pq.offer(e1);
		
		Random rm = new Random();
		
		for(int i = 0; i< 100;i++) {
			pq.offer(new Edge("aa", "bb", rm.nextInt(100)));
			pq.offer(new Edge("aa", "bb", rm.nextInt(100)));
		}
		
		
		while(pq.size()!=0) {
			System.out.println(pq.poll().weight);
		}
		System.out.println(pq.size());
		
		//for(Edge e : pq) {
			//System.out.println(e.weight);
		//}
		
	}
	
	private class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			// TODO Auto-generated method stub
			return Integer.compare(o1.weight, o2.weight);
		}
		
	}

}
