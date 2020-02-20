package modeltrain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tuple<E> {
    
    private final E elementOne;
    private final E elementTwo;
    
    public Tuple(E one, E two) {
        elementOne = one;
        elementTwo = two;
    }
    
    public E getFirst() {
        return elementOne;
    }
    
    public E getSecond() {
        return elementTwo;
    }
    
    public List<E> getAsList() {
        List<E> ret = new ArrayList<>();
        ret.add(elementOne);
        ret.add(elementTwo);
        return ret;
    }
    
    public Set<E> getAsSet() {
        Set<E> ret = new HashSet<>();
        ret.add(elementOne);
        ret.add(elementTwo);
        return ret;
    }
    
}
