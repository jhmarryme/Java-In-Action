package com.jhmarryme.demo.web.controller.demo;

import com.jhmarryme.demo.common.annotation.OperLog;
import com.jhmarryme.demo.common.annotation.ResponseResultBody;
import com.jhmarryme.demo.pojo.model.system.log.OperationLog;
import com.jhmarryme.demo.pojo.vo.TestVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2021/3/5 12:15
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @RequestMapping(path = "formValidate", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseResultBody
    @OperLog(operModule = "表单校验测试", operType = "测试type", operDesc = "测试Desc")
    public OperationLog formValidate(@RequestBody @Validated TestVO vo) {
        return new OperationLog();
    }
}
