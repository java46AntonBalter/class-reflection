package telran.annotation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telran.annotation.*;

public class Validator {

	public static List<String> validate(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> errors = new ArrayList<>();
		checkFields(obj, fields, errors);
		return errors;
	}

	private static void checkFields(Object obj, Field[] fields, List<String> errors) {
		Arrays.stream(fields).forEach(f -> {
			f.setAccessible(true);
			checkAnnotations(obj, errors, f);
		});
	}

	private static void checkAnnotations(Object obj, List<String> errors, Field field) {
		Annotation[] annotations = field.getAnnotations();
		if (annotations.length != 0) {
			Arrays.stream(annotations).forEach(annotation -> {
				switch (annotation.annotationType().getSimpleName()) {
				case "Min": {
					try {
						Min min = (Min) annotation;
						double setValue = min.value();
						var checkedValue = field.get(obj);
						if (checkedValue == null || checkedValue == "") {
							errors.add("Value absent");
							break;
						}
						if (isPrimitiveType(checkedValue)) {
							double checkedValueAsDouble = Double.valueOf("" + checkedValue);
							if (checkedValueAsDouble < setValue) {
								errors.add(min.message());
							}
							break;
						} else if (checkedValue.getClass().getSimpleName().equals("String")) {
							int length = (int) ((String) checkedValue).length();
							if (length < setValue) {
								errors.add(min.message());
							}
							break;
						}
						throw new IllegalArgumentException("Value type violation");

					} catch (Exception e) {
						errors.add(e.getMessage());
						break;
					}
				}
				case "Max": {
					try {
						Max max = (Max) annotation;
						double setValue = max.value();
						var checkedValue = field.get(obj);
						if (checkedValue == null || checkedValue == "") {
							errors.add("Value absent");
							break;
						}
						if (isPrimitiveType(checkedValue)) {
							double checkedValueAsDouble = Double.valueOf("" + checkedValue);
							if (checkedValueAsDouble > setValue) {
								errors.add(max.message());
							}
							break;
						} else if (checkedValue.getClass().getSimpleName().equals("String")) {
							int length = (int) ((String) checkedValue).length();
							if (length > setValue) {
								errors.add(max.message());
							}
							break;
						}
						throw new IllegalArgumentException("Value type violation");

					} catch (Exception e) {
						errors.add(e.getMessage());
						break;
					}

				}
				case "NotEmpty": {
					try {
						NotEmpty notEmpty = (NotEmpty) annotation;
						var checkedValue = field.get(obj);
						if (checkedValue == null || String.valueOf(checkedValue).equals("")) {
							errors.add(notEmpty.message());
							break;
						}
						break;

					} catch (Exception e) {
						errors.add(e.getMessage());
						break;
					}
				}
				case "Pattern": {
					try {
						Pattern pattern = (Pattern) annotation;
						String strValue = (String) field.get(obj);
						String regEx = pattern.value();
						if (strValue == null || strValue == "" || !strValue.matches(regEx)) {
							errors.add(pattern.message());
							break;
						}
						break;
					} catch (Exception e) {
						errors.add(e.getMessage());
						break;
					}

				}
				default:
					break;
				}
			});
		}
	}

	private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
	static {
		WRAPPER_TYPE_MAP = new HashMap<Class<?>, Class<?>>(16);
		WRAPPER_TYPE_MAP.put(Integer.class, int.class);
		WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
		WRAPPER_TYPE_MAP.put(Character.class, char.class);
		WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
		WRAPPER_TYPE_MAP.put(Double.class, double.class);
		WRAPPER_TYPE_MAP.put(Float.class, float.class);
		WRAPPER_TYPE_MAP.put(Long.class, long.class);
		WRAPPER_TYPE_MAP.put(Short.class, short.class);
		WRAPPER_TYPE_MAP.put(Void.class, void.class);
	}

	public static boolean isPrimitiveType(Object source) {
		return WRAPPER_TYPE_MAP.containsKey(source.getClass());
	}

}
