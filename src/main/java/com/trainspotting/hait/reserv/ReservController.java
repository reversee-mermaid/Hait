package com.trainspotting.hait.reserv;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.ResponseBody;
import com.trainspotting.hait.model.ReservEntity;

@RestController
@RequestMapping("/api")
public class ReservController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ReservService service;
	
	//owner	
	@GetMapping("/reservations")
	public ResponseEntity<ResponseBody> selReservAll() {
		int pk = (int) session.getAttribute("r_pk");
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selReservAll(pk)),
				HttpStatus.OK
				);
	}
	
	@PutMapping("/reservations")
	public ResponseEntity<ResponseBody> updReservStatus(@RequestBody ReservEntity param) {
		service.updReservStatus(param);
		return new ResponseEntity<>(
				new ResponseBody(200, null, null),
				HttpStatus.OK
				);
	}
	
	//customer
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
