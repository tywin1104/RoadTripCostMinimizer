package graph;

import edu.princeton.cs.algs4.Bag;

//Reference: textbook
//ADT for directed graph
public class Digraph {
	private final int V;
	private int E;
	private Bag<Integer>[] adj;
	private static final String NEWLINE = System.getProperty("line.separator");

	public Digraph(int V) {
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdge(int v, int w) {
		adj[v].add(w);
		E++;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(String.format("%d: ", v));
			for (int w : adj[v]) {
				s.append(String.format("%d ", w));
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
}
