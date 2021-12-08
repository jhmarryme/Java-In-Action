package demo.annotation;

/**
 * @author jhmarryme
 */
@CourseInfoAnnotation(courseName = "编程", courseTag = "programing", courseProfile = "又难又多")
public class TestCourse {

    @PersonInfoAnnotation(name = "jhmarryme", language = {"Java", "c++", "python"})
    private String author;

    @CourseInfoAnnotation(courseName = "spring源码", courseTag = "spring", courseProfile = "Spring源码轻松学 一课覆盖Spring核心知识点")
    public void getCourseInfo() {
    }
}