package org.shikalenko.xmlanbind;

import javax.xml.transform.Source;

import org.shikalenko.abs.Factory;
import org.shikalenko.abs.FactoryException;
import org.xml.sax.InputSource;

public abstract class SAXInitializer {

    static class SAXInitializerFactory extends Factory {

        public SAXInitializer create() throws FactoryException {
            return instantiate(SAXInitializer.class);
        }

    }
    
    private static SAXInitializer instance;
    private static SAXInitializerFactory factory = new SAXInitializerFactory();

    public synchronized static SAXInitializer getInstance() throws FactoryException {
        if (instance == null) {
            instance = factory.create();
        }
        return instance;
    }

    public abstract void initObject(InputSource inputSource, Object object) throws BindException;

    public abstract void initObject(Source source, Object object) throws BindException;

    
}
