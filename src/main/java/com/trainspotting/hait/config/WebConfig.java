package com.trainspotting.hait.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.trainspotting.hait.admin.AdminInterceptor;
import com.trainspotting.hait.jwt.JwtProvider;
import com.trainspotting.hait.owner.OwnerInterceptor;

@EnableWebMvc
@MapperScan(basePackages = "com.trainspotting.hait")
@ComponentScan(basePackages = "com.trainspotting.hait")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/");
		resolver.setSuffix(".html");
		return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(new AdminInterceptor(jwtProvider))
				.addPathPatterns("/api/applications/**");
		registry
			.addInterceptor(new OwnerInterceptor(jwtProvider))
				.addPathPatterns("/api/restaurant/**")
				.addPathPatterns("/api/reservations/**");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/res/**").addResourceLocations("/resources");
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
