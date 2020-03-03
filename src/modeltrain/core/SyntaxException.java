package modeltrain.core;

/**
 * Syntax exception that will be thrown whenever an input dosen't match the expected input
 * @author Florian Heck
 * @version 1.0
 */
public class SyntaxException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Standard exception constructor
     */
    public SyntaxException() {
        super();
    }
    
    /**
     * Exception constructor with detail message
     * @param s The detail message
     */
    public SyntaxException(String s) {
        super(s);
    }
}
