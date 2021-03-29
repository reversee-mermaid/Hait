package com.trainspotting.hait.owner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.ResponseBody;
import com.trainspotting.hait.model.OwnerEntity;

@RestController
@RequestMapping("/api/owner")
class OwnerController {

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpSession session;

	@Autowired
	private OwnerService service;
	
	@GetMapping
	public ResponseEntity<ResponseBody> owner() {
		int r_pk = (int) session.getAttribute("r_pk");
		return new ResponseEntity<>(
				new ResponseBody(200, null, service.selOwnerByRstrntPk(r_pk)),
				HttpStatus.OK
				);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseBody> login(@RequestBody OwnerEntity p) {
		addTokenCookie(service.login(p));
		return new ResponseEntity<>(
				new ResponseBody(200, "LOGIN_SUCCESS", null),
				HttpStatus.OK
				);
	}

	@GetMapping("/logout")
	public ResponseEntity<ResponseBody> logout() {
		session.removeAttribute("r_pk");
		addTokenCookie(null);
		return new ResponseEntity<>(
				new ResponseBody(200, "LOGOUT_SUCCESS", null),
				HttpStatus.OK
				);
	}

	private void addTokenCookie(String token) {
		Cookie cookie = new Cookie("owner_token", token);
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(token == null ? 0 : (24 * 60 * 60));
		response.addCookie(cookie);
	}
	

}
