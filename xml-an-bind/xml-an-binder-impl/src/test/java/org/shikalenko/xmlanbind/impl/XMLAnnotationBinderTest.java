package org.shikalenko.xmlanbind.impl;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.shikalenko.abs.FactoryException;
import org.shikalenko.xmlanbind.XMLAnnotationBinder;

public class XMLAnnotationBinderTest {
    
    private XMLAnnotationBinder binder;
/*    
    private static class L extends LinkedList<String> {
        
    }
    
    private static class S extends LinkedHashSet<String> {
        
    }
    
    @Before
    public void init() {
        List<String> list = new L();
        Set<String> set = new S();
        printGenericInterfaces("GenericInterfaces list of string:", list.getClass());
        System.out.println("------------------------");
        printGenericInterfaces("GenericInterfaces set of string:", set.getClass());
        System.out.println("------------------------");
        printGenericClasses("GenericClasses list of string:", list.getClass());
        System.out.println("------------------------");
        printGenericClasses("GenericClasses set of string:", set.getClass());
    }

    private void printGenericInterfaces(String string, Class<?> c) {
        System.out.println(string);
        Type[] genericInterfaces = c.getGenericInterfaces();
        for (int i = 0; i < genericInterfaces.length; i++) {
            Type genericInterface = genericInterfaces[i];
            System.out.println("#" + i + " genericInterface:" + genericInterface);
            printType(genericInterface);
        }
    }

    private void printGenericClasses(String string, Class<?> c) {
        System.out.println(string);
        Type genericSuperclass = c.getGenericSuperclass();
        System.out.println("genericSuperclass:" + genericSuperclass);
    }
    
    private void printType(Type type) {
        if (type instanceof Class) {
            System.out.println("type is Class");
            System.out.println("class name: " + ((Class) type).getSimpleName());
        } else if (type instanceof ParameterizedType) {
            System.out.println("type is ParameterizedType");
            ParameterizedType ptype = (ParameterizedType)type;
            System.out.println("TypeName: " + ptype.getTypeName());
            System.out.println("OwnerType: " + ptype.getOwnerType());
            System.out.println("RawType: " + ptype.getRawType());
            System.out.println("----");
            Type[] aType = ptype.getActualTypeArguments();
            System.out.println("printing each one of ActualTypeArguments:");
            System.out.println("----");
            for (int i = 0; i < aType.length; i++) {
                Type type1 = aType[i];
                System.out.print("#" + i);
                printType(type1);
            }
            System.out.println("----");
        } else if (type instanceof TypeVariable) {
            System.out.println("type is TypeVariable");
            String typeVariableName = ((TypeVariable) type).getName();
            System.out.println("typeVariableName: " + typeVariableName);

        } else if (type instanceof GenericArrayType) {
            System.out.println("type is GenericArrayType");
            Type genericComponentType =
                    ((GenericArrayType) type).getGenericComponentType();
            printType(genericComponentType);
        }
    }    

    */
    @Test
    public void test() throws FactoryException {
        binder = XMLAnnotationBinder.newInstance();
        assertSame(XMLAnnotationBinderImpl.class, binder.getClass());
    }

}
