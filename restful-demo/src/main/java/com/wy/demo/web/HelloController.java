package com.wy.demo.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wy.demo.service.HelloService;
import com.wy.demo.vo.HelloVO;
import com.wy.restful.annotation.Post;
import com.wy.restful.annotation.Restful;

@Restful
public class HelloController {

	@Autowired
	private HelloService helloService;

	@Post("/hello")
	public HelloVO hello(Map<String, String> hello) {
		return helloService.getHello();
	}
}
