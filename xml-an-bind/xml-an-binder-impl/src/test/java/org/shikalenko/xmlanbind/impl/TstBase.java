package org.shikalenko.xmlanbind.impl;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.shikalenko.xmlanbind.BindException;
import org.shikalenko.xmlanbind.XMLAnnotationBinder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public abstract class TstBase {
    
    private static final Pattern TAG_PATTERN = Pattern.compile("<([\\w\\.\\-]+)");
    private static final Pattern ALL_TAG_PATTERN = Pattern.compile("(</?)([\\w\\.\\-]+)");
    protected static final String TEST_NS = "http://xmlanbind.shikalneko.org";

    protected XMLAnnotationBinder binder; 

    protected String updateXml(String xml) {
        return xml;
    }
    
    @Before
    public void setUp() throws Exception {
        binder = new XMLAnnotationBinderImpl();
    }
    
    protected static String addNamespace(String xml) {
        return TAG_PATTERN.matcher(xml).replaceFirst("$0 xmlns=\"" + TEST_NS + "\"");
    }
    
    protected static String addPrefixedNamespace(String xml) {
        String xml1 = TAG_PATTERN.matcher(xml).replaceFirst("$0 xmlns:sxb=\"http://xmlanbind.shikalneko.org\"");
        String xml2 = ALL_TAG_PATTERN.matcher(xml1).replaceAll("$1sxb:$2");
        return xml2;
    }

    protected InputSource toInputSource(String xml) {
        Reader reader = new StringReader(xml);
        return new InputSource(reader);
    }
    protected SAXSource toSaxSource(String xml) throws SAXException, ParserConfigurationException {
        Reader reader = new StringReader(xml);
        InputSource inputSource = new InputSource(reader);
        return new SAXSource(newXMLReader(), inputSource);
    }
    protected StreamSource toStreamSource(String xml) {
        Reader reader = new StringReader(xml);
        return new StreamSource(reader);
    }
    protected DOMSource toDomSource(String xml) throws ParserConfigurationException, SAXException, IOException {
        return new DOMSource(toNode(xml));
    }
    protected Element toNode(String xml) throws ParserConfigurationException, SAXException, IOException  {
        InputSource inputSource = toInputSource(xml);
        return toNode(inputSource, xml.contains(" xmlns=") || xml.contains(" xmlns:"));
    }
    protected Element toNode(InputSource inputSource, boolean namespaceAware) throws ParserConfigurationException, SAXException, IOException  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (namespaceAware) {
            factory.setNamespaceAware(true);
        }
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(inputSource);
        return getFirstElement(document);
    }

    protected Element getFirstElement(Document document) {
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                return (Element)node;
            }
        }
        return null;
    }
    
    protected <T> void checkObject(T object, String xml, Class<? extends T> clazz, Checker<T> checker) 
            throws BindException, ParserConfigurationException, SAXException, IOException {
    for (int i = 0; i < 6; i++) {
        checker.check(object);
        switch (i) {
        case 0:
            object = binder.bind(clazz, toInputSource(xml));
            break;
        case 1:
            object = binder.bind(clazz, toNode(xml));
            break;
        case 2:
            object = binder.bind(clazz, toSaxSource(xml));
            break;
        case 3:
            object = binder.bind(clazz, toStreamSource(xml));
            break;
        case 4:
            object = binder.bind(clazz, toDomSource(xml));
            break;
        }
    }
}
    

    private XMLReader newXMLReader() throws SAXException, ParserConfigurationException {
        SAXParserFactory parser = SAXParserFactory.newInstance();
        parser.setNamespaceAware(true);
        return parser.newSAXParser().getXMLReader();
    }
    
}
