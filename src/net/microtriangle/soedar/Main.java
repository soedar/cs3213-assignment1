package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;
import net.microtriangle.soedar.eventmanager.ThreadedEventManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Set<String> ignoredWords;
        try {
            ignoredWords = getIgnoredWords("ignore.txt");
        } catch (IOException e) {
            ignoredWords = new HashSet<String>();
        }

        final EventManager eventManager = new ThreadedEventManager();

        KwicFilters filters = new KwicFilters(eventManager, ignoredWords);
        filters.setup();

        eventManager.subscribe(KwicEvent.OUTPUT, new EventCallback() {
            @Override
            public void callback(Object object) {
                Collection<String> titles = (Collection<String>)object;
                for (String title : titles) {
                    System.out.println(title);
                }
            }
        });

        eventManager.publish(KwicEvent.INPUT, getTitles());
    }

    public static Set<String> getIgnoredWords(String file) throws IOException {
        HashSet<String> ignoredWords = new HashSet<String>();

        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            return ignoredWords;
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(fileReader);
            String word = bufferedReader.readLine();

            while (word != null) {
                ignoredWords.add(word);
                word = bufferedReader.readLine();
            }
        } finally {
            bufferedReader.close();
            return ignoredWords;
        }
    }

    public static List<String> getTitles() {
        ArrayList<String> titles = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);
        String title;
        do {
            title = scanner.nextLine();
            if (!"".equals(title)) {
                titles.add(title);
            }
        } while (!"".equals(title) && scanner.hasNextLine());

        scanner.close();
        return titles;
    }
}
