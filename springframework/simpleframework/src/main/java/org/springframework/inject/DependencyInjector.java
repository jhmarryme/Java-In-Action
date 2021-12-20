package org.springframework.inject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.BeanContainer;
import org.springframework.inject.annotation.Autowired;
import org.springframework.util.ClassUtil;
import org.springframework.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

/**
 * @author JiaHao Wang
 * @date 2021/12/20 8:37 下午
 */
@Slf4j
public class DependencyInjector {

    private final BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行Ioc
     *
     * @author Jiahao Wang
     * @date 2021/12/20 10:21 下午
     */
    public void doIoc() {
        Set<Class<?>> classSet = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("empty classset in BeanContainer");
            return;
        }
        // 1. 遍历 Bean 容器中所有的 Class 对象
        classSet.forEach(clazz -> {
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                return;
            }
            // 2. 遍历 Class 对象的所有成员变量
            for (Field field : fields) {
                // 3. 找出被 Autowired 标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();
                    // 4. 获取这些成员变量的类型
                    Class<?> fieldClass = field.getType();
                    // 5. 获取这些成员变量的类型在容器里对应的实例
                    Object fieldInstance = getFieldInstance(fieldClass, autowiredValue);
                    if (fieldInstance == null) {
                        throw new RuntimeException("unable to inject relevant type，target fieldClass is:"
                                + fieldClass.getName()
                                + " autowiredValue is : "
                                + autowiredValue);
                    }
                    // 6. 通过反射将对应的成员变量实例注入到成员变量所在类的实例里
                    Object targetBean = beanContainer.getBean(clazz);
                    ClassUtil.setField(field, targetBean, fieldInstance, true);
                }
            }

        });
    }

    /**
     * 根据指定的Class对象 在beanContainer里获取其实例或实现类的实例
     *
     * @author Jiahao Wang
     * @date 2021/12/20 10:06 下午
     * @param fieldClass class对象
     * @param autowiredValue 注解中指定的类名
     * @return java.lang.Object
     *          null => 未能获取到实例或实现类的实例
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        // 尝试根据当前Class对象直接获取实例
        Object fieldInstance = beanContainer.getBean(fieldClass);
        if (fieldInstance != null) {
            return fieldInstance;
        }

        // 未能直接获取到实例, 说明这个成员变量是一个接口, 需要得到它的实现类Class对象
        Optional<Class<?>> implementedClass = getImplementedClass(fieldClass, autowiredValue);

        // 尝试根据实现类的Class对象获取实例
        Optional<Object> bean = implementedClass.map(beanContainer::getBean);
        return bean.orElse(null);
    }

    /**
     * 根据指定的class对象 获取其对应的实现类的Class对象
     *
     * @author Jiahao Wang
     * @date 2021/12/20 10:08 下午
     * @param fieldClass class对象
     * @param autowiredValue 注解中指定的类名
     * @return java.util.Optional<java.lang.Class < ?>>
     */
    private Optional<Class<?>> getImplementedClass(Class<?> fieldClass, String autowiredValue) {

        // 获取实现类或者子类的Class集合
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);

        if (ValidationUtil.isEmpty(classSet)) {
            return Optional.empty();
        }

        // [注解中指定的类名] 为空
        if (ValidationUtil.isEmpty(autowiredValue)) {
            if (classSet.size() == 1) {
                return Optional.ofNullable(classSet.iterator().next());
            } else {
                // 如果多于两个实现类且用户未指定其中一个实现类，则抛出异常
                throw new RuntimeException("multiple implemented classes for "
                        + fieldClass.getName()
                        + " please set @Autowired's value to pick one");
            }
        }

        // 尝试从实现类或者子类的Class集合中匹配一个 [注解中指定的类名] 的Class对象
        return classSet.stream()
                .filter(clazz -> autowiredValue.equals(clazz.getSimpleName()))
                .findFirst();
    }
}
