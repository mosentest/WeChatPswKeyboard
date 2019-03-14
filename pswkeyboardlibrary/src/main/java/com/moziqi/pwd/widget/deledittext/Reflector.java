package com.moziqi.pwd.widget.deledittext;

import java.lang.reflect.Method;

public class Reflector {
    public static class TypedObject {
        private Object   obj;
        private Class<?> clazz;
 
        public TypedObject(Object obj, Class<?> clazz) {
            this.obj = obj;
            this.clazz = clazz;
        }
    }
 
    public static Object invokeMethodExceptionSafe(Object target, String methodName,
            TypedObject... typedObjects) {
        Object[] params = null;
        Class<?>[] paramClazzes = null;
        if (typedObjects != null && typedObjects.length > 0) {
            params = new Object[typedObjects.length];
            paramClazzes = new Class<?>[typedObjects.length];
 
            for (int i = 0; i < typedObjects.length; i++) {
                params[i] = typedObjects[i].obj;
                paramClazzes[i] = typedObjects[i].clazz;
            }
        }
 
        Method method;
        Class<?> targetClass = target.getClass();
        do {
            method = getMethod(targetClass, methodName, paramClazzes);
            if (method != null) {
                break;
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != Object.class);
 
        if (method != null) {
            if(!method.isAccessible()) {
                method.setAccessible(true);
            }
            try {
                return method.invoke(target, params);
            } catch (Exception e) {
            }
        }
        return null;
    }
 
    private static Method getMethod(Class<?> target, String methodName, Class<?>... types) {
        try {
            return target.getDeclaredMethod(methodName, types);
        } catch (NoSuchMethodException e) {
        }
        return null;
    }
}