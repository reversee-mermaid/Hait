package com.trainspotting.hait.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainspotting.hait.model.ApplicationEntity;

@Service
public class HomeService {
	
	@Autowired
	private HomeMapper mapper;
	
	public int insert(ApplicationEntity p) {
		return mapper.insert(p);
	}
}
