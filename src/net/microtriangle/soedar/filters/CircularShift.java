package net.microtriangle.soedar.filters;

import java.util.*;

/**
 * Created by soedar on 28/8/14.
 */
public class CircularShift implements FilterModule {

    public Object trigger(Object input) {
        List<String> titles;
         if (input instanceof String) {
            titles = new ArrayList<String>();
            titles.add((String)input);
         } else if (input instanceof List) {
            titles = (List) input;
         } else {
            throw new IllegalArgumentException();
         }

        return circularShift(titles);
    }

    private List<String> circularShift(List<String> titles) {
        Set<String> output = new HashSet<String>();

        for (String title : titles) {
            output.addAll(titleCircularShift(title));
        }

        return new ArrayList<String>(output);
    }

    private List<String> titleCircularShift(String title) {
        List<String> output = new ArrayList<String>();
        List<String> words = new LinkedList<String>(Arrays.asList(title.split(" ")));

        for (int i=0;i<words.size();i++) {
            StringBuilder sb = new StringBuilder();

            String first = words.remove(0);
            for (String word : words) {
                sb.append(word);
                sb.append(" ");
            }
            sb.append(first);
            words.add(first);

            output.add(sb.toString());
        }

        return output;
    }
}
