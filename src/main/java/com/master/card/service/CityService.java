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

	private static final String CITY_FILE_NAME = "city.txt";
	private Map<String, ArrayList<String>> cityMap = new HashMap<String, ArrayList<String>>();

	// A method that will read the text file and create a HashMap of the form
	// {source city, list of all direct destination cities from that source}
	@PostConstruct
	public void createCityMap() {
		ClassLoader classLoader = getClass().getClassLoader();
		File newFile = new File(classLoader.getResource(CITY_FILE_NAME).getFile());
		try (Stream<String> stream = Files.lines(Paths.get(newFile.getAbsolutePath()))) {
			stream.forEach(line -> {
				String[] cities = line.split(", ");
				if (cityMap.containsKey(cities[0])) {
					ArrayList<String> existingDestinations = cityMap.get(cities[0]);
					if (!existingDestinations.contains(cities[1])) {
						existingDestinations.add(cities[1]);
					}
					cityMap.put(cities[0], existingDestinations);
				} else {
					ArrayList<String> destinations = new ArrayList<>();
					destinations.add(cities[1]);
					cityMap.put(cities[0], destinations);
				}
				if (cityMap.containsKey(cities[1])) {
					ArrayList<String> existingDestinations = cityMap.get(cities[1]);
					if (!existingDestinations.contains(cities[0])) {
						existingDestinations.add(cities[0]);
					}
					cityMap.put(cities[1], existingDestinations);
				} else {
					ArrayList<String> destinations = new ArrayList<>();
					destinations.add(cities[0]);
					cityMap.put(cities[1], destinations);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String checkCities(String origin, String destination) {
		String[] result = { "No" };
		if (cityMap.containsKey(origin)) {
			if (cityMap.get(origin).contains(destination))
				result[0] = "Yes";
			else {
				ArrayList<String> destinations = cityMap.get(origin);
				destinations.forEach(dest -> {
					if (cityMap.get(dest).contains(destination))
						result[0] = "Yes";
				});
			}
		}
		return result[0];
	}

}
