package demo.pattern.eventListener;

/**
 *
 * @author JiaHao Wang
 * @date 6/27/22 10:18 AM
 */
public class EventListenerDemo {

    public static void main(String[] args) {
        EventResource eventResource = new EventResource();
        eventResource.register(new SingleClickEventListener());
        eventResource.register(new DoubleClickEventListener());
        Event singleClickEvent = Event.builder().type("singleclick").build();
        eventResource.publishEvent(singleClickEvent);
    }
}
