package org.shikalenko.xmlanbind.impl.initializers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.shikalenko.xmlanbind.annotations.Attribute;
import org.shikalenko.xmlanbind.annotations.Element;
import org.shikalenko.xmlanbind.annotations.XPath;

public abstract class ReflectionHelper {
    private static final Class<?>[][] PRIMITIVES_WRAPPERS = {
        {boolean.class, Boolean.class},
        {byte.class, Byte.class},
        {char.class, Character.class},
        {double.class, Double.class},
        {float.class, Float.class},
        {int.class, Integer.class},
        {long.class, Long.class},
        {short.class, Short.class},
        {void.class, Void.class},
    };
    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS;
                                                
    static {
          Map<Class<?>, Class<?>> m = new HashMap<>(PRIMITIVES_WRAPPERS.length);
          PRIMITIVES_TO_WRAPPERS = m;
          for (Class<?>[] classes : PRIMITIVES_WRAPPERS) {
              m.put(classes[0], classes[1]);
          }
    }

    public static Collection<Method> findAttributeSetMethod(Class<? extends Object> clazz, String name) {
        String setterName = toSetterName(name);
        Method[] methods = clazz.getMethods();
        Collection<Method> result = new LinkedList<>();
        for (Method method : methods) {
            if (method.getAnnotation(XPath.class) != null) {
                continue;
            }
            Attribute attributeAnotation = method.getAnnotation(Attribute.class);
            if (method.getParameterCount() == 1) {
                if (attributeAnotation != null) {
                    if (name.equals(attributeAnotation.value())) {
                        result.clear();
                        result.add(method);
                        break;
                    }
                }
                if (setterName.equals(method.getName())) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    if (paramType.isAssignableFrom(String.class)) {
                        result.clear();
                        result.add(method);
                        break;
                    }
                    result.add(method);
                }
            }
        }
        return result;
    }

    public static Collection<Method> findElementSetMethod(Class<? extends Object> clazz, String name) {
        String setterName = toSetterName(name);
        Method[] methods = clazz.getMethods();
        Collection<Method> result = new LinkedList<>();
        for (Method method : methods) {
            if (method.getAnnotation(XPath.class) != null) {
                continue;
            }
            Element attributeAnotation = method.getAnnotation(Element.class);
            if (method.getParameterCount() == 1) {
                if (attributeAnotation != null) {
                    if (name.equals(attributeAnotation.value())) {
                        result.clear();
                        result.add(method);
                        break;
                    }
                }
                if (setterName.equals(method.getName())) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    if (paramType.isAssignableFrom(String.class)) {
                        result.clear();
                        result.add(method);
                        break;
                    }
                    result.add(method);
                }
            }
        }
        return result;
    }
    
    public static Collection<Method> findXPathMethod(Class<? extends Object> clazz) {
        Method[] methods = clazz.getMethods();
        Collection<Method> result = new LinkedList<>();
        for (Method method : methods) {
            if (method.getParameterCount() == 1) {
                if (method.getAnnotation(XPath.class) != null) {
                    result.add(method);
                }
            }
        }
        return result;
    }
    
    
    
    public static Method findValueOfMethod(Class<?> clazz) {
        Class<?> wrapper = PRIMITIVES_TO_WRAPPERS.get(clazz);
        return findValueOfMethod_(wrapper == null ? clazz : wrapper);
    }
    
    private static String toSetterName(String name) {
        return "set" + capitalize(name);
    }

    private static String capitalize(String name) {
        return name.length() > 0 ? 
                new StringBuilder().append(Character.toUpperCase(name.charAt(0))).append(name.substring(1)).toString()
                : name;
    }


    private static Method findValueOfMethod_(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if ("valueOf".equals(method.getName())) {
                if (Modifier.isStatic(method.getModifiers())) {
                    if (method.getParameterCount() == 1) {
                        Class<?> paramType = method.getParameterTypes()[0];
                        if (paramType.isAssignableFrom(String.class)) {
                            return method;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static <T> T istantiateByDefault(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = findDefaultConstructor(clazz);
        try {
            return constructor != null ? (T) constructor.newInstance() : null;
        } catch (Throwable e) {
            throw e;
        }
        
    }
    public static Constructor<?> findDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor;
            }
        }
        return null;
    }


}
