package org.shikalenko.xmlanbind;

import org.shikalenko.abs.Factory;
import org.shikalenko.abs.FactoryException;
import org.w3c.dom.Node;

public abstract class DomInitializer {

    static class DomInitializerFactory extends Factory {

        public DomInitializer create() throws FactoryException {
            return instantiate(DomInitializer.class);
        }

    }
    
    private static DomInitializerFactory factory = new DomInitializerFactory();

    public static DomInitializer newInstance() throws FactoryException {
        return factory.create();
    }

    private int features;

    public abstract void initObject(Node node, Object object) throws BindException;

    public void setFeatures(int features) {
        this.features = features;
    }

    public abstract void setXPathContext(XPathContext xpathContext);
    
    protected boolean hasFeature(int feature) {
        return (this.features & feature) == feature; 
    }

    
}
