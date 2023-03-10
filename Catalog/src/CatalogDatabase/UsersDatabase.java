package CatalogDatabase;

import CatalogUsers.Teacher;
import CatalogUsers.Assistant;
import CatalogUsers.Student;
import CatalogUsers.Parent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class UsersDatabase {
    private static UsersDatabase instance = null;
    private final HashMap<String, Teacher> teachers;
    private final HashMap<String, Assistant> assistants;
    private final HashMap<String, Student> students;
    private final HashMap<String, Parent> parents;
    private final ArrayList<String> teachersData;
    private final ArrayList<String> assistantsData;
    private final ArrayList<String> studentsData;
    private final ArrayList<String> parentsData;
    private final Comparator<String> userDataComp;
    private String adminPassword;
    public final static String adminPath = "src/CatalogDatabase/Database/admin.txt";
    public final static String teacherPath = "src/CatalogDatabase/Database/teachers.txt";
    public final static String assistantsPath = "src/CatalogDatabase/Database/assistants.txt";
    public final static String parentsPath = "src/CatalogDatabase/Database/parents.txt";
    public final static String studentsPath = "src/CatalogDatabase/Database/students.txt";

    private UsersDatabase(){
        teachers = new HashMap<>();
        assistants = new HashMap<>();
        students = new HashMap<>();
        parents = new HashMap<>();
        teachersData = new ArrayList<>();
        assistantsData = new ArrayList<>();
        studentsData = new ArrayList<>();
        parentsData = new ArrayList<>();
        userDataComp = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int result = s1.substring(14).compareTo(s2.substring(14));
                if (result == 0)
                    return s1.compareTo(s2);

                return result;
            }
        };
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
        teachersData.add(teacher.toString());
        teachersData.sort(userDataComp);
    }
    public void add(Assistant assistant) {
        assistants.put(assistant.getCNP(), assistant);
        assistantsData.add(assistant.toString());
        assistantsData.sort(userDataComp);
    }
    public void add(Student student) {
        students.put(student.getCNP(), student);
        studentsData.add(student.toString());
        studentsData.sort(userDataComp);
    }
    public void add(Parent parent) {
        parents.put(parent.getCNP(), parent);
        parentsData.add(parent.toString());
        parentsData.sort(userDataComp);
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
    public ArrayList<String> getTeachersData() {
        return teachersData;
    }
    public ArrayList<String> getAssistantsData() {
        return assistantsData;
    }
    public ArrayList<String> getStudentsData() {
        return studentsData;
    }
    public ArrayList<String> getParentsData() {
        return parentsData;
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
    // Testing
    public void print() {
        System.out.println(adminPassword);
        System.out.println(teachers);
        System.out.println(assistants);
        System.out.println(parents);
        System.out.println(students);
    }
}
