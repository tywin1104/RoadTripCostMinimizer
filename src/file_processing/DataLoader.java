package file_processing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adt.City;
import adt.Meal;
import au.com.bytecode.opencsv.CSVReader;

public class DataLoader {

	// Top 2 cheapest meals from McDonald's, Burger King and Wendy's
	public static final Meal M1 = new Meal("McChicken - Meal", 3.79);
	public static final Meal M2 = new Meal("Double Cheeseburger - Meal", 4.38);
	public static final Meal B1 = new Meal("Double Cheeseburgers - Meal", 5.49);
	public static final Meal B2 = new Meal("Chicken Nuggets (10 Pc.)", 5.89);
	public static final Meal W1 = new Meal("Dave's Hot 'n Juicy 1/4 lb. Single with Cheese", 6.19);
	public static final Meal W2 = new Meal("Spicy Chicken - Combo", 6.59);

	public static Map<Integer, City> IndexToCity = new HashMap<Integer, City>();

	public static ArrayList<City> loadCities() throws IOException, FileNotFoundException {
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

		return cities;
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

//	public static Digraph loadDigraphs(String file) {
////		City[] cities = loadCities(file)
//		// Read from connectedCities.txt
//		// For each pair, translate cityName -> cityName to int -> int
//		// init, add to the digraph and return the complete digraph
//	}

//	private static CitygetCityFromName(String name)  {
//	  //Helper function
//	}

//	public static EdgeWeightedDigraph LoadEdgeWeightedDigraph(String file) {
//		// Read from connectedCities.txt
//		// This time, construct an EdgeWeightedDigraph
//	}

	public static void main(String[] args) {
		try {
			ArrayList<City> cities = loadCities();
			for (City city : cities) {
				System.out.println(Arrays.asList(city.getDiningOptions()));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
