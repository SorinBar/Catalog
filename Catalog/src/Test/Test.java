package Test;

import CatalogAux.Grade;
import CatalogCourses.*;

import CatalogPatterns.BestExamScore;
import CatalogPatterns.BestPartialScore;
import CatalogPatterns.BestTotalScore;
import CatalogUsers.*;


public class Test {
    public static void main(String[] args) {

        FullCourse test = (FullCourse) new FullCourse.FullCourseBuilder("Mate")
                .teacher(new Teacher("Ana", "Maria", "2343242"))
                .credit(4)
                .build();

        test.addGroup("321CC", new Assistant("first", "last", "1"));
        test.addGroup("322CC", new Assistant("first2", "last", "2"));
        test.addStudent("321CC", new Student("A", "B", "3"));
        test.addStudent("321CC", new Student("A", "C", "4"));
        test.addStudent("322CC", new Student("C", "B", "5"));
        System.out.println(test.getAllStudents());
        Grade grade = new Grade();
        grade.setStudent(new Student("David", "Petrescu", "6"));
        grade.setExamScore(3.9);
        grade.setPartialScore(3.0);
        grade.setCourse("Mate");
        test.addGrade(grade);
        grade = new Grade();
        grade.setStudent(new Student("Florin", "Cazaciu", "7"));
        grade.setExamScore(3.2);
        grade.setPartialScore(4.0);
        grade.setCourse("Mate");
        test.addGrade(grade);
        System.out.println("TEST " + test.getGrade(new Student("Florin", "Cazaciu", "7")).getStudent());
        System.out.println(test.getGraduatedStudents());
        System.out.println("Best partial grade:");
        test.setStrategy(new BestPartialScore());
        System.out.println(test.getBestStudent());
        System.out.println("Best exam grade:");
        test.setStrategy(new BestExamScore());
        System.out.println(test.getBestStudent());
        System.out.println("Best exam grade:");
        test.setStrategy(new BestTotalScore());
        System.out.println(test.getBestStudent());
        Teacher t1 = new Teacher("Florin", "Tamas", "7");

    }

}