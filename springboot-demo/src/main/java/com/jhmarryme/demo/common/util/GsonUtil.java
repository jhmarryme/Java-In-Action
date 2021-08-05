package com.jhmarryme.demo.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 基于Gson的json工具类
 * @author JiaHao Wang
 * @date 2021/2/27 21:59
 */
@Slf4j
public class GsonUtil {

    private static final Gson GSON;

    static {
        GSON = new GsonBuilder().create();
    }

    /**
     * 将对象转为json字符串
     *
     * @param obj 需要转化的对象
     * @return java.lang.String
     */
    public static Optional<String> obj2Json(Object obj) {
        try {
            return Optional.ofNullable(GSON.toJson(obj));
        } catch (Exception e) {
            log.error("序列化对象失败");
        }
        return Optional.empty();
    }

    /**
     * 将指定类型的对象转为json字符串
     *
     * @param t 需要转换的pojo
     * @param cls 对象中的object类型
     * @return java.lang.String
     */
    public static <T> Optional<String> pojo2Json(T t, Class<T> cls) {
        try {
            return Optional.ofNullable(GSON.toJson(t, cls));
        } catch (Exception e) {
            log.error("序列化对象失败");
        }
        return Optional.empty();
    }

    /**
     * 将json字符串转为指定类型的实例
     *
     * @param json json数据
     * @param cls 对象中的object类型
     * @return T
     */
    public static <T> Optional<T> jsonToPojo(String json, Class<T> cls) {
        try {
            return Optional.ofNullable(GSON.fromJson(json, cls));
        } catch (Exception e) {
            log.error("反序列化对象失败");
        }
        return Optional.empty();
    }

    /**
     * 将json转为指定类型的List
     *
     * @param json json数据
     * @return java.util.List<T>
     */
    public static <T> List<T> jsonToList(String json) {
        try {
            // Gson的TypeToken来确定要反序列化的正确类型
            return GSON.fromJson(json, new TypeToken<List<T>>() {}.getType());
        } catch (Exception e) {
            log.error("反序列化对象失败");
        }
        return Lists.newArrayList();
    }

    /**
     * 将json转为map
     *
     * @param json json数据
     * @return java.util.Optional<java.lang.Object>
     */
    public static <T> Optional<Object> jsonToMap(String json) {
        try {
            return Optional.ofNullable(GSON.fromJson(json, new TypeToken<Map<String, T>>() {}.getType()));
        } catch (Exception e) {
            log.error("反序列化对象失败");
        }
        return Optional.empty();
    }

    /**
     * 将json转为Map List
     *
     * @param json json数据
     * @return java.util.List<java.util.Map < java.lang.String, T>>
     */
    public static <T> List<Map<String, T>> jsonToMapList(String json) {
        try {
            // Gson的TypeToken来确定要反序列化的正确类型
            return GSON.fromJson(json, new TypeToken<List<Map<String, T>>>() {}.getType());
        } catch (Exception e) {
            log.error("反序列化对象失败");
        }
        return Lists.newArrayList();
    }

}
