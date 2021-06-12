package org.shikalenko.xmlanbind.impl.xpath;


import java.util.Collection;
import org.shikalenko.xmlanbind.annotations.XPath;
import org.shikalenko.xmlanbind.impl.pojo.PhysicalStateObject;
import org.shikalenko.xmlanbind.impl.pojo.Sex;

public class NSXPATHElementsTest extends AXPATHElementsTst {

    public static class OptionObject extends AXPATHElementsTst.OptionObject {
        @XPath("ns:value")
        public void setValue(String value) {
            super.setValue(value);
        }
        @XPath("ns:msg-id")
        public void setMsgId(String msgId) {
            super.setMsgId(msgId);
        }
    }

    public static class HumanObject extends AXPATHElementsTst.HumanObject {
        
        @XPath("ns:age")
        public void setAge(byte age) {
            super.setAge(age);
        }
        
        @XPath("ns:height")
        public void setHeight(short height) {
            super.setHeight(height);
        }
        
        @XPath("ns:year-of-birth")
        public void setYearOfBirth(int yearOfBirth) {
            super.setYearOfBirth(yearOfBirth);
        }

        @XPath("ns:date-of-birth-long")
        public void setDateOfBirth(long dateOfBirth) {
            super.setDateOfBirth(dateOfBirth);
        }

        @XPath("ns:weight-pounds")
        public void setWeightPounds(float weightPounds) {
            super.setWeightPounds(weightPounds);
        }
        
        @XPath("ns:weight-grams")
        public void setWeightGrams(double weightGrams) {
            super.setWeightGrams(weightGrams);
        }
        @XPath("ns:is-alien='true'")
        public void setAlien(boolean alien) {
            super.setAlien(alien);
        }

    }

    public static class PersonObjectToAdd extends AXPATHElementsTst.PersonObjectToAdd {
        
        @XPath("ns:id")
        public void setId(String id) {
            super.setId(id);
        }

        @XPath("ns:physical/ns:sex")
        public void setSex(Sex sex) {
            super.setSex(sex);
        }

        @XPath("ns:physical/ns:age")
        public void setAge(short age) {
            super.setAge(age);
        }
        
        @XPath(value = "ns:names/ns:name")
        public void addName(String name) {
            super.addName(name);
        }
        @XPath(value="ns:children/ns:child")
        public void addChild(PersonObjectToAdd child) {
            super.addChild(child);
        }
    }
    
    public static class PersonObjectToSet extends AXPATHElementsTst.PersonObjectToSet {
        @XPath("ns:id")
        public void setId(String id) {
            super.setId(id);
        }

        @XPath("ns:physical/ns:sex")
        public void setSex(Sex sex) {
            super.setSex(sex);
        }

        @XPath("ns:physical/ns:age")
        public void setAge(short age) {
            super.setAge(age);
        }
        
        @XPath(value = "ns:names/ns:name", type=String.class)
        public void setNames(Collection<String> names) {
            super.setNames(names);
        }
        @XPath(value="ns:children/ns:child", type = PersonObjectToSet.class)
        public void setChildren(Collection<PersonObject> children) {
            super.setChildren(children);
        }
    }
    
    public static class TeamPersonObject extends AXPATHElementsTst.TeamPersonObject {
        @XPath("ns:id")
        public void setId(String id) {
            super.setId(id);
        }
        @XPath(value = "ns:names")
        public void setNames(NamesObject names) {
            super.setNames(names);
        }
        @XPath(value="ns:children/ns:child")
        public void addChild(PersonObjectToAdd child) {
            super.addChild(child);
        }
        @XPath(value="ns:physical")
        public void setPhysicalState(PhysicalStateObject physicalState) {
            super.setPhysicalState(physicalState);
        }
    }
    
    
    public static class TeamObject extends AXPATHElementsTst.TeamObject {
        @XPath("ns:project-id")
        public void setProjectId(String projectId) {
            super.setProjectId(projectId);
        }
        @XPath("ns:person")
        public void addPerson(TeamPersonObject person) {
            super.addPerson(person);
        }
        
    }
    
    
    @Override
    protected String updateXml(String xml) {
        return addNamespace(xml);
    }

    @Override
    protected Class<OptionObject> getOptionObjectClass() {
        binder.getXPathContext().addNamespaceMapping("ns", TEST_NS);
        return OptionObject.class;
    }
    @Override
    protected Class<HumanObject> getHumanObjectClass() {
        binder.getXPathContext().addNamespaceMapping("ns", TEST_NS);
        return HumanObject.class;
    }
    
    @Override
    protected Class<PersonObjectToAdd> getPersonObjectToAddClass() {
        binder.getXPathContext().addNamespaceMapping("ns", TEST_NS);
        return PersonObjectToAdd.class;
    }
    
    
    @Override
    protected Class<PersonObjectToSet> getPersonObjectToSetClass() {
        binder.getXPathContext().addNamespaceMapping("ns", TEST_NS);
        return PersonObjectToSet.class;
    }

    protected Class<TeamObject> getTeamObjectClass() {
        binder.getXPathContext().addNamespaceMapping("ns", TEST_NS);
        return TeamObject.class;
    }
    
}
