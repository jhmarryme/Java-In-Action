package org.springframework.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Component;
import org.springframework.core.annotation.Controller;
import org.springframework.core.annotation.Repository;
import org.springframework.core.annotation.Service;
import org.springframework.util.ClassUtil;
import org.springframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JiaHao Wang
 * @date 2021/12/12 1:48 下午
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    /** 用于存放所有被配置标记的目标对象的Map */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    /** 需要加载bean的注解列表 */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION =
            Arrays.asList(Component.class, Controller.class, Repository.class, Service.class);

    /** 是否加载过bean */
    @Getter
    private Boolean loaded = Boolean.FALSE;

    /** 获取bean容器实例 */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        /** 单例对象 */
        HOLDER,
        ;

        private final BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 扫描加载所有的bean
     *
     * @author Jiahao Wang
     * @date 2021/12/12 2:40 下午
     * @param packageName 扫描的包名
     */
    public void loadBeans(String packageName) {
        // 判断bean容器是否被加载过
        if (this.getLoaded()) {
            log.warn("【WARN】BeanContainer has been loaded");
            return;
        }
        // 获取路径下所有的class对象
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("【WARN】extract nothing from packageName" + packageName);
            return;
        }

        // 如果类上有BEAN_ANNOTATION中的注解, 则将类的class作为键, 实例作为值放入beanMap
        classSet.forEach(
                clazzInClassSet ->
                        BEAN_ANNOTATION.stream()
                                .filter(clazzInClassSet::isAnnotationPresent)
                                .findFirst()
                                .ifPresent(aClass ->
                                        beanMap.put(clazzInClassSet, ClassUtil.newInstance(clazzInClassSet, true))));
        this.loaded = Boolean.TRUE;
    }

    public int size() {
        return beanMap.size();
    }

    /**
     * 添加一个class对象及其Bean实例
     * @param clazz Class对象
     * @param bean  Bean实例
     * @return 原有的Bean实例, 没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 移除一个IOC容器管理的对象
     *
     * @author Jiahao Wang
     * @date 2021/12/12 10:46 下午
     * @param clazz Class对象
     * @return java.lang.Object 删除的bean实例, 没有则返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取Bean实例
     *
     * @author Jiahao Wang
     * @date 2021/12/12 10:46 下午
     * @param clazz Class对象
     * @return java.lang.Object
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 获取容器管理的所有Class对象集合
     *
     * @author Jiahao Wang
     * @date 2021/12/12 10:47 下午
     * @return java.util.Set<java.lang.Class < ?>> Class对象集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有Bean集合
     *
     * @author Jiahao Wang
     * @date 2021/12/12 10:47 下午
     * @return java.util.Set<java.lang.Object> Bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }


    /**
     * 根据注解筛选出Bean的Class集合
     *
     * @author Jiahao Wang
     * @date 2021/12/12 10:49 下午
     * @param annotation 注解
     * @return java.util.Set<java.lang.Class < ?>> Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        // 1.获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return Collections.emptySet();
        }

        // 2.通过注解筛选被注解标记的class对象，并添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        keySet.forEach(clazz -> {
            // 类是否有相关的注解标记
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        });

        return classSet;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括其本身
     *
     * @author Jiahao Wang
     * @date 2021/12/12 10:50 下午
     * @param interfaceOrClass 接口或者父类的Class
     * @return java.util.Set<java.lang.Class < ?>> Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        // 1.获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return Collections.emptySet();
        }

        // 2.判断keySet里的元素是否是传入的接口或者类的子类，如果是，就将其添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // 判断keySet里的元素是否是传入的接口或者类的子类（非实现类对象或子类对象）
            // 父类.class.isAssignableFrom(子类.class)
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
                classSet.add(clazz);
            }
        }

        return classSet;
    }

}
