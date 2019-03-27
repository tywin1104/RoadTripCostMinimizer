package graph;

import java.util.Stack;

import edu.princeton.cs.algs4.Queue;

public class BreadthFirstDirectedPaths {
	private final int s;
	private boolean[] marked; // marked[v] = is there an s->v path?
	private int[] edgeTo; // edgeTo[v] = last edge on shortest s->v path

	public BreadthFirstDirectedPaths(Digraph G, int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		this.s = s;
		bfs(G, s);
	}

	private void bfs(Digraph G, int s) {
		Queue<Integer> q = new Queue<Integer>();
		marked[s] = true;
		q.enqueue(s);
		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}

	public boolean hasPathTo(int v) {
		return marked[v];
	}

	public Iterable<Integer> pathTo(int v) {
		if (!hasPathTo(v))
			return null;
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; x != s; x = edgeTo[x])
			path.push(x);
		path.push(s); 
		return path;
	}
}
