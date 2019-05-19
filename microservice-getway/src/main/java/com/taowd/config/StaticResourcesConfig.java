package com.taowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourcesConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// // registry.addResourceHandler("/cms/broadcasts/*").addResourceLocations("file:D:\\static\\");
		// registry.addInterceptor(new LoginRequiredInterceptor())
		// .excludePathPatterns(Arrays.asList("/views/**", "/res/**"));

		// registry.addResourceHandler("/static/*").addResourceLocations("classpath:/static/");

	}
}