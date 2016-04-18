package Labb3;

public class Edge implements Comparable<Edge>{
	
	public String source;
	public String target;
	public int weight;

	public Edge(String source, String target, int weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge o) {
		return Integer.compare(weight, o.weight);
	}


}
