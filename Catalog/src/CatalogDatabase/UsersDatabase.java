package CatalogDatabase;

import CatalogUsers.Teacher;
import CatalogUsers.Assistant;
import CatalogUsers.Student;
import CatalogUsers.Parent;

import java.util.HashMap;

public class UsersDatabase {
    private static UsersDatabase instance = null;
    private HashMap<String, Teacher> teachers;
    private HashMap<String, Assistant> assistants;
    private HashMap<String, Student> students;
    private HashMap<String, Parent> parents;
    private String adminPassword;
    public final static String adminPath = "src/CatalogDatabase/Database/admin.txt";
    public final static String teacherPath = "src/CatalogDatabase/Database/teachers.txt";
    public final static String assistantsPath = "src/CatalogDatabase/Database/assistants.txt";
    public final static String parentsPath = "src/CatalogDatabase/Database/parents.txt";
    public final static String studentsPath = "src/CatalogDatabase/Database/students.txt";

    private UsersDatabase(){
        teachers = new HashMap<String, Teacher>();
        assistants = new HashMap<String, Assistant>();
        students = new HashMap<String, Student>();
        parents = new HashMap<String, Parent>();
    }
    public static UsersDatabase getInstance() {
        if (instance == null)
            instance = new UsersDatabase();

        return instance;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    public void add(Teacher teacher) {
        teachers.put(teacher.getCNP(), teacher);
    }
    public void add(Assistant assistant) {
        assistants.put(assistant.getCNP(), assistant);
    }
    public void add(Student student) {
        students.put(student.getCNP(), student);
    }
    public void add(Parent parent) {
        parents.put(parent.getCNP(), parent);
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public Teacher getTeacher(String CNP) {
        return teachers.get(CNP);
    }
    public Assistant getAssistant(String CNP) {
        return assistants.get(CNP);
    }
    public Student getStudent(String CNP) {
        return students.get(CNP);
    }
    public Parent getParent(String CNP) {
        return parents.get(CNP);
    }
    public HashMap<String, Teacher> getTeachers() {
        return teachers;
    }
    public HashMap<String, Assistant> getAssistants() {
        return assistants;
    }
    public HashMap<String, Student> getStudents() {
        return students;
    }
    public HashMap<String, Parent> getParents() {
        return parents;
    }
    public void print() {
        System.out.println(adminPassword);
        System.out.println(teachers);
        System.out.println(assistants);
        System.out.println(parents);
        System.out.println(students);
    }
    public void load() {
        AdminData.load(this, adminPath);
        TeachersData.load(this, teacherPath);
        AssistantsData.load(this, assistantsPath);
        ParentsData.load(this, parentsPath);
        StudentsData.load(this, studentsPath);
    }

    public void update() {
        AdminData.update(this, adminPath);
        TeachersData.update(this, teacherPath);
        AssistantsData.update(this, assistantsPath);
        ParentsData.update(this, parentsPath);
        StudentsData.update(this, studentsPath);
    }
}
