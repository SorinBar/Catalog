package CatalogCourses;

import CatalogAux.*;
import CatalogUsers.*;
import CatalogPatterns.ScoreStrategy;

import java.util.*;

public abstract class Course {
    private String name;
    private Teacher teacher;
    private int credit;
    private HashSet<Assistant> assistants;
    protected ArrayList<Grade> grades;
    public HashMap<String, Group> groups;
    private ScoreStrategy strategy;
    private Snapshot snapshot;
    private ArrayList<String> groupsData;

    protected Course(CourseBuilder builder) {
        this.name = builder.name;
        this.teacher = builder.teacher;
        this.credit = builder.credit;
        assistants = new HashSet<Assistant>();
        grades = new ArrayList<Grade>();
        groups = new HashMap<String, Group>();
        snapshot = new Snapshot();
        strategy = null;
        groupsData = new ArrayList<>();
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
    public String getName() {
        return name;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public int getCredit() {
        return credit;
    }
    public void addAssistant(String ID, Assistant assistant) {
        Group reqGroup = (Group) (groups.get(ID));
        if (reqGroup == null)
            return;
        reqGroup.setAssistant(assistant);
        assistants.add(assistant);
    }
    public void addStudent(String ID, Student student) {
        for (Map.Entry <String, Group> entry : groups.entrySet())
            if (entry.getValue().contains(student))
                return;
        Group reqGroup = (Group) (groups.get(ID));
        if (reqGroup == null)
            return;
        reqGroup.add(student);
    }
    public void addGroup(Group group) {
        if (groups.get(group.getID()) != null)
            return;
        groups.put(group.getID(), group);
        groupsData.add(group.getID());
        Collections.sort(groupsData);
    }
    public void addGroup(String ID, Assistant assistant) {
        if (groups.get(ID) != null)
            return;
        groups.put(ID, new Group(ID, assistant));
        groupsData.add(ID);
        Collections.sort(groupsData);
    }
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
        if (groups.get(ID) != null)
            return;
        groups.put(ID, new Group(ID, assist, comp));
        groupsData.add(ID);
        Collections.sort(groupsData);
    }
    public Grade getGrade(Student student) {
        for (Grade grade : grades) {
            Student cStudent = grade.getStudent();
            if (student.equals(cStudent))
                return grade;
        }
        return null;
    }
    public void addGrade(Grade grade) {
        grades.add(grade);
    }
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        for (Map.Entry<String, Group> pair : groups.entrySet())
            students.addAll(pair.getValue());

        return students;
    }
    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> studGrades = new HashMap<Student, Grade>();
        for (Grade grade : grades)
            studGrades.put(grade.getStudent(), grade);
        return studGrades;
    }
    public abstract ArrayList<Student> getGraduatedStudents();
    public HashMap<String, Group> getGroups() {
        return groups;
    }
    public void setStrategy(ScoreStrategy strategy) {
        this.strategy = strategy;
    }
    public Student getBestStudent() {
        if (strategy == null)
            return null;
        return strategy.getBestStudent(grades);
    }
    public void makeBackup() {
        snapshot.clearData();
        snapshot.saveData(grades);
    }
    public void undo() {
        while (grades.size() != 0)
            grades.remove(0);
        ArrayList<Grade> oldGrades = snapshot.getData();
        for(Grade grade : oldGrades)
            grades.add((Grade) grade.clone());
    }
    public Group getGroup(String groupID) {
        return groups.get(groupID);
    }
    public ArrayList<String> getGroupsData() {
        return groupsData;
    }


    public static abstract class CourseBuilder {
        private String name;
        private Teacher teacher;
        private int credit;

        public CourseBuilder(String name) {
            this.name = name;
        }
        public CourseBuilder teacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }
        public CourseBuilder credit(int credit) {
            this.credit = credit;
            return this;
        }
        public abstract Course build();
    }
    private class Snapshot {
        private ArrayList<Grade> grades;

        public Snapshot() {
            grades = new ArrayList<Grade>();
        }
        public void clearData() {
            while (grades.size() != 0)
                grades.remove(0);
        }
        public void saveData(ArrayList<Grade> grades) {
            clearData();
            for(Grade grade : grades)
                this.grades.add((Grade) grade.clone());
        }
        public ArrayList<Grade> getData() {
            return grades;
        }
    }
    // Testing
    public void print() {
        System.out.println("Course: " + name);
        System.out.println("Teacher: " + teacher);
        System.out.println("Type: " + this.getClass() + " Credit: " + credit);
        for(Map.Entry<String, Group> entry : groups.entrySet()) {
            System.out.println("Group: " + entry.getKey());
            System.out.println("Assistant: " + entry.getValue().getAssistant());
            System.out.println("Students: ");
            for (Student student : entry.getValue()) {
                System.out.println(student + ", " + getGrade(student));
            }
            System.out.println();
        }
    }
}
