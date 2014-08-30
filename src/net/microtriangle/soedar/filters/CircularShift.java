package net.microtriangle.soedar.filters;

import java.util.*;

/**
 * Created by soedar on 28/8/14.
 */
public class CircularShift implements FilterModule {

    @Override
    public Object trigger(Object input) {
        if (input instanceof String) {
            return circularShiftTitle((String) input);
        } else if (input instanceof Collection) {
            return circularShiftTitles((Collection<String>) input);
        }

        throw new IllegalArgumentException();
    }

    private Collection<String> circularShiftTitles(Collection<String> titles) {
        HashSet<String> output = new HashSet<String>();

        for (String title : titles) {
            output.addAll(circularShiftTitle(title));
        }

        return new ArrayList<String>(output);
    }

    private Collection<String> circularShiftTitle(String title) {
        ArrayList<String> output = new ArrayList<String>();
        LinkedList<String> words = new LinkedList<String>(Arrays.asList(title.split(" ")));

        for (int i=0;i<words.size();i++) {
            StringBuilder sb = new StringBuilder();

            for (String word : words) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(word);
            }

            words.add(words.removeFirst());
            output.add(sb.toString());
        }

        return output;
    }
}
