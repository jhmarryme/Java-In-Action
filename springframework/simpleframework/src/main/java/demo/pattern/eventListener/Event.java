package demo.pattern.eventListener;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author JiaHao Wang
 * @date 6/27/22 10:03 AM
 */
@Data
@Builder
public class Event {
    /** 事件类型 */
    private String type;
}
