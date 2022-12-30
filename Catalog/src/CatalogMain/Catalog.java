package CatalogMain;

import CatalogAux.Grade;
import CatalogCourses.Course;
import CatalogPatterns.Notification;
import CatalogPatterns.Observer;
import CatalogPatterns.Subject;
import CatalogUsers.Student;

import java.util.*;

public class Catalog implements Subject {
    private static Catalog instance = null;
    private static HashMap<String, Course> courses;
    private final ArrayList<Observer> observers;
    ArrayList<String> coursesNames;
    public final static String catalogPath = "src/CatalogDatabase/Database/catalog.txt";

    private Catalog() {
        courses = new HashMap<>();
        observers = new ArrayList<>();
        coursesNames =  new ArrayList<>();
    }
    public static Catalog getInstance() {
        if (instance == null)
            instance = new Catalog();
        return instance;
    }
    public void addCourse(Course course) {
        if (courses.containsKey(course.getName()))
            return;
        courses.put(course.getName(), course);
        coursesNames.add(course.getName());
        Collections.sort(coursesNames);
    }
    public void removeCourse(Course course) {
        courses.remove(course.getName());
        coursesNames.remove(course.getName());
    }
    public Course getCourse(String name) {
        return courses.get(name);
    }
    public HashMap<String, Course> getCourses() {
        return courses;
    }
    public ArrayList<String> getCoursesNames() {
        return coursesNames;
    }
    public ArrayList<String> getStudentData(Student student) {
        ArrayList<String> studentData = new ArrayList<>();
        Grade grade;
        String data;
        for (Map.Entry<String, Course> entry : courses.entrySet()) {
            grade = entry.getValue().getGrade(student);
            if (grade != null) {
                data = "Course: " + entry.getValue().getName();
                data += ", Partial: " + grade.getPartialScore();
                data += ", Exam: " + grade.getExamScore();
                studentData.add(data);
            }
        }

        return  studentData;
    }
    @Override
    public void addObserver(CatalogPatterns.Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }
    @Override
    public void removeObserver(CatalogPatterns.Observer observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers(Grade grade) {
        Notification notification = new Notification(grade);
        for (Observer observer : observers)
            observer.update(notification);
    }
    // Testing
    public void print() {
        for (Map.Entry<String, Course> entry : courses.entrySet()) {
            entry.getValue().print();
            System.out.println();
        }
    }
    public ArrayList<Observer> getObservers() {
        return observers;
    }
}
