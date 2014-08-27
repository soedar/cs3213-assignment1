package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;
import net.microtriangle.soedar.eventmanager.MemoryEventManager;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final EventManager eventManager = new MemoryEventManager();

        eventManager.subscribe("helloevent", new EventCallback() {
            @Override
            public void callback(Map<String, Object> object) {
                System.out.println("Called");
            }
        });

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (line != null) {
            System.out.println(line);
            if ("hello".equals(line)) {
                System.out.println("here");
                eventManager.publish("helloevent");
            }
            line = scanner.nextLine();
        }

        scanner.close();
    }
}
