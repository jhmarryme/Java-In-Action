package com.example.localmsgtable.controller;


import com.example.localmsgtable.service.OrderService;
import com.example.localmsgtable.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 */
@RestController
public class LocalMsgTableTestController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/handleOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String handleOrder(int orderId) {
        int result = orderService.handleOrder(orderId);
        if (result != 0) {
            return "failed";
        }
        return "success";
    }

    @RequestMapping(path = "/payment", method = {RequestMethod.GET, RequestMethod.POST})
    public String payment(int userId, int orderId, Long amount) {
        int result = paymentService.payment(userId, orderId, amount);
        return "支付结果" + result;
    }
}
