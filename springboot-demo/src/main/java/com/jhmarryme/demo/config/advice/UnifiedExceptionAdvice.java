package com.jhmarryme.demo.config.advice;

import com.jhmarryme.demo.common.base.CommonResult;
import com.jhmarryme.demo.common.base.interfaces.IResponseEnum;
import com.jhmarryme.demo.common.base.enums.ResponseEnum;
import com.jhmarryme.demo.common.base.exception.BaseException;
import com.jhmarryme.demo.common.util.I18nMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一异常处理切面
 * @author JiaHao Wang
 * @date 2021/3/8 16:19
 */
@Slf4j
@RestControllerAdvice
public class UnifiedExceptionAdvice {

    @Autowired
    MessageSource messageSource;

    @Autowired
    LocaleResolver localeResolver;

    @ExceptionHandler(BaseException.class)
    public Object handleCommonException(BaseException e, HttpServletRequest request) {
        log.error("统一异常处理(业务异常):" + e);
        return buildResult(e.getResponseEnum(), e.getData(), e.getParams());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                        HttpServletRequest request) {
        log.error("参数校验异常:" + e);
        List<String> list = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(x -> list.add(x.getDefaultMessage()));
        return buildResult(ResponseEnum.PARAM_VERIFICATION_ERROR, list);
    }

    @ExceptionHandler(Exception.class)
    public Object error(Exception e) {
        log.error("统一异常处理(业务异常):" + e);
        return buildResult(ResponseEnum.UNKNOWN_ERROR, null);
    }

    private CommonResult<Object> buildResult(IResponseEnum responseEnum, Object data, String... params) {
        // 国际化
        String msg = I18nMessageUtil.getMsg(responseEnum.getCode(), params);
        CommonResult<Object> result = CommonResult.failure(responseEnum, data);
        result.setMsg(msg);
        return result;
    }

}
