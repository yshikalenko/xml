package org.shikalenko.abs;

import static org.junit.Assert.*;

import org.junit.Test;
import org.shikalenko.abs.test.NotImplementedInterfaceFactory;
import org.shikalenko.abs.test.SomeInterface;
import org.shikalenko.abs.test.SomeInterfaceFactory;

public class FactoryTest {

    @Test
    public void test() throws Exception {
        SomeInterface someInterface = SomeInterfaceFactory.getInstance().createInterface();

        assertTrue("someInterface must be instance of " + SomeInterface.class, someInterface instanceof SomeInterface);
    }

    @Test(expected = FactoryException.class)
    public void testNotImplementedInterface() throws Exception {
        NotImplementedInterfaceFactory.getInstance().createInterface();
    }
}
