package modeltrain.core;

/**
 * A generic Tuple of type E
 * 
 * @author Florian
 * @version 1.0
 * @param <E> the first elements generic type
 * @param <F> the second elements generic type
 */
public class Tuple<E, F> {
    
    private final E elementOne;
    private final F elementTwo;
    
    /**
     * Creates the two element Tuple
     * 
     * @param one The tuple's first element
     * @param two The tuple's second element
     */
    public Tuple(E one, F two) {
        elementOne = one;
        elementTwo = two;
    }
    
    /**
     * Returns the tuple's first element
     * @return The first element
     */
    public E getFirst() {
        return elementOne;
    }
    
    /**
     * Returns the tuple's second element
     * @return The second element
     */
    public F getSecond() {
        return elementTwo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(elementOne);
        sb.append(",");
        sb.append(elementTwo);
        sb.append("]");
        return sb.toString();
    }
}
