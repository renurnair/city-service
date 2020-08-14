package city_service;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.master.card.controller.CityController;
import com.master.card.service.CityService;

public class CityControllerTest {

	CityController classUnderTest = new CityController(); //Class under test
	private Map<String, ArrayList<String>> cityMap = new HashMap<String, ArrayList<String>>(); //to hold a mock city map to be passed to the CityService.java

	//JUnit Test which checks a Yes response from the service.
	@Test
	public void testCheckCities_Yes() {
		CityService cityService = new CityService();
		ReflectionTestUtils.setField(classUnderTest, "cityService", cityService); //Passing a mock Autowired object
		ArrayList<String> destinations = new ArrayList<>();
		destinations.add("Newark");
		cityMap.put("Boston", destinations);
		ReflectionTestUtils.setField(cityService, "cityMap", cityMap); //Passing a mock city map.
		assertSame("Yes", classUnderTest.checkCities("Boston", "Newark"));
	}
	
	//JUnit Test which checks a No response from the service.
	@Test
	public void testCheckCities_No() {
		CityService cityService = new CityService();
		ReflectionTestUtils.setField(classUnderTest, "cityService", cityService); //Passing a mock Autowired object
		ArrayList<String> destinations = new ArrayList<>();
		destinations.add("Trenton");
		cityMap.put("Boston", destinations);
		ReflectionTestUtils.setField(cityService, "cityMap", cityMap); //Passing a mock city map.
		assertSame("No", classUnderTest.checkCities("Boston", "Newark"));
	}
	
}
