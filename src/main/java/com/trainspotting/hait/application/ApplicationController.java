package com.trainspotting.hait.application;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.ResponseBody;
import com.trainspotting.hait.model.ApplicationEntity;

@RestController
@RequestMapping("/api")
public class ApplicationController {
	
	@Autowired
	private ApplicationService service;
	
	// For guest - home application
	@PostMapping("/application")
	public ResponseEntity<ResponseBody> insert(@RequestBody ApplicationEntity p) {
		service.insert(p);
		return new ResponseEntity<>(
				new ResponseBody(200, "APPLY_SUCCESS", null),
				HttpStatus.OK
				);
	}

	@GetMapping("/applications")
	public ResponseEntity<ResponseBody> list(int process_status) {
		return new ResponseEntity<> (
				new ResponseBody(200, null, service.listAll(process_status)),
				HttpStatus.OK
				);
	}

	@GetMapping("/applications/{pk}")
	public ResponseEntity<ResponseBody> detail(@PathVariable int pk) {
		return new ResponseEntity<>(
				new ResponseBody(200, "DETAIL", service.detail(pk)),
				HttpStatus.OK
				);
	}
	
	@PutMapping("/applications")
	public ResponseEntity<ResponseBody> update(@RequestBody ApplicationEntity p) throws UnsupportedEncodingException, MessagingException {
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.update(p)),
				HttpStatus.OK
				);
	}
}
