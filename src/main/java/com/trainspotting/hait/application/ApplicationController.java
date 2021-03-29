package com.trainspotting.hait.application;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.ResponseBody;
import com.trainspotting.hait.model.ApplicationEntity;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
	
	@Autowired
	private ApplicationService service;
	
	@PostMapping
	public ResponseEntity<ResponseBody> insert(@RequestBody ApplicationEntity p) {
		service.insert(p);
		return new ResponseEntity<>(
				new ResponseBody(200, "APPLY_SUCCESS", null),
				HttpStatus.OK
				);
	}

//	@GetMapping
//	public ResponseEntity<ResponseBody> list(@RequestParam String process_status) {
//		Map<String, Object> json = new HashMap<>();
//		switch (process_status) {
//		case "":
//			json.put("data", service.listAll());
//			return json;
//		default:
//			json.put("data", service.listStatus(Integer.parseInt(process_status)));
//			return json;
//		}
//	}

	@GetMapping("/{pk}")
	public ResponseEntity<ResponseBody> detail(ApplicationEntity p) {
		service.insert(p);
		return new ResponseEntity<>(
				new ResponseBody(200, "DETAIL", service.detail(p)),
				HttpStatus.OK
				);
	}
	
	@PutMapping
	public int update(@RequestBody ApplicationEntity p) throws UnsupportedEncodingException, MessagingException {
		return service.update(p);
	}

}
