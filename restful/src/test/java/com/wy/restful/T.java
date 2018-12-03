package com.wy.restful;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.wy.restful.annotation.Get;
import com.wy.restful.annotation.Post;
import com.wy.restful.annotation.Restful;
import com.wy.restful.entity.Body;
import com.wy.restful.entity.Headers;

@Restful
public class T {

	@Get("/hello/world")
	public Object hello(Headers headers, Body<? extends List<String>> body) {
		return null;
	}

	public static void main(String[] args) throws ClassNotFoundException {

		Method[] methods = T.class.getDeclaredMethods();
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (Get.class == annotation.annotationType()) {
					System.out.println("get");

					Parameter[] parameters = method.getParameters();
					Parameter parameter = parameters[1];
					ParameterizedType type = (ParameterizedType) parameter.getParameterizedType();
					ParameterizedType type3 = (ParameterizedType) type.getActualTypeArguments()[0];

					System.out.println(type3);
				}
				if (Post.class == annotation.annotationType()) {
					System.out.println("post");
				}
			}

		}
		System.out.println();
	}
}
