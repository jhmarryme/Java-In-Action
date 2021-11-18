package com.example.localmsgtable.service;

import com.example.localmsgtable.db1.dao.PaymentMsgMapper;
import com.example.localmsgtable.db1.model.PaymentMsg;
import com.example.localmsgtable.db1.model.PaymentMsgExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/15 上午11:13
 */
@Service
@Slf4j
public class OrderScheduler {

    @Resource
    private PaymentMsgMapper paymentMsgMapper;

    @Scheduled(cron = "0/10 * * * * ?")
    public void orderNotify() throws IOException {
        // 查询所有未发送消息的记录
        PaymentMsgExample paymentMsgExample = new PaymentMsgExample();
        paymentMsgExample.createCriteria().andStatusEqualTo(0);
        List<PaymentMsg> paymentMsgs = paymentMsgMapper.selectByExample(paymentMsgExample);
        if (paymentMsgs == null || paymentMsgs.size() == 0) {
            log.info("paymentMsg is null");
            return;
        }
        // 循环调用订单回调接口(类似于支付宝通知业务系统该订单已支付成功)
        for (PaymentMsg paymentMsg : paymentMsgs) {
            Integer orderId = paymentMsg.getOrderId();
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("http://localhost:7777/handleOrder");
            NameValuePair orderIdPair = new BasicNameValuePair("orderId", orderId + "");
            List<NameValuePair> list = new ArrayList<>();
            list.add(orderIdPair);
            HttpEntity httpEntity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());

            // 更新消息状态
            if ("success".equals(result)) {
                paymentMsg.setStatus(1);

            } else {
                paymentMsg.setTryCount(paymentMsg.getTryCount() + 1);
                if (paymentMsg.getTryCount() >= 5) {
                    paymentMsg.setStatus(2);
                }
            }
            paymentMsgMapper.updateByPrimaryKeySelective(paymentMsg);
            log.info("处理订单[{}]status:[{}]", orderId, paymentMsg.getStatus());
        }
    }
}
