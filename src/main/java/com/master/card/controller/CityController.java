package com.master.card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.master.card.service.CityService;

@RestController
public class CityController {
	
	@Autowired CityService cityService;

	@RequestMapping(method=RequestMethod.GET, path = "/connected", produces = "application/json")
	public String checkCities(@RequestParam String origin, @RequestParam String destination) {
		String result="No";
		result = cityService.checkCities(origin, destination);
		return result;
	}
}
