package org.mydomain.example.abs;

public class FactoryException extends RuntimeException {

    private static final long serialVersionUID = 6660118659819888011L;

    public FactoryException() {
    }

    public FactoryException(String message) {
        super(message);
    }

    public FactoryException(Throwable cause) {
        super(cause);
    }

    public FactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FactoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
