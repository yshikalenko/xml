package org.mydomain.example.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.mydomain.example.abs.Person;
import org.mydomain.example.abs.Team;
import org.shikalenko.xmlanbind.annotations.XPath;

public class TeamImpl implements Team {
    private String projectId;
    private Collection<Person> persons = new LinkedList<Person>();
    @Override
    public String getProjectId() {
        return projectId;
    }
    @XPath("@project-id|project-id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    @Override
    public Collection<Person> getPersons() {
        return persons;
    }
    @XPath(type = PersonImpl.class, value = "person")    
    public void setPersons(Collection<Person> persons) {
        this.persons.addAll(persons);
    }
}
