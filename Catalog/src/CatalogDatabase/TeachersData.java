package CatalogDatabase;

import CatalogUsers.Teacher;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class TeachersData {
    public static void load(UsersDatabase usersDatabase, String path) {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading teachers!");
            return;
        }
        int index = 0;
        int number = Integer.parseInt(lines.get(index++));
        Teacher teacher;
        String CNP, fistName, lastName, hashPass;
        for (int i = 0; i < number; i++) {
            CNP = lines.get(index++);
            fistName = lines.get(index++);
            lastName = lines.get(index++);
            hashPass = lines.get(index++);
            teacher = new Teacher(fistName, lastName, CNP);
            teacher.setHashPass(hashPass);
            usersDatabase.add(teacher);
        }
    }
    public static void update(UsersDatabase usersDatabase, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(usersDatabase.getTeachers().size());
            for (Map.Entry<String, Teacher> entry : usersDatabase.getTeachers().entrySet()) {
                printWriter.println(entry.getValue().getCNP());
                printWriter.println(entry.getValue().getFirstName());
                printWriter.println(entry.getValue().getLastName());
                printWriter.println(entry.getValue().getHashPass());
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating teachers!");
        }
    }
}
