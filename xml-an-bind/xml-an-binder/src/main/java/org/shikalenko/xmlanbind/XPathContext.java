package org.shikalenko.xmlanbind;

import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public abstract class XPathContext {
    
    protected Map<String, String> namespaceMapping = new HashMap<>(4);
    
    protected XPathContext() {
        namespaceMapping.put(XMLConstants.XML_NS_PREFIX, XMLConstants.XML_NS_URI);
        namespaceMapping.put(XMLConstants.XMLNS_ATTRIBUTE, XMLConstants.XMLNS_ATTRIBUTE_NS_URI);
    }

    public void addNamespaceMapping(String prefix, String namespaceUrl) {
        if (prefix == null) {
            throw new NullPointerException("prefix");
        }
        if (prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is empty");
        }
        if (namespaceUrl == null) {
            throw new NullPointerException("namespaceUrl");
        }
        if (namespaceUrl.trim().length() == 0) {
            throw new IllegalArgumentException("namespaceUrl is empty");
        }
        namespaceMapping.put(prefix, namespaceUrl);
    }

    public abstract NamespaceContext getNamespaceContext();

}
