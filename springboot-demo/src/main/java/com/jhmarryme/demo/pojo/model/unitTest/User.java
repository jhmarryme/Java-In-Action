package com.jhmarryme.demo.pojo.model.unitTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用于单元测试学习
 * @author JiaHao Wang
 * @date 2021/6/21 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "unit_test_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
