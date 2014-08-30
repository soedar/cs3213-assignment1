package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;
import net.microtriangle.soedar.eventmanager.ThreadedEventManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Set<String> titles = null;
        Set<String> ignoredWords = null;

        if (args.length > 0) {
            if ("-h".equals(args[0]) || "--help".equals(args[0])) {
                showHelpMessage();
                return;
            }

            try {
                titles = readFileDataFromArgs("-f", args);
            } catch (IOException e) {
                System.err.println("Invalid filename, reading from STDIN");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("No filename specified");
            }

            try {
                ignoredWords = readFileDataFromArgs("-i", args);
            } catch (IOException e) {
                System.err.println("Invalid ignore words file, no ignored words");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("No ignore words file specified");
            }
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

        if (titles == null) {
            titles = getTitlesFromStdin();
        }

        eventManager.publish(KwicEvent.INPUT, titles);
    }

    public static Set<String> readFileDataFromArgs(String flag, String[] args) throws IOException, ArrayIndexOutOfBoundsException {
        String filename = null;
        for (int i=0;i<args.length;i++) {
            if (args[i].equals(flag)) {
                filename = args[i+1];
                break;
            }
        }

        if (filename != null) {
            return readFile(filename);
        }
        return null;
    }

    public static void showHelpMessage() {
        System.out.println("CS3213 Assignment 1 - KwicKwacKwoc");
        System.out.println("Soedarsono A0078541B");
        System.out.println("usage: java -jar assignment1.jar [-f filename] [-i ignored-word-file]");
        System.out.println("If -f is not specified, this program will read from STDIN until EOF or empty line");
    }

    public static Set<String> readFile(String file) throws IOException {
        HashSet<String> lines = new HashSet<String>();

        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(fileReader);
            String word = bufferedReader.readLine();

            while (word != null) {
                lines.add(word);
                word = bufferedReader.readLine();
            }
        } finally {
            bufferedReader.close();
            return lines;
        }
    }

    public static Set<String> getTitlesFromStdin() {
        HashSet<String> titles = new HashSet<String>();

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
