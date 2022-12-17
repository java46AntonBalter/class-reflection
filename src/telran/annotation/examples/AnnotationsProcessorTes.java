package telran.annotation.examples;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import telran.annotation.validator.Validator;

class AnnotationsProcessorTes {

	@Test	
	void getIdTest() throws Exception {
		
		Person person = new Person(123, "Vasya");
		assertEquals(123, AnnotationsProcessor.getIdValue(person));
		X x = new X();
		assertNull(AnnotationsProcessor.getIdValue(x));
	}
	@Test
	void patternValidateTest() {
		Person person1 = new Person(123, "1Vasya");
		assertFalse(AnnotationsProcessor.validatePattern(person1).isEmpty());
		Person person2 = new Person(123, "Vasya");
		assertTrue(AnnotationsProcessor.validatePattern(person2).isEmpty());
		X x = new X();
		String errorMessage = AnnotationsProcessor.validatePattern(x);
		assertTrue(errorMessage.isEmpty());
		System.out.println(errorMessage);
	}
	@Test
	void validatorTest() {
		Person person = new Person(10, "Vas");
		List<String> errors = Validator.validate(person);
		assertEquals(2, errors.size());
		for(String error:errors) {
			System.out.println(error);
		}
		
		Person person1 = new Person(1100, "Vasyavasyavasya1");
		errors = Validator.validate(person1);
		assertEquals(3, errors.size());
		for(String error:errors) {
			System.out.println(error);
		}
		
		person1.setName(null);
		errors = Validator.validate(person1);
		assertEquals(5, errors.size());
		for(String error:errors) {
			System.out.println(error);
		}
		
	}
}
