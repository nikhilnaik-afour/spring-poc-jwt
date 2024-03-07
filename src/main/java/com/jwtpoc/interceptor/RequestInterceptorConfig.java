package com.jwtpoc.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestInterceptorConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthRequestInterceptor authRequestInterceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
		// multiple interceptors can be added
		// specific path for each interceptor can be added as well
		registry.addInterceptor(authRequestInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/error");
	}
	
}
