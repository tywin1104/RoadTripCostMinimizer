package adt;

import java.util.HashMap;
import java.util.Map;

public class City {
	private final String name;
	private final double latitude;
	private final double longitude;
	private double diningCost;
	private int graphIndex;
	private Map<String, Boolean> diningOptions;
	private Meal meal;

	public City(String name, double latitude, double longitude) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.meal = null;
		this.diningCost = 0;
		this.graphIndex = -1;
		this.initDiningOptions();
	}

	private void initDiningOptions() {
		this.diningOptions = new HashMap<>();
		diningOptions.put("MacDonald", false);
		diningOptions.put("BurgerKing", false);
		diningOptions.put("Wendy", false); 
	}

	public String getName() {
		return name;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getDiningCost() {
		return diningCost;
	}

	public int getGraphIndex() {
		return graphIndex;
	}

	public Map<String, Boolean> getDiningOptions() {
		return diningOptions;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setDiningCost(double diningCost) {
		this.diningCost = diningCost;
	}

	public void setGraphIndex(int graphIndex) {
		this.graphIndex = graphIndex;
	}

	public void setDiningOptions(Map<String, Boolean> diningOptions) {
		this.diningOptions = diningOptions;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
