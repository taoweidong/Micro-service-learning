package com.taowd.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

/**
 * 权限检查
 * 
 * @author Taoweidong
 */
public class AccessFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return null;
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return false;
  }

  @Override
  public Object run() throws ZuulException {
    return null;
  }
}
