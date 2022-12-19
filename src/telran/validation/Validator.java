package telran.validation;

import java.lang.reflect.*;
import java.util.*;

import telran.validation.constraints.*;

import java.lang.annotation.Annotation;

public class Validator {
	/**
	 * validates the given object against the constraints in the package
	 * telran.annotation.validation.constraints
	 * 
	 * @param obj
	 * @return list constraint violation messages or empty list if no violations
	 */

	static public List<String> validate(Object obj) {
		List<String> res = new ArrayList<>();
		if (obj != null) {
			Field fields[] = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				for (Annotation annotation : field.getAnnotations()) {

					processAnnotation(res, obj, annotation, field);
				}
			}
		}

		return res;
	}

	static String max(Field field, Object obj) {
		Max annotation = field.getAnnotation(Max.class);

		return maxMinValidation(field, annotation, obj, true);
	}

	private static String maxMinValidation(Field field, Annotation annotation, Object obj, boolean isMax) {

		Object value = null;
		try {
			value = field.get(obj);
		} catch (Exception e) {

		}
		String strValue = value.toString();
		if (value instanceof String) {
			return stringMinMaxValidation(field, annotation, strValue, isMax);
		} else {

		}
		return value instanceof String ? stringMinMaxValidation(field, annotation, strValue, isMax)
				: numberMinMaxValidation(field, annotation, strValue, isMax);

	}

	private static String numberMinMaxValidation(Field field, Annotation annotation, String strValue, boolean isMax) {
		String res = "";

		try {

			double fieldValue = Double.parseDouble(strValue);
			if (isMax) {
				Max maxAnnotation = (Max) annotation;
				if (fieldValue > maxAnnotation.value()) {
					res = maxAnnotation.message();
				}
			} else {
				Min minAnnotation = (Min) annotation;
				if (fieldValue < minAnnotation.value()) {
					res = minAnnotation.message();
				}
			}
		} catch (Exception e) {
			res = "Wrong annotation with exception " + e.getMessage();
		}

		return res;

	}

	private static String stringMinMaxValidation(Field field, Annotation annotation, String strValue, boolean isMax) {
		int length = strValue.length();
		String res = null;
		if (isMax) {
			Max maxAnnotation = (Max) annotation;
			if (length > maxAnnotation.value()) {
				res = maxAnnotation.message();
			}

		} else {
			Min minAnnotation = (Min) annotation;
			if (length < minAnnotation.value()) {
				res = minAnnotation.message();
			}
		}
		return res;
	}

	static String min(Field field, Object obj) {
		Min annotation = field.getAnnotation(Min.class);
		return maxMinValidation(field, annotation, obj, false);
	}

	static String patern(Field field, Object obj) {
		Pattern paternAnnotation = field.getAnnotation(Pattern.class);
		String res = paternAnnotation.message();
		try {
			res = ((String) field.get(obj)).matches(paternAnnotation.value()) ? "" : paternAnnotation.message();
		} catch (Exception e) {
			res = "Wrong annotation with exception " + e.getMessage();
		}
		return res;
	}

	private static void processAnnotation(List<String> errorMessages, Object obj, Annotation annotation, Field field) {
		String methodName = null;
		try {
			methodName = annotation.annotationType().getSimpleName().toLowerCase();
			Method method = Validator.class.getDeclaredMethod(methodName, Field.class, Object.class);
			method.setAccessible(true);
			String message = (String) method.invoke(null, field, obj);
			if (!message.isEmpty()) {
				errorMessages.add(message);
			}

		} catch (Exception e) {
			System.out.printf("anootation %s is not a validation annotation\n", methodName);
		}

	}
}
