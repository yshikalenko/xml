package org.mydomain.example.impl;

import org.mydomain.example.abs.FactoryException;
import org.mydomain.example.abs.Team;
import org.shikalenko.xmlanbind.BindException;
import org.shikalenko.xmlanbind.XMLAnnotationBinder;
import org.w3c.dom.Node;

public class TeamFactoryImpl {
    
    public Team newTeam(Node node) throws FactoryException {
        try {
            XMLAnnotationBinder binder = XMLAnnotationBinder.newInstance();
            binder.setFeature(XMLAnnotationBinder.FEATURE_XPATH);
            return binder.bind(TeamImpl.class, node); 
        } catch (org.shikalenko.abs.FactoryException | BindException e) {
            throw new FactoryException(e);
        }
    }
    
}