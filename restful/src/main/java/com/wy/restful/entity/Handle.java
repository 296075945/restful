package com.wy.restful.entity;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import io.netty.handler.codec.http.HttpMethod;

public class Handle {

	private String path;
	private Object bean;
	private Method method;
	private HttpMethod httpMethod;

	private Type bodyType;
	
	
	public Type getBodyType() {
		return bodyType;
	}

	public void setBodyType(Type bodyType) {
		this.bodyType = bodyType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

}
