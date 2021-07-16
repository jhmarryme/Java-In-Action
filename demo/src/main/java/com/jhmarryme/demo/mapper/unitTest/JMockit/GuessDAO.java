package com.jhmarryme.demo.mapper.unitTest.JMockit;

import org.springframework.stereotype.Component;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/14 9:35
 */
@Component
public class GuessDAO {
    public void saveResult(boolean b, int number) {
        System.out.println("GuessDAO.saveResult");
    }
}
