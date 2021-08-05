package com.jhmarryme.demo.pojo.vo.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author JiaHao Wang
 * @date 2021/6/21 12:39
 */
@Data
public class TimeReceiveReqVO {

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String dateString;

    private Instant instant;
}
