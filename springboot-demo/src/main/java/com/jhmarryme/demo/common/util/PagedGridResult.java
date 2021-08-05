package com.jhmarryme.demo.common.util;

import lombok.Data;

import java.util.List;

/**
 * 用来返回分页Grid的数据格式
 * @author Jiahao Wang
 * @date 2021/4/1 10:19
 */
@Data
public class PagedGridResult {

    /** 当前页数 */
    private int page;
    /** 总页数 */
    private int total;
    /** 总记录数 */
    private long records;
    /** 每行显示的内容 */
    private List<?> rows;

}
