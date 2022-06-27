package demo.pattern.eventListener;

/**
 * 对双击事件进行监听的监听器
 * @author JiaHao Wang
 * @date 6/27/22 10:17 AM
 */
public class DoubleClickEventListener implements EventListener {
    @Override
    public void processEvent(Event event) {
        // 判断事件类型--双击
        if ("doubleclick".equals(event.getType())) {
            System.out.println("双击被触发了");
        }
    }
}
