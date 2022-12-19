package telran.annotation.examples;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

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
		assertFalse(errorMessage.isEmpty());
		System.out.println(errorMessage);

}
}
