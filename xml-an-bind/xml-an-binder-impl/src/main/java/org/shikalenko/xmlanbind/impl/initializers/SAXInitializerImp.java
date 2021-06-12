package org.shikalenko.xmlanbind.impl.initializers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.shikalenko.xmlanbind.BindException;
import org.shikalenko.xmlanbind.SAXInitializer;
import org.w3c.dom.DOMException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SAXInitializerImp extends SAXInitializer {


    private static class SAXBindException extends SAXException {
        private static final long serialVersionUID = 5320173047705695133L;

        public SAXBindException(Exception e) {
            super(e);
        }

    }




    private static class Handler extends DefaultHandler {

        private static class State {
            private Object parent;
            private Object object;
            private int stringStart;
            public Method method;
            public Method valueOf;
            public State() {
            }
        }
        
        LinkedList<State> stack = new LinkedList<>();
        private StringBuilder text = new StringBuilder();

        public Handler(Object object) {
            State state = new State();
            state.object = object;
            stack.push(state);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            State state = stack.peek();
            if (state.parent != null) {
                parseChildStart(state, uri, localName, qName);
            }
            if (state.object != null) {
                parseAttributes(state, uri, localName, qName, attributes);
            }
            super.startElement(uri, localName, qName, attributes);
            State newState = new State();
            newState.parent = state.object;
            newState.stringStart = text.length();
            stack.push(newState);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            text.append(ch, start, length);
            super.characters(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            stack.pop();
            State state = stack.peek();
            if (state.parent != null) {
                parseChildEnd(uri, localName, qName, state);
            }
            text.setLength(state.stringStart);
            super.endElement(uri, localName, qName);
        }

        private void parseChildStart(State state, String uri, String localName, String qName) throws SAXBindException {
            Collection<Method> methods = ReflectionHelper.findElementSetMethod(state.parent.getClass(), getName(localName, qName));
            for (Method method : methods) {
                Class<?> paramType = method.getParameterTypes()[0];
                try {
                    Object value = null;
                    if (paramType.isAssignableFrom(String.class)) {
                        state.method = method;
                        break;
                    }
                    Method valueOf = ReflectionHelper.findValueOfMethod(paramType);
                    if (valueOf != null) {
                        state.method = method;
                        state.valueOf = valueOf;
                        break;
                    }
                    value = ReflectionHelper.istantiateByDefault(paramType);
                    if (value != null) {
                        method.invoke(state.parent, value);
                        state.object = value;
                        break;
                    }
                } catch (IllegalAccessException | DOMException |  InvocationTargetException | InstantiationException e) {
                    throw new SAXBindException(e);
                }
            }
            
        }
        
        private String getName(String localName, String qName) {
            return localName == null || localName.length() == 0 ? qName : localName;
        }

        private void parseChildEnd(String uri, String localName, String qName, State state) throws SAXBindException {
            if (state.method != null) {
                try {
                    String string = text.substring(state.stringStart).trim();
                    Object value = string;
                    if (state.valueOf != null) {
                        value = state.valueOf.invoke(null, string);
                        state.valueOf = null;
                    }
                    state.method.invoke(state.parent, value);
                    state.method = null;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new SAXBindException(e);
                }
            }
        }

        private void parseAttributes(State state, String uri, String localName, String qName, Attributes attributes) throws SAXBindException {
            for (int i = 0, l = attributes.getLength(); i < l; i++) {
                String name = attributes.getLocalName(i);
                Collection<Method> methods = ReflectionHelper.findAttributeSetMethod(state.object.getClass(), name);
                for (Method method : methods) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    try {
                        String attrValue = attributes.getValue(i);
                        Object value = null;
                        if (paramType.isAssignableFrom(String.class)) {
                            value =  attrValue;
                            method.invoke(state.object, value);
                            break;
                        }
                        Method valueOf = ReflectionHelper.findValueOfMethod(paramType);
                        if (valueOf != null) {
                            value = valueOf.invoke(null, attrValue);
                            method.invoke(state.object, value);
                            break;
                        }
                    } catch (IllegalAccessException | DOMException |  InvocationTargetException e) {
                        throw new SAXBindException(e);
                    }
                }
            }
        }
        
    
    }

    @Override
    public void initObject(Source source, Object object) throws BindException {
        if (source instanceof SAXSource) {
            SAXSource saxSource = (SAXSource) source;
            initObject(saxSource.getXMLReader(), saxSource.getInputSource(), object);
        } else if (source instanceof StreamSource) {
            StreamSource streamSource = (StreamSource) source;
            InputSource inputSource = new InputSource(streamSource.getSystemId());
            inputSource.setByteStream(streamSource.getInputStream());
            inputSource.setCharacterStream(streamSource.getReader());
            initObject(inputSource, object);
        } else {
            InputSource inputSource = new InputSource(source.getSystemId());
            initObject(inputSource, object);
        }
    }
    
    @Override
    public void initObject(InputSource inputSource, Object object) throws BindException {
        XMLReader reader;
        try {
            reader = newXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            throw new BindException(e);
        }
        initObject(reader, inputSource, object);
    }
    
    private void initObject(XMLReader reader, InputSource inputSource, Object object) throws BindException {
        reader.setContentHandler(new Handler(object));
        try {
            try {
                try {
                    reader.parse(inputSource);
                } catch (SAXBindException e) {
                    throw e.getCause();
                }
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        } catch (Throwable e) {
            throw BindException.wrap(e); 
        }
    }
        
    private XMLReader newXMLReader() throws SAXException, ParserConfigurationException {
        SAXParserFactory parser = SAXParserFactory.newInstance();
        parser.setNamespaceAware(true);
        return parser.newSAXParser().getXMLReader();
    }

}
