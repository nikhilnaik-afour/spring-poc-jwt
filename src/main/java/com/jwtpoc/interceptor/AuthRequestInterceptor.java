package com.jwtpoc.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jwtpoc.service.JwtService;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthRequestInterceptor implements HandlerInterceptor {

	@Autowired
	JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(" ||jwtService|| "+jwtService);
		
		try {
			System.out.println("## URI: "+request.getRequestURI());
			String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
			if (authorization == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				throw new Exception("Token Required!");
			}
			
			if (authorization.startsWith("Bearer")) {
				System.out.println(" ||jwtService|| "+jwtService);
				String jwttoken = authorization.substring("Bearer".length()).trim();
				jwtService.validateToken(jwttoken);
//				byte[] decodedBytes = Base64.getDecoder().decode(encoded);
//			    String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
//			    // credentials = username:password
//			    final String[] values = decoded.split(":", 2);
			}
			else if (authorization.startsWith("Basic")) {
				String encoded = authorization.substring("Basic".length()).trim();
				byte[] decodedBytes = Base64.getDecoder().decode(encoded);
			    String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
			    // credentials = username:password
			    final String[] values = decoded.split(":", 2);
			    String username = values[0];
			    String pass = values[1];
			}
			
			//			request.getHeaderNames().asIterator().forEachRemaining(e -> System.out.println("## Header: "+e));
//			request.getHeader("authorization")
//			System.out.println("## HEader names",request.getHeaderNames().);
			System.out.println("1 - preHandle() : Before sending request to the Controller"); 
            System.out.println("Method Type: " + request.getMethod()); 
            System.out.println("Request URL: " + request.getRequestURI()); 
		}
		catch (SignatureException e) {
			System.out.println("Invalid Sign: "+e);
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		catch (Exception e) {
			System.out.println("ERRRROR: "+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		
//		try { 
//            System.out.println("2 - postHandle() : After the Controller serves the request (before returning back response to the client)"); 
//            System.out.println("Method Type: " + request.getMethod()); 
//            System.out.println("Request URL: " + request.getRequestURI()); 
////            handler
//		}
//		
//        catch (Exception e) { 
//            e.printStackTrace(); 
//        } 
//		
//		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//	}
}
