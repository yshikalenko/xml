package org.mydomain.example.impl;

import org.mydomain.example.abs.Person;
import org.mydomain.example.abs.Sex;
import org.shikalenko.xmlanbind.annotations.Element;

public class PersonImpl implements Person {
    private String id;
    private NamesImpl names = new NamesImpl();
    private PhysicalStateImpl physicalState = new PhysicalStateImpl();
    @Override
    public String getId() {
        return id;
    }
    @Override
    public String getFirstname() {
        return names.getFirstname();
    }
    @Override
    public String getLastname() {
        return names.getLastname();
    }
    @Override
    public Sex getSex() {
        return physicalState.getSex();
    }
    @Override
    public int getAge() {
        return physicalState.getAge();
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setNames(NamesImpl names) {
        this.names = names;
    }
    @Element("physical")
    public void setPhysicalState(PhysicalStateImpl physicalState) {
        this.physicalState = physicalState;
    }
}
