package CatalogDatabase;

import CatalogMain.Catalog;
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
    public void addTeacher(Teacher teacher) {
        teachers.put(teacher.getCNP(), teacher);
    }
    public void addAssistant(Assistant assistant) {
        assistants.put(assistant.getCNP(), assistant);
    }
    public void addStudent(Student student) {
        students.put(student.getCNP(), student);
    }
    public void addParent(Parent parent) {
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
}
