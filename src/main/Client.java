package main;

import java.io.IOException;
import java.util.Stack;

import file_processing.DataLoader;
import graph.BreadthFirstDirectedPaths;
import graph.DepthFirstDirectedPaths;
import graph.Digraph;

public class Client {

	// Run DFS or BFS on the digraph built by city connections to find path
	// DFS or BFS is determined by argument is_bfs
	// And return the path found from Boston to Minneapolis
	private static String getPath(boolean is_bfs) {
		// Init the return string
		StringBuilder result = null;
		try {
			// Build the directed graph and specify the origin and destination
			Digraph digraph = DataLoader.loadDigraphs();
			int from_index = DataLoader.getCityIndexByName("Boston");
			int to_index = DataLoader.getCityIndexByName("Minneapolis");
			Stack<Integer> path = null;

			// Decide which algorithm to run based on the argument
			if (is_bfs) {
				result = new StringBuilder("BFS: ");
				BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, from_index);
				path = (Stack<Integer>) bfs.pathTo(to_index);
			} else {
				result = new StringBuilder("DFS: ");
				DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(digraph, from_index);
				path = (Stack<Integer>) dfs.pathTo(to_index);
			}

			// Obtain the corresponding path and build the String from the stack
			while (!path.empty()) {
				int index = path.pop();
				String cityName = DataLoader.IndexToCity.get(index).getName();
				if (index == to_index)
					result.append(cityName);
				else
					result.append(cityName + " , ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	 

	public static void main(String[] args) {
		System.out.println(getPath(true));
		System.out.println(getPath(false));

	}

}
