package org.shikalenko.xmlanbind.impl.xpath;

public class NSXPrefixedPATHElementsTest extends NSXPATHElementsTest {
    @Override
    protected String updateXml(String xml) {
        return addPrefixedNamespace(xml);
    }
}
