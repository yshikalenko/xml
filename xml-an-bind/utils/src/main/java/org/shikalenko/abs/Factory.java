package org.shikalenko.abs;

import static org.shikalenko.abs.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;


public abstract class Factory {
    
    protected <T> T instantiate(Class<T> clazz) throws FactoryException {
        assertNotNull(clazz, "class");
        String className = searchImlementation(clazz);
        if (className == null) {
            throw new FactoryException("Realization is not found of " + clazz + ". Check \"META-INF/services/\"" + clazz.getName() + " in the classpath");
        }
        try {
            return (T) Class.forName(className).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | 
                NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            throw new FactoryException("Instantiaition of " + clazz + " using \"META-INF/services/\"" + clazz.getName() + " fails", e);
        } catch (InvocationTargetException e) {
            throw new FactoryException("Instantiaition of " + clazz + " using \"META-INF/services/\"" + clazz.getName() + " fails", e.getTargetException());
        }
    }

    protected String searchImlementation(Class clazz) throws FactoryException {
        assertNotNull(clazz, "class");
        String resourcePath = "META-INF/services/" + clazz.getName(); 
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new FactoryException("Resource not found: " + resourcePath);
        }
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("ISO-8859-1")))) {
                return reader.readLine();
            }
        } catch (IOException e) {
            throw new FactoryException("IO Problem with resource: " + resourcePath, e);
        }
    }
    
}
