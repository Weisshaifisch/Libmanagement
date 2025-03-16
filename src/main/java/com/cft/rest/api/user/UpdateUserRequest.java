package com.cft.rest.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserRequest {

	private final String name;
	private final int age;

	public UpdateUserRequest(@JsonProperty(value = "name") String name, 
							 @JsonProperty(value = "age") int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
	
}
