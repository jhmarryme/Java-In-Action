package com.jhmarryme.demo.pojo.model.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 节假日api所返回的 当日信息
 * @author JiaHao Wang
 * @date 1/3/21 10:22 AM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News implements Serializable {

    private String date;
    private Integer daycode;
    private String weekday;
    private String cnweekday;
    private String lunaryear;
    private String lunarmonth;
    private String lunarday;
    private String info;
    private String start;
    private String end;
    private String holiday;
    private String name;
    private String enname;
    private Integer isnotwork;
    private List<String> vacation;
    private List<String> remark;
    private String tip;
    private String rest;

}
