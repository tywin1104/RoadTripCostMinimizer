package main;

import java.io.IOException;
import java.util.Stack;

import file_processing.DataLoader;
import graph.DepthFirstDirectedPaths;
import graph.Digraph;

public class Client {

	private static String dfsPath() {
		StringBuilder result = new StringBuilder("DFS: ");
		try {
			Digraph digraph = DataLoader.loadDigraphs();
			int from_index = DataLoader.getCityIndexByName("Boston");
			int to_index = DataLoader.getCityIndexByName("Minneapolis");

			DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(digraph, from_index);

			Stack<Integer> path = (Stack<Integer>) dfs.pathTo(to_index);
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
		System.out.println(dfsPath());
	}

}
