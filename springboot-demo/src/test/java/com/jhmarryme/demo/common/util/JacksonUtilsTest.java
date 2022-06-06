package com.jhmarryme.demo.common.util;

import com.jhmarryme.demo.common.base.exception.BusinessException;
import com.jhmarryme.demo.pojo.vo.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author JiaHao Wang
 * @date 2021/4/9 11:33
 */
@Slf4j
class JacksonUtilsTest {

    private TestVO testVO;

    private String jsonList;

    private String json;

    private String mapJson;

    @BeforeEach
    void setUp() {
        testVO = createData();
        jsonList = "[{\"name\":\"wjh\",\"key\":\"123\",\"state\":\"1\"},{\"name\":\"wjh\",\"key\":\"123\"," +
                "\"state\":\"1\"},{\"name\":\"wjh\",\"key\":\"123\",\"state\":\"1\"}]";
        json = "{\"name\":\"wjh\",\"key\":\"123\",\"state\":\"1\"}";
        mapJson = "{\"1\":{\"name\":\"wjh\",\"key\":\"123\",\"state\":\"1\"},\"2\":{\"name\":\"wjh\",\"key\":\"123\"," +
                "\"state\":\"1\"},\"3\":{\"name\":\"wjh\",\"key\":\"123\",\"state\":\"1\"},\"4\":{\"name\":\"wjh\"," +
                "\"key\":\"123\",\"state\":\"1\"}}";
    }

    @Test
    @DisplayName("对象转json")
    void obj2Json() {
        // 正常转换
        Assertions.assertNotEquals(JacksonUtils.obj2Json(testVO).orElse("null"), "null");
        // 对象为null时, 会返回"null"串
        Assertions.assertEquals(JacksonUtils.obj2Json(null).orElse("error"), "null");
    }

    @Test
    void json2Pojo() {
        TestVO actual = JacksonUtils.json2Pojo(json, TestVO.class).orElseThrow(BusinessException::new);
        Assertions.assertNotNull(actual);
    }

    @Test
    void json2List() {
        List<TestVO> testVOS = JacksonUtils.json2List(jsonList, TestVO.class);
        Assertions.assertEquals(testVOS.size(), 3);
    }

    @Test
    void json2map() {
        Map map = JacksonUtils.json2Map(mapJson).orElseThrow(BusinessException::new);

        Assertions.assertEquals(map.size(), 4);
    }

    @Test
    void json2PojoMap() {
        Map<String, TestVO> testVOMap =
                JacksonUtils.json2PojoMap(mapJson, TestVO.class).orElseThrow(BusinessException::new);

        Assertions.assertEquals(testVOMap.size(), 4);
    }

    @Test
    void map2obj() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "www");
        map.put("key", "sxxxxxxx");
        map.put("state", "1");

        TestVO testVO = JacksonUtils.map2obj(map, TestVO.class).orElseThrow(BusinessException::new);
        Assertions.assertEquals(testVO.getName(), "www");
    }

    public TestVO createData() {
        return TestVO.builder().name("wjh").key("123").state("1").build();
    }
}