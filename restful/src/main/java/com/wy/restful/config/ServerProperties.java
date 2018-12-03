package com.wy.restful.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="restful.server")
public class ServerProperties {

	private int port;
	private int maxMsgLength;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxMsgLength() {
		return maxMsgLength;
	}

	public void setMaxMsgLength(int maxMsgLength) {
		this.maxMsgLength = maxMsgLength;
	}
	
	
	
}
