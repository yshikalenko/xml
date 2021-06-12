package org.shikalenko.xmlanbind.impl.pojo;

public class NSPrefixedPOJOByElementsTest extends APOJOByElementsTst {
    
    @Override
    protected String updateXml(String xml) {
        return addPrefixedNamespace(xml);
    }
    
}
