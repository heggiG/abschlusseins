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
public class Tuple<E> {
    
    private final E elementOne;
    private final E elementTwo;
    
    /**
     * Creates the two element Tuple
     * 
     * @param one The tuple's first element
     * @param two The tuple's second element
     */
    public Tuple(E one, E two) {
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
    public E getSecond() {
        return elementTwo;
    }
    
    /**
     * Returns the tuple as a list
     * @return The tuple as a list
     */
    public List<E> getAsList() {
        List<E> ret = new ArrayList<>();
        ret.add(elementOne);
        ret.add(elementTwo);
        return ret;
    }
    
    /**
     * Returns the tuple as a set
     * @return The tuple as a set
     */
    public Set<E> getAsSet() {
        Set<E> ret = new HashSet<>();
        ret.add(elementOne);
        ret.add(elementTwo);
        return ret;
    }
    
}
