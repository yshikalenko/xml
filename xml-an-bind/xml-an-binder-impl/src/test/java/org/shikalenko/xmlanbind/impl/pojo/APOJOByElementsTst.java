package org.shikalenko.xmlanbind.impl.pojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
import org.shikalenko.xmlanbind.impl.TstBase;

public abstract class APOJOByElementsTst extends TstBase {

    @Test
    public void testOption() throws Exception {
        String xml = "<option><value>diversification_of_investments</value><msg-id>UI.EAF.ACCOUNT_PURPOSE.EXPECTED_USE.OPTION.DIVERSIFICATION_OF_INVESTMENTS</msg-id></option>";
        xml = updateXml(xml);
        OptionObject object = binder.bind(OptionObject.class, xml);
        checkObject(object, xml, OptionObject.class, optionObject -> {
            assertNotNull(optionObject);
            assertEquals("diversification_of_investments", optionObject.getValue());
            assertEquals("UI.EAF.ACCOUNT_PURPOSE.EXPECTED_USE.OPTION.DIVERSIFICATION_OF_INVESTMENTS", optionObject.getMsgId());
        });
    }

    @Test
    public void testHuman() throws Exception {
        String xml = "<human><age>62</age></human>";
        xml = updateXml(xml);
        HumanObject object = binder.bind(HumanObject.class, xml);
        checkObject(object, xml, HumanObject.class, humanObject -> {
            assertNotNull(humanObject);
            assertEquals(62, humanObject.getAge());
        });
    }
    

    @Test
    public void testPerson() throws Exception {
        String xml = "<person><id>3463463</id><names><firstname>Yuri</firstname><lastname>Shikalenko</lastname></names><physical><sex>male</sex><age>62</age></physical></person>";
        xml = updateXml(xml);
        PersonObject object = binder.bind(PersonObject.class, xml);
        checkObject(object, xml, PersonObject.class, personObject -> {
            assertNotNull(personObject);
            assertEquals("3463463", personObject.getId());
            NamesObject names = personObject.getNames();
            assertNotNull(names);
            assertEquals("Yuri", names.getFirstname());
            assertEquals("Shikalenko", names.getLastname());
            PhysicalStateObject physicalState = personObject.getPhysicalState();
            assertNotNull(physicalState);
            assertEquals(Sex.male, physicalState.getSex());
            assertEquals(62, physicalState.getAge());
        });
    }


    @Test
    public void testTeam() throws Exception {
        String xml = "<team><project-id>xml-an-bind</project-id>";
        xml += "<person><id>3463463</id><names><firstname>Yuri</firstname><lastname>Shikalenko</lastname></names><physical><sex>male</sex><age>62</age></physical></person>";
        xml += "<person><id>3456454</id><names><firstname>Eliza</firstname><lastname>Bowl</lastname></names><physical><sex>female</sex><age>35</age></physical></person>";
        xml += "<person><id>3456465</id><names><firstname>John</firstname><lastname>Robbin</lastname></names><physical><sex>male</sex><age>28</age></physical></person>";
        xml += "</team>";
        xml = updateXml(xml);
        TeamObject object = binder.bind(TeamObject.class, xml);
        checkObject(object, xml, TeamObject.class, teamObject -> {
            assertNotNull(teamObject);
            assertEquals("xml-an-bind", teamObject.getProjectId());
            Collection<PersonObject> persons = teamObject.getPersons();
            assertEquals(3, persons.size());
            Iterator<PersonObject> iterator = persons.iterator();
            {
                PersonObject personObject = iterator.next();
                assertEquals("3463463", personObject.getId());
                NamesObject names = personObject.getNames();
                assertNotNull(names);
                assertEquals("Yuri", names.getFirstname());
                assertEquals("Shikalenko", names.getLastname());
                PhysicalStateObject physicalState = personObject.getPhysicalState();
                assertNotNull(physicalState);
                assertEquals(Sex.male, physicalState.getSex());
                assertEquals(62, physicalState.getAge());
            }
            {
                PersonObject personObject = iterator.next();
                assertEquals("3456454", personObject.getId());
                NamesObject names = personObject.getNames();
                assertNotNull(names);
                assertEquals("Eliza", names.getFirstname());
                assertEquals("Bowl", names.getLastname());
                PhysicalStateObject physicalState = personObject.getPhysicalState();
                assertNotNull(physicalState);
                assertEquals(Sex.female, physicalState.getSex());
                assertEquals(35, physicalState.getAge());
            }
            {
                PersonObject personObject = iterator.next();
                assertEquals("3456465", personObject.getId());
                NamesObject names = personObject.getNames();
                assertNotNull(names);
                assertEquals("John", names.getFirstname());
                assertEquals("Robbin", names.getLastname());
                PhysicalStateObject physicalState = personObject.getPhysicalState();
                assertNotNull(physicalState);
                assertEquals(Sex.male, physicalState.getSex());
                assertEquals(28, physicalState.getAge());
            }
        });
    }
    
}
