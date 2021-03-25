package com.trainspotting.hait.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trainspotting.hait.ResponseBody;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(LoginFailedException.class)
	protected ResponseEntity<ResponseBody> handler(LoginFailedException e) {
		return new ResponseEntity<>(
				new ResponseBody(400, e.getMessage(), null),
				HttpStatus.BAD_REQUEST
				);
	}

	@ExceptionHandler({UnauthenticatedException.class, JwtInvalidException.class})
	protected ResponseEntity<ResponseBody> handler(RuntimeException e) {
		return new ResponseEntity<>(
				new ResponseBody(401, e.getMessage(), null),
				HttpStatus.UNAUTHORIZED
				);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<ResponseBody> handler(UnauthorizedException e) {
		return new ResponseEntity<>(
				new ResponseBody(403, e.getMessage(), null),
				HttpStatus.FORBIDDEN
				);
	}

	@ExceptionHandler(ReservDuplicatedException.class)
	protected ResponseEntity<ResponseBody> handler(ReservDuplicatedException e) {
		return new ResponseEntity<>(
				new ResponseBody(400, e.getMessage(), null),
				HttpStatus.BAD_REQUEST
				);
	}
}
