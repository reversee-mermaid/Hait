package com.trainspotting.hait.application;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.model.ApplicationEntity;

@RestController
@RequestMapping("/api/admin/applications")
public class ApplicationController {
	
	@Autowired
	private ApplicationService service;
	
	@PutMapping
	public int update(@RequestBody ApplicationEntity p) throws UnsupportedEncodingException, MessagingException {
//		System.out.println(p.getOwner_email());
//		System.out.println(p.getProcess_status());
		return service.update(p);
	}
}