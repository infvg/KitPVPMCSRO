package net.mcsro.kitpvp.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {
	
 public static Object getField(Object object, String field)
   throws SecurityException, NoSuchFieldException,
   IllegalArgumentException, IllegalAccessException {
  Class<?> c = object.getClass();
  Field objectField = c.getDeclaredField(field);
  objectField.setAccessible(true);
  Object result = objectField.get(object);
  objectField.setAccessible(false);
  return result;
 }
}