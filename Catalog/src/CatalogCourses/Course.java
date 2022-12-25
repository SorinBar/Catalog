package CatalogCourses;

import CatalogAux.Grade;
import CatalogAux.Group;
import CatalogPatterns.Notification;
import CatalogPatterns.Observer;
import CatalogPatterns.ScoreStrategy;
import CatalogPatterns.Subject;
import CatalogUsers.Assistant;
import CatalogUsers.Student;
import CatalogUsers.Teacher;

import java.util.*;

public abstract class Course {
    private String name;
    private Teacher teacher;
    private int credit;
    private HashSet<Assistant> assistants;
    protected ArrayList<Grade> grades;
    private HashMap<String, Group> groups;
    private ScoreStrategy strategy;

    protected Course(CourseBuilder builder) {
        this.name = builder.name;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
        this.credit = builder.credit;
        this.strategy = builder.strategy;
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
        Group reqGroup = (Group) (groups.get(ID));
        if (reqGroup == null)
            return;
        reqGroup.add(student);
        // Add grade for each student
        /*
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(name);
        addGrade(grade);
        */
    }
    public void addGroup(Group group) {
        if (groups.get(group.getID()) != null)
            return;
        groups.put(group.getID(), group);
    }
    public void addGroup(String ID, Assistant assistant) {
        if (groups.get(ID) != null)
            return;
        groups.put(ID, new Group(ID, assistant));
    }
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
        if (groups.get(ID) != null)
            return;
        groups.put(ID, new Group(ID, assist, comp));
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
    public void setStrategy(ScoreStrategy strategy) {
        this.strategy = strategy;
    }
    public Student getBestStudent() {
        if (strategy == null)
            return null;
        return strategy.getBestStudent(grades);
    }

    public static abstract class CourseBuilder {
        private String name;
        private Teacher teacher;
        private int credit;
        private HashSet<Assistant> assistants;
        private ArrayList<Grade> grades;
        private HashMap<String, Group> groups;
        private ScoreStrategy strategy;

        public CourseBuilder(String name) {
            this.name = name;
            assistants = new HashSet<Assistant>();
            grades = new ArrayList<Grade>();
            groups = new HashMap<String, Group>();
            strategy = null;
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
}
