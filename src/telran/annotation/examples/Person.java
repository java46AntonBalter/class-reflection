package telran.annotation.examples;

import telran.annotation.*;
import telran.validation.constraints.Pattern;


public class Person {
	@Id
	private long id;
	@Pattern("[A-Z][a-z]{1,10}")
	private String name;
	
	public Person(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
