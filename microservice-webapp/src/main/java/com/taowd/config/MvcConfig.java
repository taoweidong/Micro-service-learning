package com.taowd.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//
	// // registry.addResourceHandler("/static/*").addResourceLocations("classpath:/static/");
	// // registry.addResourceHandler("/cms/broadcasts/*").addResourceLocations("file:D:\\static\\");
	// registry.addInterceptor(new LoginRequiredInterceptor())
	// .excludePathPatterns(Arrays.asList("/views/**", "/res/**"));
	// }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new LoginRequiredInterceptor())
				.excludePathPatterns(Arrays.asList("/jquery-easyui-1.5.5.2/**"));
	}
}
