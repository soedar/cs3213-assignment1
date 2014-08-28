package net.microtriangle.soedar.eventmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by soedar on 27/8/14.
 */
public class MemoryEventManager implements EventManager {

    private final HashMap<String, ArrayList<EventCallback>> subscribers = new HashMap<String, ArrayList<EventCallback>>();

    @Override
    public synchronized void subscribe(String eventName, EventCallback callback) {
        ArrayList<EventCallback> subs = subscribers.get(eventName);
        if (subs == null) {
            subs = new ArrayList<EventCallback>();
            subscribers.put(eventName, subs);
        }
        subs.add(callback);

    }

    @Override
    public void publish(String eventName) {
        publish(eventName, null);
    }

    @Override
    public synchronized void publish(String eventName, Object object) {
        ArrayList<EventCallback> subs = subscribers.get(eventName);
        for (EventCallback callback : subs) {
            callback.callback(object);
        }
    }
}
