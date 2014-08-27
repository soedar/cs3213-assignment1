package net.microtriangle.soedar.eventmanager;

import java.util.Map;

/**
 * Created by soedar on 27/8/14.
 */
public interface EventManager {
    void subscribe(String eventName, EventCallback callback);
    void publish(String eventName);
    void publish(String eventName, String key, Object object);
    void publish(String eventName, Map<String, Object>objects);
}
