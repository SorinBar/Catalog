package CatalogDatabase;

import CatalogAux.*;
import CatalogCourses.*;
import CatalogMain.Catalog;
import CatalogUsers.Parent;
import CatalogUsers.Student;

import javax.xml.catalog.CatalogResolver;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CatalogData {
    public static void load(Catalog catalog,UsersDatabase usersDatabase, String path) {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading catalog!");
            return;
        }
        // Read from database
        int index = 0;
        int coursesNr;
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

        // Courses
        coursesNr = Integer.parseInt(lines.get(index++));
        for(int i = 0; i < coursesNr; i++) {
            // Load course data
            courseName = lines.get(index++);
            courseType = lines.get(index++);
            credit = Integer.parseInt(lines.get(index++));
            teachersCNP = lines.get(index++);
            if (courseType.equals("full"))
                course = new FullCourse.FullCourseBuilder(courseName)
                        .teacher(usersDatabase.getTeacher(teachersCNP))
                        .credit(credit)
                        .build();
            else
                course = new PartialCourse.PartialCourseBuilder(courseName)
                        .teacher(usersDatabase.getTeacher(teachersCNP))
                        .credit(credit)
                        .build();
            // Add course to catalog
            catalog.addCourse(course);
            // Groups
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
    public static void update(Catalog catalog, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Update course data
            Course course;
            Group group;
            Grade grade;
            HashMap<String, Group> groups;
            HashMap<String, Course> courses = catalog.getCourses();
            printWriter.println(courses.size());
            for(Map.Entry<String, Course> cEntry : courses.entrySet()) {
                course = cEntry.getValue();
                printWriter.println(course.getName());
                if (course instanceof FullCourse)
                    printWriter.println("full");
                else
                    printWriter.println("part");
                printWriter.println(course.getCredit());
                printWriter.println(course.getTeacher().getCNP());
                groups = course.getGroups();
                printWriter.println(groups.size());
                for (Map.Entry<String, Group> gEntry : groups.entrySet()) {
                   group = gEntry.getValue();
                   printWriter.println(group.getID());
                   printWriter.println(group.getAssistant().getCNP());
                   for (Student student : group) {
                       printWriter.println(student.getCNP());
                       grade = course.getGrade(student);
                       if (grade.getPartialScore() == null)
                           printWriter.println("null");
                       else
                           printWriter.println(grade.getPartialScore());
                       if (grade.getExamScore() == null)
                           printWriter.println("null");
                       else
                           printWriter.println(grade.getExamScore());
                   }
                   printWriter.println();
                }
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating admin!");
        }
    }
    public static void addParents(Catalog catalog, UsersDatabase usersDatabase) {
        for (Map.Entry<String, Parent> entry : usersDatabase.getParents().entrySet()) {
            catalog.addObserver(entry.getValue());
        }
    }
}
