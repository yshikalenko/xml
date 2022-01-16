package org.mydomain.example.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mydomain.example.abs.Person;
import org.mydomain.example.abs.Sex;
import org.mydomain.example.abs.Team;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TeamFactoryImplTest {
    
    TeamFactoryImpl teamFactory;

    @Before
    public void setUp() throws Exception {
        teamFactory = new TeamFactoryImpl();
    }

    @Test
    public void givenXMLWithDataInElementsWhenXMLIsBoundEntityContainsCorrectData() throws Exception {
        String xml = "<team>\r\n"
                + "    <project-id>xml-an-bind</project-id>\r\n"
                + "    <person>\r\n"
                + "        <id>3463463</id>\r\n"
                + "        <names>\r\n"
                + "            <firstname>John</firstname>\r\n"
                + "            <lastname>Plenkovich</lastname>\r\n"
                + "        </names>\r\n"
                + "        <physical>\r\n"
                + "            <sex>male</sex>\r\n"
                + "            <age>42</age>\r\n"
                + "        </physical>\r\n"
                + "    </person>\r\n"
                + "    <person>\r\n"
                + "        <id>3456454</id>\r\n"
                + "        <names>\r\n"
                + "            <firstname>Eliza</firstname>\r\n"
                + "            <lastname>Bowl</lastname>\r\n"
                + "        </names>\r\n"
                + "        <physical>\r\n"
                + "            <sex>female</sex>\r\n"
                + "            <age>35</age>\r\n"
                + "        </physical>\r\n"
                + "    </person>\r\n"
                + "    <person>\r\n"
                + "        <id>3456465</id>\r\n"
                + "        <names>\r\n"
                + "            <firstname>John</firstname>\r\n"
                + "            <lastname>Robbin</lastname>\r\n"
                + "        </names>\r\n"
                + "        <physical>\r\n"
                + "            <sex>male</sex>\r\n"
                + "            <age>28</age>\r\n"
                + "        </physical>\r\n"
                + "    </person>\r\n"
                + "</team>";
        Team team = teamFactory.newTeam(toNode(xml));
        assertNotNull(team);
        assertEquals("xml-an-bind", team.getProjectId());
        Collection<Person> persons = team.getPersons();
        assertEquals(3, persons.size());
        Iterator<Person> iterator = persons.iterator();
        assertPerson("3463463", "John", "Plenkovich", Sex.male, 42, iterator.next());
        assertPerson("3456454", "Eliza", "Bowl", Sex.female, 35, iterator.next());
        assertPerson("3456465", "John", "Robbin", Sex.male, 28, iterator.next());
    }

    @Test
    public void givenXMLWithDataInAttributesWhenXMLIsBoundEntityContainsCorrectData() throws Exception {
        String xml = "<team project-id=\"xml-an-bind\">\r\n"
                + "    <person id=\"3463463\">\r\n"
                + "        <names firstname=\"John\" lastname=\"Plenkovich\"/>\r\n"
                + "        <physical sex=\"male\" age=\"42\"/>\r\n"
                + "    </person>\r\n"
                + "    <person id=\"3456454\">\r\n"
                + "        <names firstname=\"Eliza\" lastname=\"Bowl\"/>\r\n"
                + "        <physical sex=\"female\" age=\"35\"/>\r\n"
                + "    </person>\r\n"
                + "    <person id=\"3456465\">\r\n"
                + "        <names firstname=\"John\" lastname=\"Robbin\"/>\r\n"
                + "        <physical sex=\"male\" age=\"28\"/>\r\n"
                + "    </person>\r\n"
                + "</team>";
        Team team = teamFactory.newTeam(toNode(xml));
        assertNotNull(team);
        assertEquals("xml-an-bind", team.getProjectId());
        Collection<Person> persons = team.getPersons();
        assertEquals(3, persons.size());
        Iterator<Person> iterator = persons.iterator();
        assertPerson("3463463", "John", "Plenkovich", Sex.male, 42, iterator.next());
        assertPerson("3456454", "Eliza", "Bowl", Sex.female, 35, iterator.next());
        assertPerson("3456465", "John", "Robbin", Sex.male, 28, iterator.next());
    }
    
    private void assertPerson(String id, String firstname, String lastname, Sex sex, int age, Person person) {
            assertEquals(id, person.getId());
            assertEquals(firstname, person.getFirstname());
            assertEquals(lastname, person.getLastname());
            assertEquals(sex, person.getSex());
            assertEquals(age, person.getAge());
    }

    protected Element toNode(String xml) throws ParserConfigurationException, SAXException, IOException  {
        InputSource inputSource = toInputSource(xml);
        return toNode(inputSource, true);
    }

    protected Element toNode(InputSource inputSource, boolean namespaceAware) throws ParserConfigurationException, SAXException, IOException  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (namespaceAware) {
            factory.setNamespaceAware(true);
        }
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(inputSource);
        return getFirstElement(document);
    }
    
    protected InputSource toInputSource(String xml) {
        Reader reader = new StringReader(xml);
        return new InputSource(reader);
    }
    
    protected Element getFirstElement(Document document) {
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                return (Element)node;
            }
        }
        return null;
    }
}
