package city_service;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.master.card.service.CityService;

public class CityServiceTest {
	//Tests the CityService.java class and all its methods.
	CityService classUnderTest = new CityService();
	
	//JUnit Test which checks a direct path flow.
	@Test
	public void testCheckCities_DirectRoute() {
		classUnderTest.createCityMap();
		assertSame("Yes", classUnderTest.checkCities("Boston", "Newark"));
	}
	
	//JUnit Test which checks an indirect path flow. There is no direct road but a series of roads.
	@Test
	public void testCheckCities_IndirectRoute() {
		classUnderTest.createCityMap();
		assertSame("Yes", classUnderTest.checkCities("Boston", "Philadelphia"));
	}
	
	//JUnit Test which checks a no path flow.
	@Test
	public void testCheckCities_NoRoute() {
		classUnderTest.createCityMap();
		assertSame("No", classUnderTest.checkCities("Newark", "Albany"));
	}
	
	//JUnit Test which checks the flow when a non existent origin city is input.
	@Test
	public void testCheckCities_NoOriginCity() {
		classUnderTest.createCityMap();
		assertSame("No", classUnderTest.checkCities("Miami", "Albany"));
	}
	
	@Test
	public void testCheckCities_DuplicateSource() {
		Map<String, ArrayList<String>> cityMap = new HashMap<String, ArrayList<String>>(); //to hold a mock city map to be passed to the CityService.java
		ArrayList<String> destinations = new ArrayList<>();
		destinations.add("Newark");
		cityMap.put("Boston", destinations);
		ReflectionTestUtils.setField(classUnderTest, "cityMap", cityMap); //Passing a mock city map.
		classUnderTest.createCityMap();
	}
	
	@Test
	public void testCheckCities_DuplicateTarget() {
		Map<String, ArrayList<String>> cityMap = new HashMap<String, ArrayList<String>>(); //to hold a mock city map to be passed to the CityService.java
		ArrayList<String> destinations = new ArrayList<>();
		destinations.add("Boston");
		cityMap.put("Newark", destinations);
		ReflectionTestUtils.setField(classUnderTest, "cityMap", cityMap); //Passing a mock city map.
		classUnderTest.createCityMap();
	}
	
}
