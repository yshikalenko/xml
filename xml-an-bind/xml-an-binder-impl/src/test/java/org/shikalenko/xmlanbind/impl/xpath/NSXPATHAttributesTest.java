package org.shikalenko.xmlanbind.impl.xpath;

import org.shikalenko.xmlanbind.annotations.XPath;
import org.shikalenko.xmlanbind.impl.pojo.Sex;

public class NSXPATHAttributesTest extends AXPATHAttributesTst {

    public static class TeamObject extends AXPATHAttributesTst.TeamObject {
        @XPath("ns:person")
        public void addPerson(PersonObject person) {
            super.addPerson(person);
        }
    }

    public static class PersonObject extends AXPATHAttributesTst.PersonObject {
        @XPath("ns:names/@firstname")
        public void setFirstname(String firstname) {
            super.setFirstname(firstname);
        }
        @XPath("ns:names/@lastname")
        public void setLastname(String lastname) {
            super.setLastname(lastname);
        }
        @XPath("ns:physical/@sex")
        public void setSex(Sex sex) {
            super.setSex(sex);
        }
        @XPath("ns:physical/@age")
        public void setAge(short age) {
            super.setAge(age);
        }    
        
    }
    
    @Override
    protected String updateXml(String xml) {
        return addNamespace(xml);
    }
    
    @Override
    protected Class<? extends TeamObject> getTeamObjectClass() {
        binder.getXPathContext().addNamespaceMapping("ns", TEST_NS);
        return TeamObject.class;
    }
    

}
