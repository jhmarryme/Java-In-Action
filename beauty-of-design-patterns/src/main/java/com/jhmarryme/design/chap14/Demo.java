package com.jhmarryme.design.chap14;

import org.junit.jupiter.api.Test;

/**
 * 我们再来看下，基于重构之后的代码，如果再添加上面讲到的那个新功能，每秒钟接口超时请求个数超过某个最大阈值就告警，我们又该如何改动代码呢？主要的改动有下面四处。<p>
 * 第一处改动是：在 ApiStatInfo 类中添加新的属性 timeoutCount。<p>
 * 第二处改动是：添加新的 TimeoutAlertHander 类。<p>
 * 第三处改动是：在 ApplicationContext 类的 initializeBeans() 方法中，往 alert 对象中注册新的 timeoutAlertHandler。<p>
 * 第四处改动是：在使用 Alert 类的时候，需要给 check() 函数的入参 apiStatInfo 对象设置 timeoutCount 的值。<p>
 * <p>
 * <p>
 * 重构之后的代码更加灵活和易扩展。如果我们要想添加新的告警逻辑，只需要基于扩展的方式创建新的 handler 类即可，不需要改动原来的 check() 函数的逻辑。而且，
 * 我们只需要为新的 handler 类添加单元测试，老的单元测试都不会失败，也不用修改。
 */
public class Demo {

    @Test
    public void whenAlertSuccess() {
        ApiStatInfo apiStatInfo = ApiStatInfo.builder()
                .api("testApi")
                .errorCount(101)
                .requestCount(101)
                .durationOfSeconds(1)
                .timeoutCount(101)
                .build();
        AlertContext.getInstance().getAlert().check(apiStatInfo);
    }
}
