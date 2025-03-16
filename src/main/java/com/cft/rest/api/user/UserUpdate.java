package com.cft.rest.api.user;

public class UserUpdate {

	private final long id;
	private final String name;
	private final Integer age;
	
	public UserUpdate(long id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}	
	
}
