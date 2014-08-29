package net.microtriangle.soedar.eventmanager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by soedar on 28/8/14.
 */
public class ThreadedEventManager implements EventManager {
    private final HashMap<String, ArrayList<EventCallback>> subscribers = new HashMap<String, ArrayList<EventCallback>>();

    @Override
    public void subscribe(String eventName, EventCallback callback) {
        synchronized (subscribers) {
            ArrayList<EventCallback> subs = subscribers.get(eventName);
            if (subs == null) {
                subs = new ArrayList<EventCallback>();
                subscribers.put(eventName, subs);
            }
            subs.add(callback);
        }
    }

    @Override
    public void publish(String eventName, final Object object) {
        ArrayList<EventCallback> subs;
        synchronized(subscribers) {
            subs = subscribers.get(eventName);
            if (subs == null) {
                return;
            }
            subs = new ArrayList<EventCallback>(subs);
        }

        for (final EventCallback callback : subs) {
            (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        callback.callback(object);
                    }
                })).start();
        }
    }
}
