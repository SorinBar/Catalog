import Catalog.Catalog;
import CatalogAux.Grade;
import CatalogCourses.*;
import CatalogPatterns.BestExamScore;
import CatalogPatterns.BestPartialScore;
import CatalogPatterns.BestTotalScore;
import CatalogPatterns.ScoreVisitor;
import CatalogUsers.*;


public class Main {
    public static void main(String[] args) {

        FullCourse test = (FullCourse) new FullCourse.FullCourseBuilder("Mate")
                .teacher(new Teacher("Ana", "Maria"))
                .credit(4)
                .build();

        test.addGroup("321CC", new Assistant("first", "last"));
        test.addGroup("322CC", new Assistant("first2", "last"));
        test.addStudent("321CC", new Student("A", "B"));
        test.addStudent("321CC", new Student("A", "C"));
        test.addStudent("322CC", new Student("C", "B"));
        System.out.println(test.getAllStudents());
        Grade grade = new Grade();
        grade.setStudent(new Student("David", "Petrescu"));
        grade.setExamScore(3.9);
        grade.setPartialScore(3.0);
        grade.setCourse("Mate");
        test.addGrade(grade);
        grade = new Grade();
        grade.setStudent(new Student("Florin", "Cazaciu"));
        grade.setExamScore(3.2);
        grade.setPartialScore(4.0);
        grade.setCourse("Mate");
        test.addGrade(grade);
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
        Teacher t1 = new Teacher("Florin", "Tamas");
        //t1.accept(new ScoreVisitor(null));
    }

}
