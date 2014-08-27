package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;
import net.microtriangle.soedar.eventmanager.MemoryEventManager;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final EventManager eventManager = new MemoryEventManager();

        eventManager.subscribe(KwicEvent.OUTPUT, new EventCallback() {
            @Override
            public void callback(Map<String, Object> object) {
                System.out.println("Called: " + object.get(KwicEvent.OUTPUT_DICT_VALUE));
            }
        });

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (line != null) {
            eventManager.publish(KwicEvent.OUTPUT, KwicEvent.OUTPUT_DICT_VALUE, line);
            line = scanner.nextLine();
        }

        scanner.close();
    }
}
