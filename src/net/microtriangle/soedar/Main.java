package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;
import net.microtriangle.soedar.eventmanager.MemoryEventManager;
import net.microtriangle.soedar.filters.CircularShift;
import net.microtriangle.soedar.filters.ConvertCase;
import net.microtriangle.soedar.filters.DropTitles;
import net.microtriangle.soedar.filters.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> ignoredWords = new ArrayList<String>();
        ignoredWords.add("of");
        ignoredWords.add("and");

        final EventManager eventManager = new MemoryEventManager();

        eventManager.subscribe(KwicEvent.OUTPUT, new EventCallback() {
            @Override
            public void callback(Object object) {
                System.out.println("Called: " + object);
            }
        });

        Filter circularShiftFilter = new Filter(eventManager, new CircularShift());
        Filter convertCaseFilter = new Filter(eventManager, new ConvertCase(ignoredWords));
        Filter dropTitleFilter = new Filter(eventManager, new DropTitles(ignoredWords));

        circularShiftFilter.addInputEvent(KwicEvent.CIRCULAR_SHIFT);
        circularShiftFilter.addOutputEvent(KwicEvent.DROP_TITLES);

        dropTitleFilter.addInputEvent(KwicEvent.DROP_TITLES);
        dropTitleFilter.addOutputEvent(KwicEvent.CONVERT_CASE);

        convertCaseFilter.addInputEvent(KwicEvent.CONVERT_CASE);
        convertCaseFilter.addOutputEvent(KwicEvent.OUTPUT);


        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (line != null) {
            eventManager.publish(KwicEvent.CIRCULAR_SHIFT, line);
            line = scanner.nextLine();
        }

        scanner.close();
    }
}
