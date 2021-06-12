package org.shikalenko.xmlanbind;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.shikalenko.abs.Factory;
import org.shikalenko.abs.FactoryException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class XMLAnnotationBinder {

    static class XMLAnnotationBinderFactory extends Factory {

        public XMLAnnotationBinder create() throws FactoryException {
            return instantiate(XMLAnnotationBinder.class);
        }

    }

    public static final int FEATURE_XPATH = 1;
    
    private static XMLAnnotationBinderFactory factory = new XMLAnnotationBinderFactory();

    public static XMLAnnotationBinder newInstance() throws FactoryException {
        return factory.create();
    }

    private int features;

    public abstract <T> T bind(Class<T> clazz, String xml) throws BindException;

    public abstract <T> T bind(Class<T> clazz, InputSource inputSource) throws BindException;
    
    public abstract <T> T bind(Class<T> clazz, Source source) throws BindException;
    
    public abstract <T> T bind(Class<T> clazz, Node node) throws BindException;

    public abstract <T> void bind(T object, String xml) throws BindException;

    public abstract <T> void bind(T object, InputSource inputSource) throws BindException;
    
    public abstract <T> void bind(T object, Source source) throws BindException;
    
    public abstract <T> void bind(T object, Node node) throws BindException;
    
    public void setFeature(int feature) {
        this.features |= feature;
        
    }
    
    public boolean hasFeature(int feature) {
        return (this.features & feature) == feature; 
    }

    protected int getFeatures() {
        return this.features; 
    }
    
    public Element toNode(String xml) throws BindException {
        Reader reader = new StringReader(xml);
        InputSource inputSource = new InputSource(reader);
        return toNode(inputSource);
    }

    public Node toNode(Source xml) throws BindException {
        if (xml instanceof DOMSource) {
            return ((DOMSource) xml).getNode();
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (needNSSupport()) {
            factory.setNamespaceAware(true);
        }
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new BindException(e);
        }
        Document document = documentBuilder.newDocument();
        DOMResult domResult = new DOMResult(document);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(xml, domResult);
        } catch (TransformerException e) {
            throw new BindException(e);
        }
        return getFirstElement(document);
    }
    
    public Element toNode(InputSource inputSource) throws BindException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (needNSSupport()) {
            factory.setNamespaceAware(true);
        }
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new BindException(e);
        }
        Document document;
        try {
            document = documentBuilder.parse(inputSource);
        } catch (SAXException | IOException e) {
            throw new BindException(e);
        }
        return getFirstElement(document);
    }
    
    protected abstract boolean needNSSupport();

    public static Element getFirstElement(Document document) {
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                return (Element)node;
            }
        }
        return null;
    }

    public abstract XPathContext getXPathContext();

    public abstract void cleanXPathContext();
    
}
