package org.shikalenko.abs.test;

import org.shikalenko.abs.Factory;
import org.shikalenko.abs.FactoryException;

public class SomeInterfaceFactory extends Factory {

    private static SomeInterfaceFactory instance = new SomeInterfaceFactory();

    public static SomeInterfaceFactory getInstance() {
        return instance;
    }

    public SomeInterface createInterface() throws FactoryException {
        return instantiate(SomeInterface.class);
    }


}
