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
        if (args.length > 0) {
            if ("-h".equals(args[0]) || "--help".equals(args[0])) {
                showHelpMessage();
                return;
            }
            else {
                System.err.println("Ignoring unknown arguments");
            }
        }

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

    public static void showHelpMessage() {
        System.out.println("CS3213 Assignment 1 - KwicKwacKwoc");
        System.out.println("Soedarsono A0078541B");
        System.out.println("Usage: java -jar assignment1.jar");
        System.out.println("This program will keep reading from STDIN until EOF or empty line");
        System.out.println("");
        System.out.println("To ignore keywords, create a ignore.txt file in the same directory with a newline separated keywords to ignore");
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
