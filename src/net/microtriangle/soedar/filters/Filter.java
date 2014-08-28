package net.microtriangle.soedar.filters;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;

import java.util.ArrayList;

/**
 * Created by soedar on 28/8/14.
 */
public class Filter implements EventCallback {
    private final EventManager manager;
    private final FilterModule module;
    private final ArrayList<String> outputEvents = new ArrayList<String>();

    public Filter(EventManager manager, FilterModule module) {
        this.manager = manager;
        this.module = module;
    }

    public void addInputEvent(String eventName) {
        manager.subscribe(eventName, this);
    }

    public void addOutputEvent(String eventName) {
        outputEvents.add(eventName);
    }

    @Override
    public void callback(Object input) {
        Object output = module.trigger(input);
        for (String outputEvent : outputEvents) {
            manager.publish(outputEvent, output);
        }
    }
}
