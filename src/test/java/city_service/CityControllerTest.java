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

	CityController classUnderTest = new CityController();
	private Map<String, ArrayList<String>> cityMap = new HashMap<String, ArrayList<String>>();

	@Test
	public void testCheckCities() {
		CityService cityService = new CityService();
		ReflectionTestUtils.setField(classUnderTest, "cityService", cityService);
		ArrayList<String> destinations = new ArrayList<>();
		destinations.add("Newark");
		cityMap.put("Boston", destinations);
		ReflectionTestUtils.setField(cityService, "cityMap", cityMap);
		assertSame("Yes", classUnderTest.checkCities("Boston", "Newark"));
	}

}
