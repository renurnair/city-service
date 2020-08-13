package city_service;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.master.card.service.CityService;

public class CityServiceTest {
	
	CityService classUnderTest = new CityService();
	

	@Test
	public void testCreateCityMap_IOException() {
		classUnderTest.createCityMap();
		
	}
	
	@Test
	public void testCheckCities_DirectRoute() {
		classUnderTest.createCityMap();
		assertSame("Yes", classUnderTest.checkCities("Boston", "Newark"));
	}
	
	@Test
	public void testCheckCities_IndirectRoute() {
		classUnderTest.createCityMap();
		assertSame("Yes", classUnderTest.checkCities("Boston", "Philadelphia"));
	}
	
	@Test
	public void testCheckCities_NoRoute() {
		classUnderTest.createCityMap();
		assertSame("No", classUnderTest.checkCities("Newark", "Albany"));
	}
	
	@Test
	public void testCheckCities_NoOriginCity() {
		classUnderTest.createCityMap();
		assertSame("No", classUnderTest.checkCities("Miami", "Albany"));
	}
	
}
