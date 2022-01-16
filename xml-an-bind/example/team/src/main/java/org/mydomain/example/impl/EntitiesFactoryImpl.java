package org.mydomain.example.impl;

import org.mydomain.example.abs.AbstractEntitiesFactory;
import org.mydomain.example.abs.FactoryException;
import org.mydomain.example.abs.Team;
import org.w3c.dom.Node;

public class EntitiesFactoryImpl implements AbstractEntitiesFactory {

    private TeamFactoryImpl teamFactory = new TeamFactoryImpl();
    
    @Override
    public Team newTeam(Node node) throws FactoryException {
        return teamFactory.newTeam(node);
    }

}
