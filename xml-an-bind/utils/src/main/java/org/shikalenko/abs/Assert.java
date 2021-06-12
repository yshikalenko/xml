package org.shikalenko.abs;

public abstract class Assert {

    public static void assertNotNull(Object value, String format, Object... params) {
        assert format != null; 
        if (value == null) {
            throw new NullPointerException(String.format(format, params));
        }
    }

    public static void assertNotEmpty(Object value, String format, Object... params) {
        assert format != null; 
        if (value == null) {
            throw new NullPointerException(String.format(format, params));
        }
        if (value.toString().length() == 0) {
            throw new IllegalArgumentException(String.format(format, params));
        }
    }
}
