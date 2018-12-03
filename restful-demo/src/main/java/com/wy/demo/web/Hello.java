package com.wy.demo.web;

import java.util.Map;

import com.wy.restful.annotation.Post;
import com.wy.restful.annotation.Restful;
import com.wy.restful.entity.Headers;

@Restful
public class Hello {

	@Post("/hello")
	public Map<String, String> hello(Map<String, String> hello) {
		Headers headers = Headers.getRequestHeaders();
		System.out.println(Thread.currentThread());
		return hello;
	}
}
