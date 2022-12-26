package CatalogDatabase;

import CatalogUsers.Assistant;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class AssistantsData {
    public static void load(UsersDatabase usersDatabase, String path) {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading assistants!");
            return;
        }
        int index = 0;
        int number = Integer.parseInt(lines.get(index++));
        Assistant assistant;
        String CNP, fistName, lastName, hashPass;
        for (int i = 0; i < number; i++) {
            CNP = lines.get(index++);
            fistName = lines.get(index++);
            lastName = lines.get(index++);
            hashPass = lines.get(index++);
            assistant = new Assistant(fistName, lastName, CNP);
            assistant.setHashPass(hashPass);
            usersDatabase.add(assistant);
        }
    }
    public static void update(UsersDatabase usersDatabase, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(usersDatabase.getAssistants().size());
            for (Map.Entry<String, Assistant> entry : usersDatabase.getAssistants().entrySet()) {
                printWriter.println(entry.getValue().getCNP());
                printWriter.println(entry.getValue().getFirstName());
                printWriter.println(entry.getValue().getLastName());
                printWriter.println(entry.getValue().getHashPass());
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating assistants!");
        }
    }
}
