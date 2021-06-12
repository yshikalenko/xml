package org.shikalenko.xmlanbind.impl.initializers;

import java.util.Arrays;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import org.w3c.dom.Node;

public class XPATHNSContext implements NamespaceContext {
    
    private final String prefix; 
    private final String namespace; 


    public XPATHNSContext(String prefix, String namespaceURI) {
        this.prefix = prefix;
        this.namespace = namespaceURI;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (this.prefix.equals(prefix)) {
            return namespace;
        }
        if (XMLConstants.XML_NS_PREFIX.equals(prefix)) {
            return XMLConstants.XML_NS_URI;
        }
        if (XMLConstants.XMLNS_ATTRIBUTE.equals(prefix)) {
            return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
        }
        return XMLConstants.NULL_NS_URI;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        if (namespace.equals(namespaceURI)) {
            return prefix;
        }
        if (XMLConstants.XML_NS_URI.equals(namespaceURI)) {
            return XMLConstants.XML_NS_PREFIX; 
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        return Arrays.asList(getPrefix(namespaceURI)).iterator();
    }

    public static String getPrefix(Node node) {
        String nodeName = node.getNodeName();
        int colon = nodeName.indexOf(':');
        return colon < 0 ? XMLConstants.DEFAULT_NS_PREFIX : nodeName.substring(0, colon);
    }

}
