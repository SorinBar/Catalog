import CatalogCourses.Course;

import java.util.HashMap;

public class Catalog {
    private static Catalog instance = null;
    private static HashMap<String, Course> courses;

    private Catalog() {
        courses = new HashMap<String, Course>();
    }
    public static Catalog getInstance() {
        if (instance == null)
            instance = new Catalog();
        return instance;
    }
    public void addCourse(Course course) {
        courses.put(course.getName(), course);
    }
    public void removeCourse(Course course) {
        courses.remove(course.getName());
    }
    public Course getCourse(String name) {
        return courses.get(name);
    }
}
