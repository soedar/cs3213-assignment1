package net.microtriangle.soedar.filters;

import java.util.*;

/**
 * Created by soedar on 28/8/14.
 */
public class Sorter implements FilterModule {

    @Override
    public Object trigger(Object input) {
        if (input instanceof Collection) {
            return sort((Collection) input);
        }
        throw new IllegalArgumentException();
    }

    private LinkedList sort(Collection<? extends Comparable> input) {
        LinkedList collection = new LinkedList(input);
        Collections.sort(collection);

        return collection;
    }
}
