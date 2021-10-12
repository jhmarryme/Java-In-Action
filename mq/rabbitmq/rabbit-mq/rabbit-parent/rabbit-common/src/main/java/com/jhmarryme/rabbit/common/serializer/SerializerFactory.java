package com.jhmarryme.rabbit.common.serializer;

import com.jhmarryme.rabbit.api.Message;

/**
 *
 * @author JiaHao Wang
 * @date 2021/9/27 下午1:16
 */
public class SerializerFactory {

    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
