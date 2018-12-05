package com.wy.demo.service.impl;

import org.springframework.stereotype.Service;

import com.wy.demo.service.HelloService;
import com.wy.demo.vo.HelloVO;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public HelloVO getHello() {
		HelloVO helloVO = new HelloVO();
		helloVO.setId(10);
		helloVO.setName("weiyang");
		return helloVO;
	}

}
