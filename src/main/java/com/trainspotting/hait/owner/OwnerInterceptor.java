package com.trainspotting.hait.owner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.trainspotting.hait.exception.UnauthenticatedException;
import com.trainspotting.hait.exception.UnauthorizedException;
import com.trainspotting.hait.jwt.JwtProvider;

import io.jsonwebtoken.Claims;

public class OwnerInterceptor extends HandlerInterceptorAdapter {

	private final JwtProvider jwtProvider;
	
	public OwnerInterceptor(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Cookie[] cookies = request.getCookies();
		if(cookies == null) throw new UnauthenticatedException();

		String token = getToken(cookies);
		if(token == null) throw new UnauthenticatedException();
		
		Claims claims = jwtProvider.provideToken(token).getData();
		
		HttpSession session = request.getSession();
		if(session.getAttribute("r_pk") == null) {
			session.setAttribute("r_pk", claims.get("r_pk"));
		}
		
		if(request.getRequestURL().indexOf("/restaurant/initial") > 0) {
			 return true;
		};
		
		if(!"OWNER".equals(claims.get("role"))) throw new UnauthorizedException();
		
		return true;
	}
	
	private String getToken(Cookie[] cookies) {
		String token = null;
		for(Cookie cookie : cookies) {
			if("owner_token".equals(cookie.getName())) {
				token = cookie.getValue();
			}
		}
		return token;
	}
}
