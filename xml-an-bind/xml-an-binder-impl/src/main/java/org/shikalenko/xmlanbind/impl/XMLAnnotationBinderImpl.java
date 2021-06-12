package org.shikalenko.xmlanbind.impl;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.shikalenko.abs.FactoryException;
import org.shikalenko.xmlanbind.BindException;
import org.shikalenko.xmlanbind.DomInitializer;
import org.shikalenko.xmlanbind.SAXInitializer;
import org.shikalenko.xmlanbind.XMLAnnotationBinder;
import org.shikalenko.xmlanbind.XPathContext;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XMLAnnotationBinderImpl extends XMLAnnotationBinder {

    XPathContext xpathContext;
    
    public void cleanXPathContext() {
        this.xpathContext = xpathContext;
    }

    @Override
    public <T> T bind(Class<T> clazz, String xml) throws BindException {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        Object object = instantiate(clazz);
        bind(object, xml);
        return (T)object;
    }

    @Override
    public <T> void bind(T object, String xml) throws BindException {
        if (xml == null) {
            throw new NullPointerException("xml");
        }
        if (xml.trim().length() == 0) {
            throw new IllegalArgumentException("xml is empty");
        }
        if (hasFeature(FEATURE_XPATH)) {
            bind(object, toNode(xml));
            return;
        }
        try {
            SAXInitializer.getInstance().initObject(newInputSource(xml), object);
        } catch (FactoryException e) {
            throw new BindException(e);
        }
    }
    
    @Override
    public <T> T bind(Class<T> clazz, Node node) throws BindException {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        Object object = instantiate(clazz);
        bind(object, node);
        return (T)object;
    }

    @Override
    public <T> void bind(T object, Node node) throws BindException {
        if (node == null) {
            throw new NullPointerException("node");
        }
        try {
            DomInitializer domInitializer = DomInitializer.newInstance();
            domInitializer.setFeatures(getFeatures());
            domInitializer.setXPathContext(xpathContext);
            domInitializer.initObject(node, object);
        } catch (FactoryException e) {
            throw new BindException(e);
        }
    }
    
    @Override
    public <T> T bind(Class<T> clazz, InputSource inputSource) throws BindException {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        Object object = instantiate(clazz);
        bind(object, inputSource);
        return (T)object;
    }

    @Override
    public <T> void bind(T object, InputSource inputSource) throws BindException {
        if (inputSource == null) {
            throw new NullPointerException("inputSource");
        }
        if (hasFeature(FEATURE_XPATH)) {
            bind(object, toNode(inputSource));
            return;
        }
        try {
            SAXInitializer.getInstance().initObject(inputSource, object);
        } catch (FactoryException e) {
            throw new BindException(e);
        }
    }
    
    @Override
    public <T> T bind(Class<T> clazz, Source source) throws BindException {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        Object object = instantiate(clazz);
        bind(object, source);
        return (T)object;
    }

    @Override
    public <T> void bind(T object, Source source) throws BindException {
        if (source == null) {
            throw new NullPointerException("source");
        }
        if (source instanceof DOMSource || hasFeature(FEATURE_XPATH)) {
            bind(object, toNode(source));
            return;
        }
        try {
            SAXInitializer.getInstance().initObject(source, object);
        } catch (FactoryException e) {
            throw new BindException(e);
        }
    }
    

    @Override
    public XPathContext getXPathContext() {
        if (xpathContext == null) {
            xpathContext = new XPathContextImpl();
        }
        return xpathContext;
    }

    @Override
    protected boolean needNSSupport() {
        return xpathContext != null;
    }
    
    private Object instantiate(Class<?> clazz) throws BindException {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException 
                | NoSuchMethodException | SecurityException e) {
            throw new BindException(e);
        } catch (InvocationTargetException e) {
            throw new BindException(e.getTargetException());
        }
    }

    private InputSource newInputSource(String xml) {
        return new InputSource(new StringReader(xml));
    }

}
