package org.shikalenko.xmlanbind.impl.pojo;

public class NSPrefixedPOJOByAttributesTest extends APOJOByAttributesTst {
    @Override
    protected String updateXml(String xml) {
        return addPrefixedNamespace(xml);
    }
}
