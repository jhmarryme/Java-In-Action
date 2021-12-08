package demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author jhmarryme
 */
public class AnnotationParser {

    /** 解析类的注解 */
    public static void parseTypeAnnotation() throws ClassNotFoundException {

        Class<?> clazz = Class.forName("demo.annotation.TestCourse");
        // 这里获取的是只是class对象的注解，不包含其里面的方法和成员变量的注解
        Annotation[] annotations = clazz.getAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(CourseInfoAnnotation.class)) {
                CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation) annotation;
                System.out.println("课程名:" + courseInfoAnnotation.courseName() + "\n" +
                        "课程标签：" + courseInfoAnnotation.courseTag() + "\n" +
                        "课程简介：" + courseInfoAnnotation.courseProfile() + "\n" +
                        "课程序号：" + courseInfoAnnotation.courseIndex() + "\n");
            }
        }
    }

    /** 解析成员变量的注解 */
    public static void parseFieldAnnotation() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("demo.annotation.TestCourse");
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // 判断成员变量中是否有指定注解类型的注解
            boolean fieldAnnotationPresent = field.isAnnotationPresent(PersonInfoAnnotation.class);
            if (fieldAnnotationPresent) {
                PersonInfoAnnotation personInfoAnnotation = field.getAnnotation(PersonInfoAnnotation.class);
                System.out.println("名字：" + personInfoAnnotation.name() + "\n" +
                        "年龄：" + personInfoAnnotation.age() + "\n" +
                        "性别：" + personInfoAnnotation.gender());
                for (String language : personInfoAnnotation.language()) {
                    System.out.println("开发语言：" + language);
                }
            }
        }
    }

    /** 解析方法注解 */
    public static void parseMethodAnnotation() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("demo.annotation.TestCourse");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 判断方法中是否有指定注解类型的注解
            boolean hasAnnotation = method.isAnnotationPresent(CourseInfoAnnotation.class);
            if (hasAnnotation) {
                CourseInfoAnnotation courseInfoAnnotation = method.getAnnotation(CourseInfoAnnotation.class);
                System.out.println("\n" + "课程名：" + courseInfoAnnotation.courseName() + "\n" +
                        "课程标签：" + courseInfoAnnotation.courseTag() + "\n" +
                        "课程简介：" + courseInfoAnnotation.courseProfile() + "\n" +
                        "课程序号：" + courseInfoAnnotation.courseIndex() + "\n");
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        parseFieldAnnotation();
        // System.out.println("==========================");
        // parseMethodAnnotation();
        // System.out.println("==========================");
        // parseTypeAnnotation();
        // System.out.println("==========================");
    }
}
