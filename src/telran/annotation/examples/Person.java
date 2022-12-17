package telran.annotation.examples;

import telran.annotation.*;


public class Person {
	@Id
	@Min(value = 20)
	@Max(value = 1000)
	private long id;
	@Pattern("[a-zA-Z]+")
	@Min(value = 5)
	@Max(value = 10)
	@NotEmpty
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
