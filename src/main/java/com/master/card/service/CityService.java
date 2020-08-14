package com.master.card.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class CityService {

	private static final String CITY_FILE_NAME = "city.txt"; // File which contains the list of cities connected by roads
	private Map<String, ArrayList<String>> cityMap = new HashMap<String, ArrayList<String>>();
	private Map<String, Integer> indexes = new HashMap<>();// A map that will be used to store all the unique cities with a unique index assigned to each
	private int index = -1;

	// A method that will read the text file and create a HashMap of the form
	// {source city, list of all direct destination cities from that source}
	@PostConstruct
	public void createCityMap() {
		ClassLoader classLoader = getClass().getClassLoader();
		File newFile = new File(classLoader.getResource(CITY_FILE_NAME).getFile()); // load the text file
		try (Stream<String> stream = Files.lines(Paths.get(newFile.getAbsolutePath()))) { // Convert the text file into a stream where each line is a string
			stream.forEach(line -> { // Process each line in the file one by one
				String[] cities = line.split(", ");
				if (cityMap.containsKey(cities[0])) { // If the city map already contains this city no need to add it again. Just add the destination if that is not existing
					ArrayList<String> existingDestinations = cityMap.get(cities[0]);
					if (!existingDestinations.contains(cities[1])) {
						existingDestinations.add(cities[1]);
					}
					cityMap.put(cities[0], existingDestinations);
				} else { // Add this city since it is not existing in the city map. Also add the destination.
					ArrayList<String> destinations = new ArrayList<>();
					destinations.add(cities[1]);
					cityMap.put(cities[0], destinations);
					indexes.put(cities[0], ++index);
				}
				if (cityMap.containsKey(cities[1])) { // If there is a road from A -> B there also exists a road from B -> A. Add that to the map.
					ArrayList<String> existingDestinations = cityMap.get(cities[1]);
					if (!existingDestinations.contains(cities[0])) {
						existingDestinations.add(cities[0]);
					}
					cityMap.put(cities[1], existingDestinations);
				} else {
					ArrayList<String> destinations = new ArrayList<>();
					destinations.add(cities[0]);
					cityMap.put(cities[1], destinations);
					indexes.put(cities[1], ++index);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Public method that is exposed which checks whether two cities are connected.
	public String checkCities(String origin, String destination) {
		String[] result = { "No" };
		int cityCount = cityMap.size();
		boolean[] checked = new boolean[cityCount];

		// If city map has contains the origin city
		if (cityMap.containsKey(origin)) {
			if (cityMap.get(origin).contains(destination)) // If the source has a direct road to destination return "Yes"
				result[0] = "Yes";
			else { // If there is no direct road then check if any of the destinations of the city
					// has a direct road to the destination
				cityMap.get(origin).forEach(source -> {
					if ((null != indexes.get(source)) && !checked[indexes.get(source)]) {
						if (searchUtil(source, destination, checked))
							result[0] = "Yes";
					}
				});
			}
		}
		return result[0];
	}

	// Private utility method to check whether source and target are connected.
	private boolean searchUtil(Object source, String destination, boolean[] checked) {

		// mark this city as checked in order to avoid duplicate checking and infinite looping
		if (null != indexes.get(source)) {
			checked[indexes.get(source)] = true;
			ArrayList<String> list = cityMap.get(source);
			if (list.contains(destination))
				return true;
			for (int i = 0; i < list.size(); i++) {
				String dest = list.get(i); // If there is no direct road to the destination check the child destinations
											// again.
				if ((null != indexes.get(dest)) && !checked[indexes.get(dest)]) // Only check this city if it has not been checked already.
					if (searchUtil(dest, destination, checked))
						return true;
			}
		}
		return false; // Returns false only in the case that no matching road has been found.
	}

}
