package org.shikalenko.xmlanbind.impl.initializers;

import static org.shikalenko.xmlanbind.XMLAnnotationBinder.FEATURE_XPATH;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.shikalenko.xmlanbind.BindException;
import org.shikalenko.xmlanbind.DomInitializer;
import org.shikalenko.xmlanbind.XPathContext;
import org.shikalenko.xmlanbind.annotations.XPath;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomInitializerImp extends DomInitializer {

    private static final Map<Class<?>, QName> PRIMITIVES;
    private static final Pattern QNAME_REGEX = Pattern.compile("#(NUMBER|STRING|BOOLEAN)");
    static {
        Map<Class<?>, QName> m = new HashMap<>();
        PRIMITIVES = m;
        m.put(byte.class, XPathConstants.NUMBER);
        m.put(short.class, XPathConstants.NUMBER);
        m.put(int.class, XPathConstants.NUMBER);
        m.put(long.class, XPathConstants.NUMBER);
        m.put(float.class, XPathConstants.NUMBER);
        m.put(double.class, XPathConstants.NUMBER);
        m.put(boolean.class, XPathConstants.BOOLEAN);
    }
    @Override
    public void initObject(Node node, Object object) throws BindException {
        initObject_(node, object);
    }
    
    private XPathContext xpathContext;
    public void setXPathContext(XPathContext xpathContext) {
        this.xpathContext = xpathContext;
    }
    
    private void initObject_(Node node, Object object) throws BindException {
        initByXPath(node, object);
        initByAttributes(node, object);
        initByChildNodes(node, object);
        
    }
    private void initByXPath(Node node, Object object) throws BindException {
        Collection<Method> methods = ReflectionHelper.findXPathMethod(object.getClass());
        if (!methods.isEmpty()) {
            if (!hasFeature(FEATURE_XPATH)) {
                throw new BindException("Class " + object.getClass().getName() + " uses annotation @XPath. Call setFeature(XMLAnnotationBinder.FEATURE_XPATH) before using XMLAnnotationBinder instance");
            }
            for (Method method : methods) {
                invoke(object, method, node);
            }
        }
    }
    private void invoke(Object object, Method method, Node node) throws BindException {
        XPath xpathAnnotation = method.getAnnotation(XPath.class);
        assert xpathAnnotation != null;
        if (xpathAnnotation != null) {
            String xpathExp = xpathAnnotation.value();                
            javax.xml.xpath.XPath xpath = XPathFactory.newInstance().newXPath();
            if (xpathContext != null) {
                xpath.setNamespaceContext(xpathContext.getNamespaceContext());
            }
            Class<?> paramType = method.getParameterTypes()[0];
            Object value = null;
            try {
                XPathExpression compiledXPath = xpath.compile(xpathExp);
                QName xpathReturnType = XPathConstants.NODESET;
                NodeList nodeSet = null;
                try {
                    nodeSet = (NodeList) compiledXPath.evaluate(node, XPathConstants.NODESET);
                } catch (XPathExpressionException e) {
                    xpathReturnType = getQName(e);
                    if (xpathReturnType == null) {
                        xpathReturnType = getXPathReturnType(paramType);
                    }
                }
                
                if (xpathReturnType == null) {
                    throw new BindException("@XPath annotation is not supported for method " + method + ". Unsupported parameter type: " + paramType);
                }
                if (xpathReturnType == XPathConstants.NODESET) {
                    if (nodeSet != null) {
                        value = initByNodeSet(nodeSet, object, method, paramType, xpathAnnotation);
                    }
                } else {
                    value = compiledXPath.evaluate(node, xpathReturnType);
                }
                invoke(object, method, paramType, value);
            } catch (Throwable e) {
                throw BindException.wrap(e);
            }
        }
    }
    private void invoke(Object object, Method method, Class<?> paramType, Object value) throws Throwable {
        if (value != null) {
            try {
                if (value instanceof Number) {
                    Number nvalue = (Number)value;
                    if (PRIMITIVES.containsKey(paramType)) {
                        if (byte.class == paramType) {
                            method.invoke(object, nvalue.byteValue());
                        } else if (short.class == paramType) {
                            method.invoke(object, nvalue.shortValue());
                        } else if (int.class == paramType) {
                            method.invoke(object, nvalue.intValue());
                        } else if (long.class == paramType) {
                            method.invoke(object, nvalue.longValue());
                        } else if (float.class == paramType) {
                            method.invoke(object, nvalue.floatValue());
                        } else if (double.class == paramType) {
                            method.invoke(object, nvalue.doubleValue());
                        } else {
                            assert false: "Primitive value is instance of " + value.getClass() + " but param type is " + paramType;
                        }
                    }
                } else if (value instanceof Boolean) {
                    Boolean bvalue = (boolean)value;
                    if (PRIMITIVES.containsKey(paramType)) {
                        if (boolean.class == paramType) {
                            method.invoke(object, bvalue.booleanValue());
                        } else {
                            assert false: "Primitive value is instance of " + value.getClass() + " but param type is " + paramType;
                        }
                    }
                } else if (paramType.isAssignableFrom(value.getClass())){
                    method.invoke(object, value);
                } else if (value instanceof String) {
                    Method valueOf = ReflectionHelper.findValueOfMethod(paramType);
                    if (valueOf != null) {
                        value = valueOf.invoke(null, value);
                    }
                    method.invoke(object, value);
                } else {
                    assert false: "Value is instance of " + value.getClass() + " but param type is " + paramType;
                }
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
    private QName getQName(XPathExpressionException e) {
        QName result = null;
        Matcher m = QNAME_REGEX.matcher(e.getMessage());
        if (m.find()) {
            String type = m.group(1);
            if ((result = XPathConstants.STRING).getLocalPart().equals(type));
            else if ((result = XPathConstants.NUMBER).getLocalPart().equals(type));
            else if ((result = XPathConstants.BOOLEAN).getLocalPart().equals(type));
            else  result = null;
        }
        return result;
    }
    
    private QName getXPathReturnType(Class<?> paramType) {
        QName result = XPathConstants.NODESET;
        if (paramType.isPrimitive()){
            result = PRIMITIVES.get(paramType);
        } else if (paramType.isAssignableFrom(Boolean.class)) {
            result = XPathConstants.BOOLEAN;
        } else if (paramType.isAssignableFrom(Number.class)) {
            result = XPathConstants.NUMBER;
        } else if (paramType.isAssignableFrom(String.class)) {
            result = XPathConstants.STRING;
        } else {
            Method valueOf = ReflectionHelper.findValueOfMethod(paramType);
            if (valueOf != null) {
                result = XPathConstants.STRING;
            }
        }
        return result;
    }
    private Object initByNodeSet(NodeList nodeSet, Object object, Method method, Class<?> paramType, XPath xpathAnnotation) throws InstantiationException, IllegalAccessException, InvocationTargetException, BindException {
        if (paramType.isAssignableFrom(NodeList.class)) {
            return nodeSet;
        }
        int l = nodeSet.getLength();
        if (paramType.isAssignableFrom(Collection.class)) {
            Collection<Object> param; 
            if (paramType.isAssignableFrom(List.class)) {
                param = new LinkedList<>();
            } else {
                param = new LinkedHashSet<>();
            }
            Class<?> paramClass = xpathAnnotation.type();
            if (XPath.None.class == paramClass) {
                for (int i = 0; i < l; i++) {
                    param.add(nodeSet.item(i));
                }
            } else {
                Method valueOf = ReflectionHelper.findValueOfMethod(paramClass);
                if (valueOf != null) {
                    for (int i = 0; i < l; i++) {
                        param.add(valueOf.invoke(null, getNodeValue(nodeSet.item(i))));
                    }
                } else {
                    for (int i = 0; i < l; i++) {
                        Object value = ReflectionHelper.istantiateByDefault(paramClass);
                        if (value != null) {
                            param.add(value);
                            initObject_(nodeSet.item(i), value);
                        }
                    }
                }
            } 
            return param;
        }
        for (int i = 0; i < l; i++) {
            Node node = nodeSet.item(i);
            Object value = valueOf(node, paramType);
            if (value != null) {
                method.invoke(object, value);
            } else {
                value = ReflectionHelper.istantiateByDefault(paramType);
                if (value != null) {
                    initObject_(node, value);
                    method.invoke(object, value);
                }
            }
        }
        return null;
    }
    private void initByChildNodes(Node node, Object object) throws BindException {        
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            String name = child.getLocalName();
            if (name == null) {
                name = child.getNodeName();
                if (name == null) {
                    throw new BindException("Node has no names: " + node);
                }
                name = toLocalName(name);
            }
            Collection<Method> methods = ReflectionHelper.findElementSetMethod(object.getClass(), name);
            for (Method method : methods) {
                Class<?> paramType = method.getParameterTypes()[0];
                try {
                    try {
                        Object value = valueOf(child, paramType);
                        if (value != null) {
                            method.invoke(object, value);
                            break;
                        }
                        value = ReflectionHelper.istantiateByDefault(paramType);
                        if (value != null) {
                            method.invoke(object, value);
                            initObject_(child, value);
                            break;
                        }
                    } catch (InvocationTargetException e) {
                        throw e.getTargetException();
                    }
                } catch (Throwable e) {
                    throw BindException.wrap(e);
                }
            }
        }
    }
    private String toLocalName(String name) {
        int pos = name.indexOf(':');
        return pos < 0 ? name : name.substring(pos + 1);
    }

    private Object valueOf(Node child, Class<?> paramType) throws IllegalAccessException, InvocationTargetException {
        Object value = null;
        if (paramType.isAssignableFrom(String.class)) {
            value =  getNodeValue(child);
        } else {
            Method valueOf = ReflectionHelper.findValueOfMethod(paramType);
            if (valueOf != null) {
                value = valueOf.invoke(null, getNodeValue(child));
            }
        }
        return value;
    }
    private String getNodeValue(Node child) {
        return Node.ELEMENT_NODE == child.getNodeType() ? ((Element)child).getTextContent() : child.getNodeValue();
    }
    private void initByAttributes(Node node, Object object) throws BindException {    
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            for (int i = 0, l = attributes.getLength(); i < l; i++) {
                Node attribute = attributes.item(i);
                String name = attribute.getNodeName();
                Collection<Method> methods = ReflectionHelper.findAttributeSetMethod(object.getClass(), name);
                for (Method method : methods) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    try {
                        try {
                            Object value = valueOf(attribute, paramType);
                            if (value != null) {
                                method.invoke(object, value);
                                break;
                            }
                        } catch (InvocationTargetException e) {
                            throw e.getTargetException();
                        }
                    } catch (Throwable e) {
                        throw BindException.wrap(e);
                    }
                }
            }
        }
    }

}
