package org.shikalenko.xmlanbind.impl.pojo;

public class NSPOJOByAttributesTest extends APOJOByAttributesTst {
    @Override
    protected String updateXml(String xml) {
        return addNamespace(xml);
    }
}
