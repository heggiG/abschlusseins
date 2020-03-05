package modeltrain.core;

import java.util.Collection;
import java.util.HashSet;

/**
 * Personal Extension of the java.util HashSet to allow null elements in the
 * addAll method and not throw NullPointerException, needed for my delete track
 * algorithm
 * 
 * @author Florian Heck
 * @version 1.0
 * @param <E> Generic type E
 */
public class NullSet<E> extends HashSet<E> {

    private static final long serialVersionUID = 1L;

    /**
     * Super standard constructor.
     */
    public NullSet() {
        super();
    }

    /**
     * Construct to initialize with a collection
     * @param c Initial collection to add
     */
    public NullSet(Collection<? extends E> c) {
        super(c);
    }

    /**
     * Overrides the addAll Method to allow null parameters.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            return false;
        } else {
            return super.addAll(c);
        }
    }

}
