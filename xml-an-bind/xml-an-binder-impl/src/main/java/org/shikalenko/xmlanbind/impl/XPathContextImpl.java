package org.shikalenko.xmlanbind.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import org.shikalenko.xmlanbind.XPathContext;

public class XPathContextImpl extends XPathContext implements NamespaceContext {

    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix is null");
        }
        String namespaceURI = namespaceMapping.get(prefix);
        return namespaceURI == null ? XMLConstants.NULL_NS_URI : namespaceURI;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("namespaceURI is null");
        }
        for (Entry<String, String> entry : namespaceMapping.entrySet()) {
            if (entry.getValue().equals(namespaceURI)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("namespaceURI is null");
        }
        List<String> list = new LinkedList<>();
        for (Entry<String, String> entry : namespaceMapping.entrySet()) {
            if (entry.getValue().equals(namespaceURI)) {
                list.add(entry.getKey());
            }
        }
        return Arrays.asList(list.toArray(new String[list.size()])).iterator();
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this;
    }
    
}
