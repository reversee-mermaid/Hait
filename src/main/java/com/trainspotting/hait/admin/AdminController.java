package com.trainspotting.hait.admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainspotting.hait.ResponseBody;
import com.trainspotting.hait.model.AdminEntity;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private AdminService service;

	@PostMapping("/login")
	public ResponseEntity<ResponseBody> login(@RequestBody AdminEntity admin) {
		
		addTokenCookie(service.login(admin));
		return new ResponseEntity<>(
				new ResponseBody(200, "LOGIN_SUCCESS", null),
				HttpStatus.OK
				);
	}

	@GetMapping("/logout")
	public ResponseEntity<ResponseBody> logout() {
		
		addTokenCookie(null);
		return new ResponseEntity<>(
				new ResponseBody(200, "LOGOUT_SUCCESS", null),
				HttpStatus.OK
				);
	}
	
	private void addTokenCookie(String token) {
		Cookie cookie = new Cookie("admin_token", token);
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(token == null ? 0 : (24 * 60 * 60));
		response.addCookie(cookie);
	}
}
