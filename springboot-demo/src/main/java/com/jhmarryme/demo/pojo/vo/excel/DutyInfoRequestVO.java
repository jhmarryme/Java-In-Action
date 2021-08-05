package com.jhmarryme.demo.pojo.vo.excel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 *
 * @author JiaHao Wang
 * @date 1/2/21 12:32 PM
 */
@Data
@Builder
public class DutyInfoRequestVO {

    /** 需要打印的起始日期 */
    private LocalDate startDate;

    /** 需要打印的截止日期 */
    private LocalDate endDate;

    /** 从第几个开始 */
    private Integer startOrder;

}
