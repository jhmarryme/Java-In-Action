package demo.pattern.eventListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JiaHao Wang
 * @date 6/27/22 10:03 AM
 */
public class EventResource {

    /** 需要将事件监听器注册到事件源中，因为事件监听器有多个，所以需要List */
    private final List<EventListener> eventListeners = new ArrayList<>();

    public void register(EventListener listener) {
        eventListeners.add(listener);
    }

    public void publishEvent(Event event) {
        for (EventListener listener : eventListeners) {
            listener.processEvent(event);
        }
    }
}
