package ir.co.sadad.restclients.exceptions;

public class UnknownException extends Exception {


    private static final long serialVersionUID = 1L;

    public UnknownException() {
        super();
    }

    public UnknownException(String message) {
        super(message);
    }
}
