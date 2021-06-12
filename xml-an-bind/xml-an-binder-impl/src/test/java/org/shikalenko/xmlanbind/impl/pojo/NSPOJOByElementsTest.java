package org.shikalenko.xmlanbind.impl.pojo;

public class NSPOJOByElementsTest extends APOJOByElementsTst {
    
    @Override
    protected String updateXml(String xml) {
        return addNamespace(xml);
    }
    
}
