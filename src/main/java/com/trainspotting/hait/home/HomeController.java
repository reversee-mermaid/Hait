package com.trainspotting.hait.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.model.ApplicationEntity;

@CrossOrigin
@RestController
@RequestMapping("/api/home")
public class HomeController {
	
	@Autowired
	private HomeService service;
	
	@PostMapping("/applications")
	public int insert(@RequestBody ApplicationEntity p) {
		return service.insert(p);
	}
}
