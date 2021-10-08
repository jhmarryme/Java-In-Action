package com.jhmarryme.rabbit.common.serializer;

/**
 * 用于rabbitMq MessageConverter， 序列化和反序列化的接口
 * @author JiaHao Wang
 * @date 2021/9/27 下午12:53
 */
public interface Serializer {

    /**
     * Obj -> byte[]
     *
     * @param data data
     * @return byte[]
     */
    byte[] serializeRaw(Object data);

    /**
     * Obj -> String
     *
     * @param data data
     * @return java.lang.String
     */
    String serialize(Object data);

    /**
     * String -> Obj
     *
     * @param content content
     * @return T
     */
    <T> T deserialize(String content);

    /**
     * byte[] -> Obj
     *
     * @param content content
     * @return T
     */
    <T> T deserialize(byte[] content);
}
