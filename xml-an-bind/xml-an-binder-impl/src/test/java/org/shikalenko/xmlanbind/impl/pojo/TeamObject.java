package org.shikalenko.xmlanbind.impl.pojo;

import java.util.Collection;
import java.util.LinkedList;

import org.shikalenko.xmlanbind.annotations.Attribute;
import org.shikalenko.xmlanbind.annotations.Element;

public class TeamObject {
    private String projectId;
    private Collection<PersonObject> persons = new LinkedList<>();
    public String getProjectId() {
        return projectId;
    }
    @Attribute("project-id")
    @Element("project-id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public Collection<PersonObject> getPersons() {
        return persons;
    }
    @Element("person")
    public void addPerson(PersonObject person) {
        persons.add(person);
    }
    
}
