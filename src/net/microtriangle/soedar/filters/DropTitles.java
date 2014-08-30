package net.microtriangle.soedar.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by soedar on 28/8/14.
 */
public class DropTitles implements FilterModule {
    private HashSet<String> ignoredWords;

    public DropTitles(Collection<String> ignoredWords) {
        this.ignoredWords = new HashSet<String>(ignoredWords);
    }

    @Override
    public Object trigger(Object input) {
        if (input instanceof Collection) {
            return filterTitles((Collection<String>) input);
        }
        throw new IllegalArgumentException();
    }

    private Collection<String> filterTitles(Collection<String> titles) {
        ArrayList<String> filteredTitles = new ArrayList<String>();

        for (String title : titles) {
            if (shouldKeep(title)) {
                filteredTitles.add(title);
            }
        }
        return filteredTitles;
    }

    private boolean shouldKeep(String title) {
        int i = title.indexOf(' ');
        String firstWord = (i < 0) ? title : title.substring(0, i);

        return !ignoredWords.contains(firstWord);
    }
}
