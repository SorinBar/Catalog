package CatalogCourses;

import CatalogUsers.Student;

import java.util.ArrayList;

public class PartialCourse extends Course{
    private PartialCourse(PartialCourseBuilder builder){
        super(builder);
    }
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        for (Grade grade: grades) {
            if (grade.getTotal() >= 5.0)
                students.add(grade.getStudent());
        }
        return students;
    }

    public static class PartialCourseBuilder extends CourseBuilder {
        public PartialCourseBuilder(String name) {
            super(name);
        }
        public PartialCourse build() {
            return new PartialCourse(this);
        }
    }
}
