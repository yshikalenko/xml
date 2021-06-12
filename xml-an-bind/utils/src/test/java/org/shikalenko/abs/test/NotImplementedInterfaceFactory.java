package org.shikalenko.abs.test;

import org.shikalenko.abs.Factory;
import org.shikalenko.abs.FactoryException;

public class NotImplementedInterfaceFactory extends Factory {

    private static NotImplementedInterfaceFactory instance = new NotImplementedInterfaceFactory();

    public static NotImplementedInterfaceFactory getInstance() {
        return instance;
    }

    public NotImplementedInterface createInterface() throws FactoryException {
        return instantiate(NotImplementedInterface.class);
    }


}
