package com.jhmarryme.demo.pojo.model.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author JiaHao Wang
 * @date 1/2/21 12:02 PM
 */
@Data
@Table(name = "coder")
public class Coder {
    @Id
    private Integer id;

    @Column(name = "rotation_order")
    private Integer rotationOrder;

    @Column(name = "real_name")
    private String realName;

}
