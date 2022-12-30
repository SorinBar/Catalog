package Test;

import CatalogAux.Grade;
import CatalogCourses.Course;
import CatalogCourses.FullCourse;
import CatalogMain.Catalog;
import CatalogPatterns.BestExamScore;
import CatalogPatterns.BestPartialScore;
import CatalogPatterns.BestTotalScore;
import CatalogPatterns.ScoreVisitor;
import CatalogUsers.Assistant;
import CatalogUsers.Student;
import CatalogUsers.Teacher;
import CatalogUsers.UserFactory;
import SetUp.SetUp;

import java.util.Comparator;


public class Test {
    public static void main(String[] args) {
        SetUp setUp = new SetUp();
        setUp.loadUsersDatabase();
        setUp.loadCatalog();
        setUp.loadScoreDatabase();
        setUp.loadNotificationsDatabase();

        Catalog catalog = setUp.getMediator().getCatalog();

        System.out.println("--------Initial Catalog--------");
        catalog.print();
        System.out.println("--------Started Testing--------");

        // Add Course
        {
            System.out.println("--------Add Course--------");
            Course course = new FullCourse.FullCourseBuilder("Mate")
                    .teacher(new Teacher("Ana", "Maria", "2343242"))
                    .credit(4)
                    .build();

            catalog.addCourse(course);

            catalog.print();
        }
        // Add Group
        {
            System.out.println("--------Add Group--------");
            Course course = catalog.getCourse("Mate");
            course.addGroup("GrupaA", new Assistant("AssistantFirstName",
                    "AssistantLastName", "123"), new Comparator<Student>() {
                @Override
                public int compare(Student s1, Student s2) {
                    return s1.getCNP().compareTo(s2.getCNP());
                }
            });

            course.print();
        }
        Student student;
        // Add students
        {
            System.out.println("--------Add Students--------");
            Course course = catalog.getCourse("Mate");
            student = (Student) UserFactory.getUser(UserFactory.UserType.Student,
                    "StudentFirstName1", "StudentLastName1", "124");
            course.addStudent("GrupaA", student);
            course.addStudent("GrupaA", new Student("StudentFirstName2",
                    "StudentLastName2","125"));

            course.print();
        }
        // Add Grade
        {
            System.out.println("--------Add Grade--------");
            Course course = catalog.getCourse("Mate");

            Grade grade = new Grade();
            grade.setCourse(course.getName());
            grade.setStudent(student);

            // Add empty grade for student
            // This is done automatically when student is assigned to the course
            course.addGrade(grade);

            ScoreVisitor scoreVisitor = setUp.getMediator().getScoreVisitor();
            scoreVisitor.addExamScore(course.getTeacher(), student, course.getName(), 3.6);

            course.print();
        }
        // Validate Grade
        {
            System.out.println("--------Validate Grade--------");
            Course course = catalog.getCourse("Mate");

            ScoreVisitor scoreVisitor = setUp.getMediator().getScoreVisitor();
            scoreVisitor.print();
            course.getTeacher().accept(scoreVisitor);

            course.print();
        }
        // Back Up Grades
        {
            System.out.println("--------Back Up Grade--------");
            Course course = catalog.getCourse("Mate");

            course.makeBackup();
            course.getGrade(student).setExamScore(10.0);

            course.print();
        }
        // Reset Grades
        {
            System.out.println("--------Reset Grade--------");
            Course course = catalog.getCourse("Mate");

            course.undo();

            course.print();
        }
        // Get The Best Student
        {
            System.out.println("--------Get The Best Student--------");
            Course course = catalog.getCourse("Mate");

            // Add grade for the other student
            for (Student stud : course.getAllStudents()) {
                // The second student
                if (stud.getCNP().equals("125")) {
                    Grade grade = new Grade();
                    grade.setCourse(course.getName());
                    grade.setStudent(stud);
                    grade.setExamScore(3.7);
                    course.addGrade(grade);
                }
            }
            double score = 4.3;
            // Add grade for the other student
            for (Student stud : course.getAllStudents()) {
                course.getGrade(stud).setPartialScore(score);
                score -= 2.0;
            }
            course.print();
            course.setStrategy(new BestExamScore());
            System.out.println("Best exam strategy:");
            System.out.println(course.getBestStudent());

            course.setStrategy(new BestPartialScore());
            System.out.println("Best partial strategy:");
            System.out.println(course.getBestStudent());

            course.setStrategy(new BestTotalScore());
            System.out.println("Best total strategy:");
            System.out.println(course.getBestStudent());
        }
        // Remove Course
        {
            System.out.println("--------Remove Course--------");
            Course course = catalog.getCourse("Mate");

            catalog.removeCourse(course);

            catalog.print();
        }
    }

}