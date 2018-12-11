package com.wy.demo.vo;

import javax.validation.constraints.NotBlank;

public class HelloVO {
	@NotBlank(message = " id 不能为空")
	private Integer id;
	@NotBlank(message = " id 不能为空")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
