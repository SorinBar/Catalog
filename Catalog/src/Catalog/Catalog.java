package Catalog;

import CatalogAux.Grade;
import CatalogCourses.Course;
import CatalogPatterns.Notification;
import CatalogPatterns.Observer;
import CatalogPatterns.Subject;

import java.util.ArrayList;
import java.util.HashMap;

public class Catalog implements Subject {
    private static Catalog instance = null;
    private static HashMap<String, Course> courses;
    private ArrayList<Observer> observers;

    private Catalog() {
        courses = new HashMap<String, Course>();
        observers = new ArrayList<Observer>();
    }
    public static Catalog getInstance() {
        if (instance == null)
            instance = new Catalog();
        return instance;
    }
    public void addCourse(Course course) {
        courses.put(course.getName(), course);
    }
    public void removeCourse(Course course) {
        courses.remove(course.getName());
    }
    public Course getCourse(String name) {
        return courses.get(name);
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
}
