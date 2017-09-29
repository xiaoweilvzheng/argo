package com.jx.argo.jxlifecycle;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class Lifecycle implements Serializable{

	
	public Lifecycle(HttpServletRequest request){
		this.request = getUrl(request);
		this.method = request.getMethod();
	}
	/**
	 * 请求URL
	 */
	private String request;
	/**
	 * 请求方式
	 */
	private String method;
	/**
	 * 创建时间
	 */
	private final long brithTime = System.currentTimeMillis();
	/**
	 * 上次执行结束时间
	 */
	private long lastTime = System.currentTimeMillis();
	/**
	 * 路由时间
	 */
	private long routeTime;
	/**
	 * 执行时间
	 */
	private long executeTime;
	/**
	 * 总耗时
	 */
	private long totalTime;
	
	/**
	 * 前置拦截
	 */
	private final Map<String,Long> preInterceptor = new LinkedHashMap<String,Long>();
	/**
	 * 后置拦截
	 */
	private final Map<String,Long> postInterceptor = new LinkedHashMap<String,Long>();
	
	
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getRouteTime() {
		return routeTime;
	}

	public void setRouteTime(long routeTime) {
		this.routeTime = routeTime;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(long executeTime) {
		this.executeTime = executeTime;
	}

	public long getBrithTime() {
		return brithTime;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public Map<String, Long> getPreInterceptor() {
		return preInterceptor;
	}

	public Map<String, Long> getPostInterceptor() {
		return postInterceptor;
	}

	public void setTimestamp(LifecycleState state) {
		this.setTimestamp(state, null);
	}

	public void setTimestamp(LifecycleState state,String clazz){
		long tempTime = lastTime;
		lastTime = System.currentTimeMillis();
		long costTime = lastTime - tempTime;
		totalTime = lastTime - brithTime;
		switch (state) {
			case ROUTE:
				this.routeTime = costTime;
				break;
			case PRE:
				this.preInterceptor.put(clazz,costTime);
				break;
			case EXECUTE:
				this.executeTime = costTime;
				break;
			case POST:
				this.postInterceptor.put(clazz,costTime);
				break;
		}
	}
	
	private String getUrl(HttpServletRequest request) {
		String requestURL = String.valueOf(request.getRequestURL());
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			requestURL += "?" + queryString;
		}
		requestURL = StringUtils.replace(requestURL, "http://", "");
		requestURL = StringUtils.replace(requestURL, "https://", "");
		return requestURL;
	}
}
