package org.shikalenko.xmlanbind.impl.pojo;

import org.shikalenko.xmlanbind.annotations.Element;

public class PersonObject {
    private String id;
    private NamesObject names;
    private PhysicalStateObject physicalState;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public NamesObject getNames() {
        return names;
    }
    public void setNames(NamesObject names) {
        this.names = names;
    }
    public PhysicalStateObject getPhysicalState() {
        return physicalState;
    }
    @Element("physical")
    public void setPhysicalState(PhysicalStateObject physicalState) {
        this.physicalState = physicalState;
    }
    
}
