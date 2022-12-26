package CatalogDatabase;

import CatalogUsers.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class StudentsData {
    public static void load(UsersDatabase usersDatabase, String path) {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading students!");
            return;
        }
        int index = 0;
        int number = Integer.parseInt(lines.get(index++));
        Student student;
        String CNP, fistName, lastName, hashPass;
        String cnpFather, cnpMother;
        for (int i = 0; i < number; i++) {
            CNP = lines.get(index++);
            fistName = lines.get(index++);
            lastName = lines.get(index++);
            hashPass = lines.get(index++);
            cnpFather = lines.get(index++);
            cnpMother = lines.get(index++);
            student = new Student(fistName, lastName, CNP);
            student.setFather(usersDatabase.getParent(cnpFather));
            student.setMother(usersDatabase.getParent(cnpMother));
            student.setHashPass(hashPass);
            usersDatabase.add(student);
        }
    }
    public static void update(UsersDatabase usersDatabase, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(usersDatabase.getStudents().size());
            for (Map.Entry<String, Student> entry : usersDatabase.getStudents().entrySet()) {
                printWriter.println(entry.getValue().getCNP());
                printWriter.println(entry.getValue().getFirstName());
                printWriter.println(entry.getValue().getLastName());
                printWriter.println(entry.getValue().getHashPass());
                printWriter.println(entry.getValue().getFather().getCNP());
                printWriter.println(entry.getValue().getMother().getCNP());
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating students!");
        }
    }
}
