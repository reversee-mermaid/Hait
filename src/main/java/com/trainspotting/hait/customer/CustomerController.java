package com.trainspotting.hait.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.ResponseBody;
import com.trainspotting.hait.model.ReservEntity;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping("/restaurants")
	public ResponseEntity<ResponseBody> selRstrntAll() {
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selRstrntAll()),
				HttpStatus.OK
				);
	}
	
	@GetMapping("/restaurants/{pk}")
	public ResponseEntity<ResponseBody> selDetail(@PathVariable int pk) {
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selRstrnt(pk)),
				HttpStatus.OK
				);
	}
	
	@GetMapping("/reservation/{pk}")
	public ResponseEntity<ResponseBody> selReserv(@PathVariable int pk) {
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selReserv(pk)),
				HttpStatus.OK
				);
	}
	
	@PostMapping("/reservation")
	public ResponseEntity<ResponseBody> insReserv(@RequestBody ReservEntity param) {
		service.insReserv(param);
		return new ResponseEntity<>(
				new ResponseBody(200, null, param.getPk()),
				HttpStatus.OK
				);
	}
}
