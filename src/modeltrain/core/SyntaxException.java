package modeltrain.core;

public class SyntaxException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public SyntaxException() {
        super();
    }
    
    public SyntaxException(String s) {
        super(s);
    }
}
