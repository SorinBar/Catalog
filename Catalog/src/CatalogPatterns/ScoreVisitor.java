package CatalogPatterns;

import Catalog.Catalog;
import CatalogAux.Grade;
import CatalogCourses.Course;
import CatalogUsers.Assistant;
import CatalogUsers.Student;
import CatalogUsers.Teacher;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreVisitor implements Visitor{
    private Catalog catalog;
    private HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores;
    private HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores;
    public ScoreVisitor(Catalog catalog) {
        this.catalog = catalog;
        examScores = new HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>>();
        partialScores = new HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>>();
    }
    @Override
    public void visit(Teacher teacher) {
        ArrayList<Tuple<Student, String, Double>> dataList = examScores.get(teacher);
        if (dataList == null)
            return;
        Course course;
        Grade grade;
        for(Tuple<Student, String, Double> data : dataList) {
            course = catalog.getCourse(data.getY());
            grade = course.getGrade(data.getX());
            grade.setExamScore(data.getZ());
            catalog.notifyObservers(grade);
        }
    }
    @Override
    public void visit(Assistant assistant) {
        ArrayList<Tuple<Student, String, Double>> dataList = partialScores.get(assistant);
        if (dataList == null)
            return;
        Course course;
        Grade grade;
        for(Tuple<Student, String, Double> data : dataList) {
            course = catalog.getCourse(data.getY());
            grade = course.getGrade(data.getX());
            grade.setExamScore(data.getZ());
            catalog.notifyObservers(grade);
        }
    }

    public void addExamScore(Teacher teacher, Student student, String course, Double score) {
        Tuple<Student, String, Double> data = new Tuple<>(student, course, score);
        ArrayList<Tuple<Student, String, Double>> dataList = examScores.get(teacher);
        if (dataList == null) {
            dataList = new ArrayList<Tuple<Student, String, Double>>();
            dataList.add(data);
            examScores.put(teacher, dataList);
        }
        else
            dataList.add(data);
    }

    public void addPartialScore(Assistant assistant, Student student, String course, Double score) {
        Tuple<Student, String, Double> data = new Tuple<>(student, course, score);
        ArrayList<Tuple<Student, String, Double>> dataList = partialScores.get(assistant);
        if (dataList == null) {
            dataList = new ArrayList<Tuple<Student, String, Double>>();
            dataList.add(data);
            partialScores.put(assistant, dataList);
        }
        else
            dataList.add(data);
    }

    private class Tuple<X, Y, Z> {
        private X x;
        private Y y;
        private Z z;

        public Tuple(){}
        public Tuple(X x, Y y, Z z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public void setX(X x) {
            this.x = x;
        }
        public void setY(Y y) {
            this.y = y;
        }
        public void setZ(Z z) {
            this.z = z;
        }
        public X getX() {
            return x;
        }
        public Y getY() {
            return y;
        }
        public Z getZ() {
            return z;
        }
    }
}
