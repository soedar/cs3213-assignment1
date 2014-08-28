package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;

import java.util.*;

/**
 * Created by soedar on 28/8/14.
 */
public class CircularShift {
    private final EventManager eventManager;
    private final String inputEventName;
    private final String outputEventName;

    public CircularShift(final EventManager eventManager, String inputEventName, final String outputEventName) {
        this.eventManager = eventManager;
        this.inputEventName = inputEventName;
        this.outputEventName = outputEventName;

        this.eventManager.subscribe(inputEventName, new EventCallback() {
            @Override
            public void callback(Object object) {
                List<String> titles;

                if (object instanceof String) {
                    titles = new ArrayList<String>();
                    titles.add((String)object);
                } else if (object instanceof List) {
                    titles = (List) object;
                } else {
                    throw new IllegalArgumentException();
                }

                eventManager.publish(outputEventName, circularShift(titles));
            }
        });
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
