package com.jhmarryme.demo.config.aspect;

import com.jhmarryme.demo.common.annotation.OperLog;
import com.jhmarryme.demo.common.base.exception.CommonException;
import com.jhmarryme.demo.common.util.IPUtil;
import com.jhmarryme.demo.common.util.GsonUtil;
import com.jhmarryme.demo.pojo.model.system.log.ExceptionLog;
import com.jhmarryme.demo.pojo.model.system.log.OperationLog;
import com.jhmarryme.demo.web.service.system.log.ExceptionLogService;
import com.jhmarryme.demo.web.service.system.log.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author JiaHao Wang
 * @date 2021/3/2 12:37
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ExceptionLogService exceptionLogService;

    @Pointcut("@annotation(com.jhmarryme.demo.common.annotation.OperLog)")
    public void opearationLogPointCut() {

    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     *
     */
    @Pointcut("execution(* com.jhmarryme.demo.web.controller..*.*(..))")
    public void operationExceptionLogPointCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
//    @AfterReturning(value = "opearationLogPointCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {

        log.info("saveOperLog");
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request =
                (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperationLog operlog = new OperationLog();
        try {
            // 主键ID
            operlog.setId(UUID.randomUUID().toString());

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                String operModul = opLog.operModule();
                String operType = opLog.operType();
                String operDesc = opLog.operDesc();
                // 操作模块
                operlog.setModule(operModul);
                // 操作类型
                operlog.setType(operType);
                // 操作描述
                operlog.setDescription(operDesc);
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            operlog.setMethod(methodName);
            assert request != null;

            // 请求类型
            operlog.setReqType(request.getMethod());
            // 请求体
            String reqBody = GsonUtil.obj2Json(joinPoint.getArgs()).orElseThrow(CommonException::new);
            operlog.setReqBody(reqBody);
            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = GsonUtil.obj2Json(rtnMap).orElseThrow(CommonException::new);
            // 请求参数
            operlog.setReqParam(params);
            // 返回结果
            operlog.setResParam(GsonUtil.obj2Json(keys).orElseThrow(CommonException::new));
            // 请求用户ID
            //            operlog.setOperUserId(UserShiroUtil.getCurrentUserLoginName());
            // 请求用户名称
            //            operlog.setOperUserName(UserShiroUtil.getCurrentUserName());
            // 请求IP
            operlog.setIp(IPUtil.getIpAddr(request));
            // 请求URI
            operlog.setUri(request.getRequestURI());
            // 创建时间
            operlog.setCreateTime(new Date());
            // 操作版本
            operlog.setVer("0.0.1");
            operationLogService.insertOne(operlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operationExceptionLogPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        log.info("saveExceptionLog");
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExceptionLog excepLog = new ExceptionLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            excepLog.setId(UUID.randomUUID().toString());
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            assert request != null;

            // 请求类型
            excepLog.setReqType(request.getMethod());
            // 请求体
            String reqBody = GsonUtil.obj2Json(joinPoint.getArgs()).orElseThrow(CommonException::new);
            excepLog.setReqBody(reqBody);
            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = GsonUtil.obj2Json(rtnMap).orElseThrow(CommonException::new);
            excepLog.setReqParam(params);
            // 请求参数
            excepLog.setMethod(methodName);
            // 请求方法名
            // 异常名称
            excepLog.setName(e.getClass().getName());
            // 异常信息
            excepLog.setMsg(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); //
            // 操作员ID
            //            excepLog.setOperUserId(UserShiroUtil.getCurrentUserLoginName());
            // 操作员名称
            //            excepLog.setOperUserName(UserShiroUtil.getCurrentUserName());
            // 操作URI
            excepLog.setUri(request.getRequestURI());
            // 操作员IP
            excepLog.setIp(IPUtil.getIpAddr(request));
            // 操作版本号
            excepLog.setVer("0.0.1");
            // 发生异常时间
            excepLog.setCreateTime(new Date());
            exceptionLogService.insertOne(excepLog);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
    }
}
