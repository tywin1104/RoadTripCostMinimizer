package file_processing;

import java.util.HashMap;
import java.util.Map;

import adt.City;
import adt.Meal;
import graph.Digraph;
import graph.EdgeWeightedDigraph;

public class DataLoader {
	
	// Top 2 cheapest meals from McDonald's, Burger King and Wendy's
	public static final Meal M1 = new Meal("McChicken - Meal", 3.79);
	public static final Meal M2 = new Meal("Double Cheeseburger - Meal", 4.38);
	public static final Meal B1 = new Meal("Double Cheeseburgers - Meal", 5.49);
	public static final Meal B2 = new Meal("Chicken Nuggets (10 Pc.)", 5.89);
	public static final Meal W1 = new Meal("Dave's Hot 'n Juicy 1/4 lb. Single with Cheese", 6.19); 
	public static final Meal W2 = new Meal("Spicy Chicken - Combo", 6.59); 
	
	public static Map<Integer, City> IndexToCity = new HashMap<Integer, City>();
	 
	
	public static City[] loadCities(String file) {
		City[] cities = new City[40];
		// Load city data from USCities.csv
		// For each city, assign an unique graph_index
		//After assigning graph_index to city, add to the IndexToCity mapping
		//Call loadRestaurants for this city 
		return cities;
	}
	
	private static void loadRestaurants(String file, City city) {
		//helper function
		//Read all three files: wendys.csv mcdonalds.csv burgerking.csv
		//To init the diningOptions field for a specific  city
	}

	public static Digraph loadDigraphs(String file) {
//		City[] cities = loadCities(file)
		// Read from connectedCities.txt
		// For each pair, translate cityName -> cityName to int -> int
		// init, add to the digraph and return the complete digraph
	}

//	private static CitygetCityFromName(String name)  {
//	  //Helper function
//	}

	public static EdgeWeightedDigraph LoadEdgeWeightedDigraph(String file) {
		//Read from connectedCities.txt
		//This time, construct an EdgeWeightedDigraph
	}
	
}
