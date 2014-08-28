package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;
import net.microtriangle.soedar.eventmanager.MemoryEventManager;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final EventManager eventManager = new MemoryEventManager();

        eventManager.subscribe(KwicEvent.OUTPUT, new EventCallback() {
            @Override
            public void callback(Object object) {
                System.out.println("Called: " + object);
            }
        });

        CircularShift circularShift = new CircularShift(eventManager, KwicEvent.CIRCULAR_SHIFT, KwicEvent.OUTPUT);

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (line != null) {
            eventManager.publish(KwicEvent.CIRCULAR_SHIFT, line);
            line = scanner.nextLine();
        }

        scanner.close();
    }
}
