package org.springframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author JiaHao Wang
 * @date 2021/12/8 4:16 下午
 */
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    /**
     * 扫描包下所有文件, 并获取所有的类的set
     *
     * @author Jiahao Wang
     * @date 2021/12/8 10:45 下午
     * @param packageName 扫描的包名
     * @return java.util.Set<java.lang.Class < ?>>
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {

        // 1. 获取类加载器
        ClassLoader classLoader = ClassUtil.getClassLoader();
        // 2. 通过类加载器获取到加载的资源
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("unable to retrieve anything from package: " + packageName);
            return Collections.emptySet();
        }
        // 3. 依据不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> classSet = null;
        if (FILE_PROTOCOL.equals(url.getProtocol())) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }

        return classSet;
    }

    /**
     * 实例化class
     *
     * @author Jiahao Wang
     * @date 2021/12/12 2:18 下午
     * @param clazz Class
     * @param accessible 是否支持创建出私有class对象的实例
     * @return T 类的实例
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    private ClassUtil() {
    }

    /**
     *
     *
     * @author Jiahao Wang
     * @date 2021/12/8 10:07 下午
     * @param classSet 收集class文件的set, 第一次为空
     * @param packageDirectory file对象, 第一次为包所对应的file
     * @param packageName 扫描的包名
     */
    private static void extractClassFile(Set<Class<?>> classSet, File packageDirectory, String packageName) {

        // 递归结束条件: 当前 file 非文件夹
        if (!packageDirectory.isDirectory()) {
            return;
        }

        // 是文件夹时, 遍历当前目录的所有 file
        File[] files = packageDirectory.listFiles(file -> {
            // file 为文件夹, 还需要继续遍历
            if (file.isDirectory()) {
                return true;
            } else {
                String absolutePath = file.getAbsolutePath();
                // 若是class文件，则直接加载
                if (absolutePath.endsWith(".class")) {
                    // 添加该class文件到set集合中
                    Class<?> clazz = getClassFromAbsolutePath(absolutePath, packageName);
                    classSet.add(clazz);
                }
            }
            return false;
        });

        if (files != null) {
            for (File file : files) {
                // 递归
                extractClassFile(classSet, file, packageName);
            }
        }
    }

    /**
     * 根据class文件的绝对值路径，获取并生成class对象
     *
     * @author Jiahao Wang
     * @date 2021/12/8 10:00 下午
     * @param absolutePath 文件的路径 如/Users/baidu/imooc/springframework/sampleframework/target/classes/com/imooc/entity
     *                     /dto/MainPageInfoDTO.class
     * @param packageName 传入的包名 如com.imooc.entity
     * @return java.lang.Class<?>
     */
    private static Class<?> getClassFromAbsolutePath(String absolutePath, String packageName) {
        // 1. 通过class文件的绝对路径提取全限定类名
        // 处理文件路径中的分隔符
        absolutePath = absolutePath.replace(File.separator, ".");
        // 获取包含后缀的类名
        String className = absolutePath.substring(absolutePath.indexOf(packageName));
        // 去除后缀名
        className = className.substring(0, className.lastIndexOf("."));
        // 加载com.imooc.entity.dto.MainPageInfoDTO
        // 2. 通过反射获取对应的class对象
        return loadClass(className);
    }

    /**
     * 通过全限定类名加载Class文件
     *
     * @author Jiahao Wang
     * @date 2021/12/8 10:03 下午
     * @param className 全限定类名
     * @return java.lang.Class<?>
     */
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前线程所对应的类加载器
     *
     * @author Jiahao Wang
     * @date 2021/12/8 10:43 下午
     * @return java.lang.ClassLoader
     */
    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


}
