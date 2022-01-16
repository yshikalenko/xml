package org.mydomain.example.abs;

import org.w3c.dom.Node;

public interface AbstractEntitiesFactory {
    Team newTeam(Node node) throws FactoryException;
}
