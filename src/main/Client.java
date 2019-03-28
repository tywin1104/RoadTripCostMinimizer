package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Stack;

import adt.City;
import file_processing.DataLoader;
import graph.BreadthFirstDirectedPaths;
import graph.DepthFirstDirectedPaths;
import graph.Digraph;
import graph.DijkstraSP;
import graph.DirectedEdge;
import graph.EdgeWeightedDigraph;

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

	private static void getShortestPath() throws IOException {
		EdgeWeightedDigraph graph = DataLoader.LoadEdgeWeightedDigraph();
		int origin_index = DataLoader.getCityIndexByName("Boston");
		int dest_index = DataLoader.getCityIndexByName("Minneapolis");

		DijkstraSP sp = new DijkstraSP(graph, origin_index);
		for (DirectedEdge edge : sp.pathTo(dest_index)) {
			City from_city = DataLoader.IndexToCity.get(edge.from());
			City to_city = DataLoader.IndexToCity.get(edge.to());
			if (edge.from() == origin_index) {
				to_city.setMeal(DataLoader.M1);
				continue;
			}
			if (from_city.getMeal() == DataLoader.M1)
				to_city.setMeal(DataLoader.M2);
			else
				to_city.setMeal(DataLoader.M1);
		}

		Object[] header = new Object[] { "City", "Meal Choice", "Cost of Meal" };
		System.out.format("%15s%35s%40s\n", header);
		System.out.format("%15s%35s%35s\n", new Object[] { "Boston", "N/A", "N/A" });
		for (DirectedEdge edge : sp.pathTo(dest_index)) {
			City to_city = DataLoader.IndexToCity.get(edge.to());
			Object[] row = new Object[] { to_city.getName(), to_city.getMeal().getName(),
					to_city.getMeal().getPrice() };
			System.out.format("%15s%35s%35s\n", row);
		}

	}

	public static void generate_output() {
		try {
			// Redirect system output stream to the specific output file
			PrintStream fileOut = new PrintStream("./a2_out.txt");
			System.setOut(fileOut);
			// Write to the file necessary information
			System.out.println(getPath(true));
			System.out.println(getPath(false));
			System.out.println();
			try {
				getShortestPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		generate_output();
	}
}
