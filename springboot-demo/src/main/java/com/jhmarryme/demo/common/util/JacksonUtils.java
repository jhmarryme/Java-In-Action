package com.jhmarryme.demo.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 基于Jackson的json工具类
 * @author Jiahao Wang
 * @date 2021/4/1 9:46
 */
@Slf4j
public class JacksonUtils {

    /** 定义jackson对象 */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串
     *
     * @param obj 需要转换的对象
     * @return java.util.Optional<java.lang.String>
     */
    public static Optional<String> obj2Json(Object obj) {
        try {
            // 如果对象为null, 默认情况下会返回"null"字符串, 如果需要返回null对象, 需要做处理
            return Optional.ofNullable(MAPPER.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.info("将对象转换成json字符串失败");
        }
        return Optional.empty();
    }

    /**
     * 将json结果集转化为对象
     *
     * @param json json数据
     * @param type 对象中的object类型
     * @return java.util.Optional<T>
     */
    public static <T> Optional<T> json2Pojo(String json, Class<T> type) {
        try {
            return Optional.ofNullable(MAPPER.readValue(json, type));
        } catch (Exception e) {
            log.info("将json转化为对象失败");
        }
        return Optional.empty();
    }

    /**
     * 将json结果集转化为对象
     *
     * @param json json数据
     * @param type 对象中的object类型
     * @return java.util.List<T>
     */
    public static <T> List<T> json2List(String json, Class<T> type) {
        try {
            List<T> list = MAPPER.readValue(json, new TypeReference<List<T>>() {});
            if (CollectionUtils.isEmpty(list)) {
                return Lists.newArrayList();
            }
            return list;
        } catch (Exception e) {
            log.info("将json数据转换成pojo对象list失败");
            return Lists.newArrayList();
        }
    }

    /**
     * 将json转为map
     *
     * @param json json数据
     * @return java.util.Optional<java.util.Map>
     */
    public static Optional<Map> json2Map(String json) {
        try {
            return Optional.ofNullable(MAPPER.readValue(json, Map.class));
        } catch (JsonProcessingException e) {
            log.info("将json转为map失败");
        }
        return Optional.empty();
    }

    /**
     * 将json转为map, 需要传入类型
     *
     * @param json json数据
     * @param type 对象中的object类型
     * @return java.util.Optional<java.util.Map < java.lang.String, T>>
     */
    public static <T> Optional<Map<String, T>> json2PojoMap(String json, Class<T> type) {
        try {
            return Optional.ofNullable(MAPPER.readValue(json, new TypeReference<Map<String, T>>() {}));
        } catch (JsonProcessingException e) {
            log.info("将json转为map失败");
        }
        return Optional.empty();
    }

    /**
     * 将map转换为pojo对象
     *
     * @param map 需要转换的map
     * @param type 对象中的object类型
     * @return java.util.Optional<T>
     */
    public static <T> Optional<T> map2obj(Map map, Class<T> type) {
        try {
            return Optional.ofNullable(MAPPER.convertValue(map, type));
        } catch (IllegalArgumentException e) {
            log.info("将map转为pojo失败");
        }
        return Optional.empty();
    }
}
