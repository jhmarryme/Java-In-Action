package demo.pattern.eventListener;

/**
 * 对单击事件进行监听的监听器
 * @author JiaHao Wang
 * @date 6/27/22 10:16 AM
 */
public class SingleClickEventListener implements EventListener {
    @Override
    public void processEvent(Event event) {
        // 判断事件类型--单击
        if ("singleclick".equals(event.getType())) {
            System.out.println("单击被触发了");
        }
    }
}
