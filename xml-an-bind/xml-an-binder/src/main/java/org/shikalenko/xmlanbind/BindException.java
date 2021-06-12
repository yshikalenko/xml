package org.shikalenko.xmlanbind;

public class BindException extends Exception {
    private static final long serialVersionUID = -5150946484675165535L;


    public BindException(String message) {
        super(message);
    }

    public BindException(Throwable cause) {
        super(cause);
    }

    public BindException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BindException wrap(Throwable e) {
        if (e instanceof Error) {
            throw (Error)e;
        }
        if (e instanceof BindException) {
            return (BindException)e;
        }
        return new BindException(e);
    }


}
