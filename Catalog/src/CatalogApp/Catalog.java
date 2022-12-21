import java.util.ArrayList;

public class Catalog {
    private static Catalog instance = null;
    private static ArrayList<Course> courses;
    private Catalog() {
        courses = new ArrayList<Course>();
    }
    public static Catalog getInstance() {
        if (instance == null)
            instance = new Catalog();
        return instance;
    }
    public void addCourse(Course course) {
        courses.add(course);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    // Test
    public void printCourses() {
        System.out.println(courses);
    }
}
