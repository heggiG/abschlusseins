package modeltrain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A generic Tuple of type E
 * 
 * @author Florian
 * @version 1.0
 * @param <E> the generic type
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
}
