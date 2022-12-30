package CatalogCourses;

import CatalogAux.Grade;
import CatalogUsers.Student;

import java.util.ArrayList;

public class FullCourse extends Course{

    private FullCourse(FullCourseBuilder builder) {
        super(builder);
    }
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Grade grade: grades) {
            if (grade.getPartialScore() >= 3.0 && grade.getExamScore() >= 2.0)
                students.add(grade.getStudent());
        }
        return students;
    }

    public static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder(String name) {
            super(name);
        }
        public FullCourse build() {
            return new FullCourse(this);
        }
    }
}
