package net.microtriangle.soedar.filters;

import java.util.*;

/**
 * Created by soedar on 28/8/14.
 */
public class ConvertCase implements FilterModule {
    private HashSet<String> ignoredWords;

    public ConvertCase() {
        this.ignoredWords = new HashSet<String>();
    }

    public ConvertCase(Collection<String> ignoredWords) {
        this.ignoredWords = new HashSet<String>(ignoredWords);
    }

    @Override
    public Object trigger(Object input) {
        if (input instanceof String) {
            return convertCaseTitle((String) input);
        } else if (input instanceof Collection) {
            return convertCaseTitles((Collection) input);
        }
        throw new IllegalArgumentException();
    }

    private String convertCaseTitle(String title) {
        LinkedList<String> words = new LinkedList<String>(Arrays.asList(title.split(" ")));
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (sb.length() > 0) {
                sb.append(" ");
            }

            word = word.toLowerCase();
            if (ignoredWords.contains(word)) {
                sb.append(word);
            } else if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                sb.append(word.substring(1));
            }
        }
        return sb.toString();
    }

    private Collection<String> convertCaseTitles(Collection<String> titles) {
        ArrayList<String> newTitles = new ArrayList<String>();
        for (String title : titles) {
            newTitles.add(convertCaseTitle(title));
        }
        return newTitles;
    }
}
