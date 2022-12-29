package CatalogDatabase;

import CatalogAux.Grade;
import CatalogPatterns.Notification;
import CatalogPatterns.ScoreVisitor;
import CatalogUsers.Assistant;
import CatalogUsers.Student;
import CatalogUsers.Teacher;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationsDatabase {
    UsersDatabase usersDatabase;
    HashMap<String, ArrayList<Notification>> notifications;
    public final String path = "src/CatalogDatabase/Database/notifications.txt";

    public NotificationsDatabase (UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
        notifications = new HashMap<>();
    }
    public void addNotification (String parentCnp, Notification notification){
        ArrayList<Notification> notificationArray = notifications.get(parentCnp);
        if (notificationArray == null) {
            notificationArray = new ArrayList<>();
            notificationArray.add(notification);
            notifications.put(parentCnp, notificationArray);
        }
        else
            notificationArray.add(notification);
    }
    public ArrayList<Notification> getNotification (String parentCnp) {
        return notifications.get(parentCnp);
    }
    public void load() {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading notifications database!");
            return;
        }
        int index = 0;
        int parentsNr;
        int notificationsNr;
        String parentCnp;
        String aux;
        Grade grade;
        Notification notification;

        parentsNr = Integer.parseInt(lines.get(index++));
        for (int i = 0; i < parentsNr; i++) {
            parentCnp = lines.get(index++);
            notificationsNr = Integer.parseInt(lines.get(index++));
            for (int j = 0; j < notificationsNr; j++) {
                grade = new Grade();
                grade.setStudent(usersDatabase.getStudent(lines.get(index++)));
                grade.setCourse(lines.get(index++));
                aux = lines.get(index++);
                if (!aux.equals("null"))
                    grade.setPartialScore(Double.parseDouble(aux));
                aux = lines.get(index++);
                if (!aux.equals("null"))
                    grade.setExamScore(Double.parseDouble(aux));
                notification = new Notification(grade);
                notification.setDate(lines.get(index++));
                addNotification(parentCnp, notification);
            }
        }
    }
    public void update() {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            Grade grade;
            printWriter.println(notifications.size());
            for(Map.Entry<String, ArrayList<Notification>> entry : notifications.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue().size());
                for (Notification notification : entry.getValue()) {
                    grade = notification.getGrade();
                    printWriter.println(grade.getStudent().getCNP());
                    printWriter.println(grade.getCourse());
                    printWriter.println(grade.getPartialScore());
                    printWriter.println(grade.getExamScore());
                    printWriter.println(notification.getDate());
                }
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating notifications!");
        }
    }
    // Testing
    public void print() {
        System.out.println(notifications);
    }
}
