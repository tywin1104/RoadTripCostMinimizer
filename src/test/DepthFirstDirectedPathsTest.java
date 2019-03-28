package test;

import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file_processing.DataLoader;
import graph.DepthFirstDirectedPaths;
import graph.Digraph;

class DepthFirstDirectedPathsTest {
	Digraph digraph;
	int from_index;
	int to_index;

	@BeforeEach
	void setUp() throws Exception {
		digraph = DataLoader.loadDigraphs();
		from_index = DataLoader.getCityIndexByName("Boston");
		to_index = DataLoader.getCityIndexByName("Minneapolis");
	}

	@Test
	// test there is a route from Boston to Minneapolis
	void test_reachable() {
		DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(digraph, from_index);
		assertTrue(dfs.hasPathTo(to_index));
	}

	@Test
	// test for the correctness of the routes
	void test_path() {
		DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(digraph, from_index);
		Stack<Integer> path = (Stack<Integer>) dfs.pathTo(to_index);
		while (!path.empty()) {
			int v = path.pop();
			assertTrue(dfs.hasPathTo(v));
		}
	}

	@Test
	// test that all possible routes are examined(all connected cities are examined)
	void test_route_examination() {
		DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(digraph, from_index);
		for (int i = 0; i < digraph.V(); i++) {
			assertTrue(dfs.hasPathTo(i));
		}
	}
}
