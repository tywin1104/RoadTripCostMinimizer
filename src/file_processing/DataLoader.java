package file_processing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import adt.City;
import adt.Meal;
import au.com.bytecode.opencsv.CSVReader;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import graph.Digraph;

public class DataLoader {

	// Top 2 cheapest meals from McDonald's, Burger King and Wendy's
	public static final Meal M1 = new Meal("McChicken - Meal", 3.79);
	public static final Meal M2 = new Meal("Double Cheeseburger - Meal", 4.38);
	public static final Meal B1 = new Meal("Double Cheeseburgers - Meal", 5.49);
	public static final Meal B2 = new Meal("Chicken Nuggets (10 Pc.)", 5.89);
	public static final Meal W1 = new Meal("Dave's Hot 'n Juicy 1/4 lb. Single with Cheese", 6.19);
	public static final Meal W2 = new Meal("Spicy Chicken - Combo", 6.59);

	public static ArrayList<City> cities;
	public static Map<Integer, City> IndexToCity = new HashMap<Integer, City>();

	private static void loadCities() throws IOException, FileNotFoundException {
		ArrayList<City> cities = new ArrayList<City>();
		String fileIn = "data/USCities.csv";

		// Load Cities from CSV file
		CSVReader reader = new CSVReader(new FileReader(fileIn), ',', '"', 1);
		List<String[]> allRows = reader.readAll();
		for (String[] row : allRows) {
			String name = row[3];
			double latitude = Double.parseDouble(row[4]);
			double longitude = Double.parseDouble(row[5]);
			cities.add(new City(name, latitude, longitude));
		}

		// Assign each city with an unique index for graph processing
		// Maintain a mapping between int -> city for further use
		for (int i = 0; i < cities.size(); i++) {
			City city = cities.get(i);
			city.setGraphIndex(i);
			IndexToCity.put(i, city);
		}

		// Load restaurant options for each city for further use
		for (City city : cities) {
			loadRestaurants(city);
		}

		DataLoader.cities = cities;
	}

	// Helper function to get index of a specific city in the graph by its name
	public static int getCityIndexByName(String name) {
		if (cities == null)
			throw new RuntimeException("Cities are not loaded yet");

		for (City city : cities) {
			if (city.getName().toLowerCase().equals(name.trim().toLowerCase()))
				return city.getGraphIndex();
		}
		return -1;
	}

	private static void loadRestaurants(City city) throws IOException, FileNotFoundException {
		// Check for restaurant options for each city
		// Check for : McDonald's , Burger King, and Wendy's separately
		boolean options[] = { false, false, false };
		String files[] = { "data/mcdonalds.csv", "data/burgerking.csv", "data/wendys.csv" };
		for (int i = 0; i < files.length; i++) {
			CSVReader reader = new CSVReader(new FileReader(files[i]), ',', '"', 1);
			String[] line;
			line = reader.readNext();
			while (line != null) {
				double latitude = Double.parseDouble(line[1]);
				double longitude = Double.parseDouble(line[0]);
				// To check if there is a restaurant that satisfy the location requirement
				// If there is, mark the option as available and go on to check next restaurant
				if (Math.abs(city.getLatitude() - latitude) <= 0.5
						&& Math.abs(city.getLongitude() - longitude) <= 0.5) {
					options[i] = true;
					break;
				} else {
					line = reader.readNext();
				}
			}
		}

		// Update the City object with the restaurant options
		HashMap<String, Boolean> diningOptions = new HashMap<>();
		diningOptions.put("MacDonald", options[0]);
		diningOptions.put("BurgerKing", options[1]);
		diningOptions.put("Wendy", options[2]);
		city.setDiningOptions(diningOptions);
	}

	public static Digraph loadDigraphs() throws IOException, FileNotFoundException {
		// Read city connection data from connectedCities.txt
		// Build Directed Graph to support DFS and BFS graph processing algorithms
		if (DataLoader.cities == null)
			try {
				loadCities();
			} catch (Exception e) {
				e.printStackTrace();
			}
		// Init the Digraph data structure
		Digraph digraph = new Digraph(cities.size());

		String fileIn = "data/connectedCities.txt";
		CSVReader reader = new CSVReader(new FileReader(fileIn), ',', '"', 0);
		List<String[]> allRows = reader.readAll();
		for (String[] row : allRows) {
			String from = row[0].toLowerCase().trim();
			String to = row[1].toLowerCase().trim();
			// Find the corresponding index in graph from loaded cities by their names
			// Add each connection as edge to the Digraph
			int index_from = getCityIndexByName(from);
			int index_to = getCityIndexByName(to);
			assert index_from != -1 && index_to != -1;
			digraph.addEdge(index_from, index_to);
		}
		// Return the complete Digraph
		return digraph;
	}

	public static EdgeWeightedDigraph LoadEdgeWeightedDigraph() throws IOException {
		if (DataLoader.cities == null)
			try {
				loadCities();
			} catch (Exception e) {
				e.printStackTrace();
			}
		// Init the Digraph data structure
		EdgeWeightedDigraph graph = new EdgeWeightedDigraph(cities.size());
		ArrayList<Integer[]> unfinished = new ArrayList<Integer[]>();

		String fileIn = "data/connectedCities.txt";
		CSVReader reader = new CSVReader(new FileReader(fileIn), ',', '"', 0);
		List<String[]> allRows = reader.readAll();
		for (String[] row : allRows) {
			int index_from = getCityIndexByName(row[0].toLowerCase().trim());
			int index_to = getCityIndexByName(row[1].toLowerCase().trim());
			City city_from = IndexToCity.get(index_from);
			City city_to = IndexToCity.get(index_to);
			// If the origin city is BOSTON, can eat anywhere
			Meal prev_meal = city_from.getMeal();
			Meal selected_meal;
			if (index_from == 0) {
				selected_meal = M1;
				city_to.setMeal(selected_meal);
				DirectedEdge edge = new DirectedEdge(index_from, index_to, selected_meal.getPrice());
				graph.addEdge(edge);
				continue;
			}
			if (prev_meal != null) {
				if (prev_meal.equals(M1))
					selected_meal = M2;
				else {
					selected_meal = M1;
				}
				city_to.setMeal(selected_meal);
				DirectedEdge edge = new DirectedEdge(index_from, index_to, selected_meal.getPrice());
				graph.addEdge(edge);
			} else {
				unfinished.add(new Integer[] { index_from, index_to });
			}
		}

		for (ListIterator<Integer[]> pair = unfinished.listIterator(); pair.hasNext();) {
			Integer[] value = pair.next();
			int index_from = value[0];
			int index_to = value[1];
			City city_from = IndexToCity.get(index_from);
			City city_to = IndexToCity.get(index_to);
			Meal prev_meal = city_from.getMeal();
			Meal selected_meal;
			if (prev_meal != null) {
				if (prev_meal.equals(M1))
					selected_meal = M2;
				else {
					selected_meal = M1;
				}
				city_to.setMeal(selected_meal);
				DirectedEdge edge = new DirectedEdge(index_from, index_to, selected_meal.getPrice());
				graph.addEdge(edge);
			}
			pair.remove();
		}
		return graph;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
//		Digraph di = loadDigraphs();
//		System.out.println(di);
//		loadCities();
//		System.out.println(IndexToCity);
		EdgeWeightedDigraph graph = LoadEdgeWeightedDigraph();
		System.out.println(graph);
	}

}
