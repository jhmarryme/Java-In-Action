package com.jhmarryme.rabbit.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author JiaHao Wang
 * @date 2021/9/28 下午1:02
 */
@Configuration
@ComponentScan({"com.jhmarryme.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {
}
