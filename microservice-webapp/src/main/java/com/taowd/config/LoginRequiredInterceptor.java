package com.taowd.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定義拦截器
 */
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(LoginRequiredInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		logger.info(request.getRequestURI());
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {

		logger.info(request.getRequestURI());
		super.afterCompletion(request, response, handler, ex);
	}
}
