package CatalogDatabase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AdminData {
    public static void load(UsersDatabase usersDatabase, String path) {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading admin!");
            return;
        }
        usersDatabase.setAdminPassword(lines.get(0));
    }
    public static void update(UsersDatabase usersDatabase, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(usersDatabase.getAdminPassword());
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating admin!");
        }
    }
}
