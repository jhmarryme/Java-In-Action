package com.example.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeadLine {
    //主键ID
    private Long lineId;
    //头条名字
    private String lineName;
    //头条链接，点击头条会进入对应的链接中
    private String lineLink;
    //头条图片
    private String lineImg;
    //权重，越大排的越靠前
    private Integer priority;
    //0 不可用 1 可用
    private Integer enableStatus;
    //创建时间
    private Date createTime;
    //最近一次的更新时间
    private Date lastEditTime;
}