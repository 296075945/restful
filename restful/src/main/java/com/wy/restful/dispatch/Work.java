package com.wy.restful.dispatch;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.wy.restful.annotation.Get;
import com.wy.restful.annotation.Post;
import com.wy.restful.annotation.Restful;
import com.wy.restful.entity.Handle;
import com.wy.restful.exception.NotFoundException;

import io.netty.handler.codec.http.HttpMethod;

public class Work {

	private ConcurrentMap<String, Handle> handers = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

	public Work(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	private ApplicationContext ctx;

	public void init() {
		String[] beanNames = ctx.getBeanNamesForAnnotation(Restful.class);
		for (String beanName : beanNames) {
			Object bean = ctx.getBean(beanName);
			Class<?> clazz = bean.getClass();

			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				Annotation[] annotations = method.getAnnotations();
				for (Annotation annotation : annotations) {
					if (Get.class == annotation.annotationType()) {
						String path = ((Get) annotation).value();
						addHandle(bean, method, path, HttpMethod.GET, getBodyType(method));
					} else if (Post.class == annotation.annotationType()) {
						String path = ((Post) annotation).value();
						addHandle(bean, method, path, HttpMethod.POST, getBodyType(method));
					}
				}
			}

			beans.put(beanName, bean);
		}
	}

	private void addHandle(Object bean, Method method, String path, HttpMethod httpMethod, Type bodyType) {
		Handle handle = new Handle();
		handle.setBean(bean);
		handle.setMethod(method);
		handle.setPath(path);
		handle.setHttpMethod(httpMethod);
		handle.setBodyType(bodyType);
		String key = httpMethod.name() + ":" + path;
		handers.putIfAbsent(key, handle);
	}

	public Object invoke(HttpMethod httpMethod, String path, String body) throws NotFoundException  {
		String key = httpMethod.name() + ":" + path;
		Handle handle = handers.get(key);
		if(handle == null) {
			throw new NotFoundException();
		}
		Object paramObj = JSON.parseObject(body, handle.getBodyType());
		if (handle != null) {
			try {
				return handle.getMethod().invoke(handle.getBean(), paramObj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private Type getBodyType(Method method) {
		return method.getParameters()[0].getParameterizedType();
	}
}
