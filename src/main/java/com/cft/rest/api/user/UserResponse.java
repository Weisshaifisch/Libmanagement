package com.cft.rest.api.user;

import com.cft.rest.entities.User;

public class UserResponse {

	private final long id;
	private final String name;
	private final String sex;
	private final int age;
	
	public UserResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.sex = user.getSex();
		this.age = user.getAge();
	}

	public long getId() {
		return id;
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
