package com.cft.rest.api.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

	private final String name;
	private final String sex;
	private final int age;
	
	@JsonCreator
	public CreateUserRequest(@JsonProperty(value = "name", required = true) String name, 
							 @JsonProperty(value = "sex", required = true) String sex, 
							 @JsonProperty(value = "age", required = true) int age) {
		
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public int getAge() {
		return age;
	}
	
}
