package CatalogDatabase;

import CatalogAux.Grade;
import CatalogAux.Group;
import CatalogCourses.Course;
import CatalogCourses.FullCourse;
import CatalogCourses.PartialCourse;
import CatalogMain.Catalog;
import CatalogUsers.Student;
import CatalogUsers.Teacher;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CatalogData {
    public static void load(Catalog catalog,UsersDatabase usersDatabase, String path) {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading catalog!");
            return;
        }
        // Read from database
        int index = 0;
        int coursesNr = Integer.parseInt(lines.get(index++));
        int groupsNr;
        int credit;
        double partialScore;
        double examScore;
        Course course;
        Group group;
        Grade grade;
        String courseType;
        String courseName;
        String groupId;
        String teachersCNP;
        String assistantCNP;
        String studentCNP;

        for(int i = 0; i < coursesNr; i++) {
            // Load course data
            courseType = lines.get(index++);
            credit = Integer.parseInt(lines.get(index++));
            courseName = lines.get(index++);
            teachersCNP = lines.get(index++);
            if (courseType.equals("full"))
                course = (FullCourse) new FullCourse.FullCourseBuilder(courseName)
                        .teacher(usersDatabase.getTeacher(teachersCNP))
                        .credit(credit)
                        .build();
            else
                course = (PartialCourse) new PartialCourse.PartialCourseBuilder(courseName)
                        .teacher(usersDatabase.getTeacher(teachersCNP))
                        .credit(credit)
                        .build();
            // Add course to catalog
            catalog.addCourse(course);

            groupsNr = Integer.parseInt(lines.get(index++));
            for (int j = 0; j < groupsNr; j++) {
                groupId = lines.get(index++);
                assistantCNP = lines.get(index++);
                // Create group
                group = new Group(groupId, usersDatabase.getAssistant(assistantCNP), new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        int result = s1.getLastName().compareTo(s2.getLastName());
                        if (result == 0)
                            result = s1.getFirstName().compareTo(s2.getFirstName());

                        return result;
                    }
                });
                // Add group
                course.addGroup(group);
                while (!lines.get(index).isBlank()) {
                    studentCNP = lines.get(index++);
                    partialScore = -1.0;
                    examScore = -1.0;
                    if (!lines.get(index).equals("null"))
                        partialScore = Double.parseDouble(lines.get(index++));
                    else
                        index++;
                    if (!lines.get(index).equals("null"))
                        examScore = Double.parseDouble(lines.get(index++));
                    else
                        index++;
                    // Add student
                    course.addStudent(groupId, usersDatabase.getStudent(studentCNP));
                    // Add grade
                    grade = new Grade();
                    if (partialScore != -1.0)
                        grade.setPartialScore(partialScore);
                    if (examScore != -1.0)
                        grade.setExamScore(examScore);
                    grade.setCourse(course.getName());
                    grade.setStudent(usersDatabase.getStudent(studentCNP));
                    course.addGrade(grade);
                }
                index++;
            }
        }
    }
    public static void update(Catalog catalog, UsersDatabase usersDatabase, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            // fill database
            printWriter.println("example");
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating admin!");
        }
    }
}
