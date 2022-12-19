package telran.annotation.examples;

import java.lang.reflect.*;

import telran.annotation.*;
import telran.validation.constraints.Pattern;

public class AnnotationsProcessor {
static public Long getIdValue(Object obj) throws Exception{
	Field[] fields = obj.getClass().getDeclaredFields();
	Long id = null;
	for(Field field: fields) {
		field.setAccessible(true);
		if (field.isAnnotationPresent(Id.class)) {
			id = field.getLong(obj);
			break;
		}
	}
	return id;
}
static public String validatePattern(Object obj) {
	Field[] fields = obj.getClass().getDeclaredFields();
	String res = "";
	for(Field field: fields) {
		field.setAccessible(true);
		Pattern pattern = field.getAnnotation(Pattern.class);
		if (pattern != null) {
			try {
				String strValue = (String) field.get(obj);
				String regEx = pattern.value();
				if(!strValue.matches(regEx)) {
					res += ";" + pattern.message();
				}
				
			} catch (Exception e) {
				res += e.getMessage();
			}
		}
	}
	return res;
	
}
}
