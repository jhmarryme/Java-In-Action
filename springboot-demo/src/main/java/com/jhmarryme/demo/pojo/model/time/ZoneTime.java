package com.jhmarryme.demo.pojo.model.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "zone_time")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZoneTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 数据库类型为 DateTime, 需要使用` @JsonForma`注解或者全局处理`LocalDateTime`
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //    private LocalDateTime createdAt;

    // 数据库类型为 DateTime, String 与 date 均可直接插入
    //    private String createdAt;
    private Date createdAt;

    /** 数据库类型为timeStamp, 注意最大时间有限制, 最大2038年 */
    private Instant updatedAt;

}