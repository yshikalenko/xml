package org.shikalenko.xmlanbind.impl.xpath;

public class NSPrefixedXPATHAttributesTest extends NSXPATHAttributesTest {
    @Override
    protected String updateXml(String xml) {
        return addPrefixedNamespace(xml);
    }
}
