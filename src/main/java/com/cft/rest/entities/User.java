package com.cft.rest.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Size(min = 5, max = 100)
	@Column(unique = true, nullable = false)
	private String name;	
	
	@Pattern(regexp = "f|m", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Column(nullable = false)
	private String sex;
	
	@Column(nullable = false)
	private Integer age;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Loan> loans = new ArrayList<>();
	
	public User() {
	}

	public User(@NotNull String name, @NotNull String sex, @NotNull Integer age) {
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public void setId(long id) {
		this.id = id;
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

	public Integer getAge() {
		return age;
	}

	public void addLoan(Loan loan) {
		loans.add(loan);
	}
	
	public List<Loan> getLoans() {
		return new ArrayList<>(loans);
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", loans=" + Arrays.toString(loans.toArray())+ "]";
	}


}
