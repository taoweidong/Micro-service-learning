package com.taowd.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import java.util.Objects;

/**
 * Zuul过滤器.
 */
@Component
public class LogRecodePostFilter extends ZuulFilter {

	/**
	 * 日志工具.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LogRecodePostFilter.class);

	@Override
	public String filterType() {

		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {

		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
	}

	@Override
	public boolean shouldFilter() {

		RequestContext context = RequestContext.getCurrentContext();
		String serviceId = Objects.toString(context.get("serviceId"));
		LOGGER.debug("请求的服务id------>" + serviceId);
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletRequest request = ctx.getRequest();
			String interfaceMethod = request.getServletPath();
			LOGGER.info("请求url={}", interfaceMethod);
		} catch (Exception e) {
			LOGGER.error("ZuulFilter Occur exception.", e);
		}

		return null;
	}
}
