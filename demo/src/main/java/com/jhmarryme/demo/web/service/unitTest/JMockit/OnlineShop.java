package com.jhmarryme.demo.web.service.unitTest.JMockit;


import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Animal;

public interface OnlineShop {
    Boolean isLogin = null;

    String getOnlineShopStatus();
    Boolean isLogin();
    Animal getAnimal(String id);
}
