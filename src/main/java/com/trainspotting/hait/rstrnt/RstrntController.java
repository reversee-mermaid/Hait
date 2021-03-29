package com.trainspotting.hait.rstrnt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trainspotting.hait.ResponseBody;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.model.RstrntEntity;

@RestController
@RequestMapping("/api")
public class RstrntController {
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private RstrntService service;
	
	//owner
	@GetMapping("/restaurant/initial")
	public ResponseEntity<ResponseBody> restaurantInfo() {
		int pk = (int) session.getAttribute("r_pk");
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selRstrnt(pk)),
				HttpStatus.OK
				);
	}

	@PutMapping("/restaurant/initial")
	public ResponseEntity<ResponseBody> initialSetting(MultipartFile file, RstrntDTO dto, HttpServletRequest request) throws Exception {
		dto.setPk((int) session.getAttribute("r_pk"));
		addTokenCookie(service.initialSetting(file, dto, getToken(request))); 
		return new ResponseEntity<>(
				new ResponseBody(200, null, null),
				HttpStatus.OK
				);
	}

	@GetMapping("/restaurant")
	public ResponseEntity<ResponseBody> selRstrnt() {
		int pk = (int) session.getAttribute("r_pk");
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selRstrnt(pk)),
				HttpStatus.OK
				);
	}
	
	@PutMapping("/restaurant")
	public ResponseEntity<ResponseBody> updRstrnt(MultipartFile file, String more_info) throws Exception {
		int r_pk = (int) session.getAttribute("r_pk");
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.updRstrnt(r_pk, file, more_info)),
				HttpStatus.OK
				);
	}
	
	@PutMapping("/restaurant/state")
	public ResponseEntity<ResponseBody> updRstrntState(@RequestBody RstrntEntity param) {
		param.setPk((int) session.getAttribute("r_pk"));
		service.updRstrntState(param);
		return new ResponseEntity<>(
				new ResponseBody(200, "STATE_UPDATE_SUCCESS", null),
				HttpStatus.OK
				);
	}
	
	private String getToken(HttpServletRequest request) {
		String token = null;
		for(Cookie cookie : request.getCookies()) {
			if("owner_token".equals(cookie.getName())) {
				token = cookie.getValue();
			}
		}
		return token;
	}
	
	private void addTokenCookie(String token) {
		Cookie cookie = new Cookie("owner_token", token);
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(token == null ? 0 : (24 * 60 * 60));
		response.addCookie(cookie);
	}
	
	//customer
	@GetMapping("/restaurants")
	public ResponseEntity<ResponseBody> selRstrntAll(String nm, String city_pk) {
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selRstrntAll(nm, city_pk)),
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
}
