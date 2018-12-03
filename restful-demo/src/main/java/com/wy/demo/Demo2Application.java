package com.wy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.wy.restful.config.RestfulServerAutoConfiguration;

@SpringBootApplication
public class Demo2Application {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Demo2Application.class, args);
		RestfulServerAutoConfiguration configuration = ctx.getBean(RestfulServerAutoConfiguration.class);
		System.out.println();
	}
}
