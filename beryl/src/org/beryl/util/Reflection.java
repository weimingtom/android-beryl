package org.beryl.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflection {
	
	public static final ArrayList<Class<?>> ZeroParameters = new ArrayList<Class<?>>();
	/**
	 * Basically Class.getMethod but allows for a list of parameter types instead of a variable arguments list.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method findDeclaredMethod(final Class<?> clazz, final String methodName, List<Class<?>> paramTypes) throws SecurityException, NoSuchMethodException {
		Method method = null;
		Method [] methods = clazz.getDeclaredMethods();
		
		if(paramTypes == null) {
			paramTypes = ZeroParameters;
		}
		
		for(Method candidate : methods) {
			if(candidate.getName().equals(methodName)) {
				
				Class<?>[] candidateParamTypes = candidate.getParameterTypes();
				if(candidateParamTypes.length == paramTypes.size()) {
					final int length = candidateParamTypes.length;
					boolean parametersMatch = true;
					for(int i = 0; i < length && parametersMatch; i++) {
						if(! candidateParamTypes[i].isAssignableFrom(paramTypes.get(i))) {
							parametersMatch = false;
						}
					}
					if(parametersMatch) {
						method = candidate;
						method.setAccessible(true);
						break;
					}
				}
			}
		}
		
		/* Try this to:
		method = clazz.getDeclaredMethod(methodName, (Class<?>[]) paramTypes.toArray());
		if(method != null) {
			method.setAccessible(true);
		}
		*/
		return method;
	}
}
