package test;

import static org.junit.Assert.assertTrue;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file_processing.DataLoader;
import graph.BreadthFirstDirectedPaths;
import graph.Digraph;

class BreadthFirstDirectedPathsTest {

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
		BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, from_index);
		assertTrue(bfs.hasPathTo(to_index));
	}

	@Test
	// test for the correctness of the routes
	void test_path() {
		BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, from_index);
		Stack<Integer> path = (Stack<Integer>) bfs.pathTo(to_index);
		while (!path.empty()) {
			int v = path.pop();
			assertTrue(bfs.hasPathTo(v));
		}
	}

	@Test
	// test that all possible routes are examined(all connected cities are examined)
	void test_route_examination() {
		BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, from_index);
		for (int i = 0; i < digraph.V(); i++) {
			assertTrue(bfs.hasPathTo(i));
		}
	}
}
