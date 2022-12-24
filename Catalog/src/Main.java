import CatalogAux.Grade;
import CatalogCourses.*;
import CatalogPatterns.Notification;
import CatalogUsers.*;

import java.util.Date;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {

        FullCourse test = (FullCourse) new FullCourse.FullCourseBuilder("Mate")
                .teacher(new Teacher("Ana", "Maria"))
                .credit(4)
                .build();

        // Test course builder
        PartialCourse test2 = (PartialCourse) new PartialCourse.PartialCourseBuilder("Info")
                .teacher(new Teacher("Diana", "Maria"))
                .credit(6)
                .build();
        System.out.println(test.getName());
        System.out.println(test.getCredit());
        System.out.println(test.getTeacher().getFirstName());
        System.out.println(test2.getName());
        System.out.println(test2.getCredit());
        System.out.println(test2.getTeacher().getFirstName());
        ///////////////////////

        test.addGroup("321CC", new Assistant("first", "last"));
        test.addGroup("322CC", new Assistant("first2", "last"));
        test.addStudent("321CC", new Student("A", "B"));
        test.addStudent("321CC", new Student("A", "C"));
        test.addStudent("322CC", new Student("C", "B"));
        System.out.println(test.getAllStudents());
        Grade grade = new Grade();
        grade.setStudent(new Student("A", "B"));
        grade.setExamScore(1.9);
        grade.setPartialScore(3.3);
        grade.setCourse("Course1");
        test.addGrade(grade);
        grade = new Grade();
        grade.setStudent(new Student("A", "C"));
        grade.setExamScore(3.9);
        grade.setPartialScore(3.0);
        grade.setCourse("Course1");
        test.addGrade(grade);
        System.out.println(test.getGraduatedStudents());

    }

}
