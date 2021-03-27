package com.trainspotting.hait.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.ResponseBody;

@RestController
public class CityController {
	
	@Autowired
	CityMapper mapper;
	
	@GetMapping("/api/cities")
	public ResponseEntity<ResponseBody> cities() {
		return new ResponseEntity<>(
				new ResponseBody(200, null, mapper.selectAll()),
				HttpStatus.OK
				);
	}
}