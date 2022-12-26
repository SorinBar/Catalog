package CatalogDatabase;

import CatalogUsers.Parent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class ParentsData {
    public static void load(UsersDatabase usersDatabase, String path) {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading parents!");
            return;
        }
        int index = 0;
        int number = Integer.parseInt(lines.get(index++));
        Parent parent;
        String CNP, fistName, lastName, hashPass;
        for (int i = 0; i < number; i++) {
            CNP = lines.get(index++);
            fistName = lines.get(index++);
            lastName = lines.get(index++);
            hashPass = lines.get(index++);
            parent = new Parent(fistName, lastName, CNP);
            parent.setHashPass(hashPass);
            usersDatabase.add(parent);
        }
    }
    public static void update(UsersDatabase usersDatabase, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(usersDatabase.getParents().size());
            for (Map.Entry<String, Parent> entry : usersDatabase.getParents().entrySet()) {
                printWriter.println(entry.getValue().getCNP());
                printWriter.println(entry.getValue().getFirstName());
                printWriter.println(entry.getValue().getLastName());
                printWriter.println(entry.getValue().getHashPass());
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating parents!");
        }
    }
}
