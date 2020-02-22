package com.taowd.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul过滤器.
 * 
 * @author Taoweidong
 */
@Component
public class LogRecodePostFilter extends ZuulFilter {

  /**
   * 日志工具.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(LogRecodePostFilter.class);

  /**
   * 服务请求端口.
   */
  @Value("${server.port}")
  private String serverPort;

  /**
   * 返回过滤器的类型
   * 
   * @return
   */
  @Override
  public String filterType() {

    return FilterConstants.POST_TYPE;
  }

  /**
   * 返回一个int值来指定过滤器的执行顺序，不同的过滤器允许返回相同的数字
   * 
   * @return
   */
  @Override
  public int filterOrder() {

    return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
  }

  /**
   * 返回一个boolean值来判断该过滤器是否要执行。
   * 
   * @return true表示执行，false表示不执行
   */
  @Override
  public boolean shouldFilter() {

    return true;
  }

  /**
   * 过滤器的具体逻辑
   * 
   * @return
   */
  @Override
  public Object run() {

    try {
      // 获取上下文
      RequestContext ctx = RequestContext.getCurrentContext();
      // 获取HttpServletRequest
      HttpServletRequest request = ctx.getRequest();
      String interfaceMethod = request.getRequestURI();
      LOGGER.info("端口{}，请求url={}", serverPort, interfaceMethod);
    } catch (Exception e) {
      LOGGER.error("ZuulFilter Occur exception.", e);
    }

    return null;
  }
}
