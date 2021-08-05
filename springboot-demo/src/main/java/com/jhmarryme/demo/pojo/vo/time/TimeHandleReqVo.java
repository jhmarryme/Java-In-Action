package com.jhmarryme.demo.pojo.vo.time;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 *
 * @author JiaHao Wang
 * @date 2021/6/21 10:45
 */
@Data
public class TimeHandleReqVo {

    /** 使用LocalDateTime接收日期 */
    private LocalDateTime createdAtInLocalDateTime;

    /** 使用Instant接收带有时区的日期 */
    private Instant updatedAt;
}
