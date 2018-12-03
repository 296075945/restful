package com.wy.restful.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wy.restful.bootstrap.Server;
import com.wy.restful.dispatch.Work;

@Configuration
public class RestfulServerAutoConfiguration {

	@Autowired
	private ServerProperties serverProperties;
	
	@Autowired
	private ApplicationContext ctx;

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public Server server() {
		return new Server(serverProperties);
	}

	@Bean(initMethod = "init")
	public Work work() {
		return new Work(ctx);
	}
}
