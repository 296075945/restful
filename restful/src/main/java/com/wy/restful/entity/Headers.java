package com.wy.restful.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Headers implements Iterable<Map.Entry<String, String>> {

	public static final String CONTENT_TYPE = "content-type";
	public static final String CONTENT_LENGTH = "content-length";

	private Map<String, String> headers = new HashMap<>();

	private static ThreadLocal<Headers> requestHeaders = new ThreadLocal<>();
	private static ThreadLocal<Headers> responseHeaders = new ThreadLocal<>();

	private Headers() {

	}

	public String get(String key) {
		return headers.get(key);
	}

	public void putAll(Map<String, String> m) {
		headers.putAll(m);
	}

	public void put(String key, String value) {
		headers.put(key, value);
	}

	public static Headers getRequestHeaders() {
		Headers headers = requestHeaders.get();
		if (headers == null) {
			headers = new Headers();
			requestHeaders.set(headers);
		}
		return headers;
	}

	public static Headers getResponseHeaders() {
		Headers headers = responseHeaders.get();
		if (headers == null) {
			headers = new Headers();
			responseHeaders.set(headers);
		}
		return headers;
	}

	public static void removeThreadLocal() {
		requestHeaders.remove();
		responseHeaders.remove();
	}

	@Override
	public Iterator<Entry<String, String>> iterator() {
		return headers.entrySet().iterator();
	}
}
