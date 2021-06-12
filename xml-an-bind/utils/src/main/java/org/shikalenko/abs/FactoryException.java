package org.shikalenko.abs;

public class FactoryException extends Exception {
    private static final long serialVersionUID = 495961002269946614L;

    public FactoryException() {
        super();
    }

    public FactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FactoryException(String message) {
        super(message);
    }

    public FactoryException(Throwable cause) {
        super(cause);
    }

}
