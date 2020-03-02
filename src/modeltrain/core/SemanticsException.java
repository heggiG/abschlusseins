package modeltrain.core;

/**
 * An exception that when thrown indicates that an internal semantical error has
 * occured, like when trying to switch to a point that isn't part of the
 * switchtrack
 * 
 * @author Florian Heck
 * @version 1
 */
public class SemanticsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Standard exception constructor
     */
    public SemanticsException() {
        super();
    }

    /**
     * Exception constructor with detail message
     * @param s The detail message
     */
    public SemanticsException(String s) {
        super(s);
    }

}
